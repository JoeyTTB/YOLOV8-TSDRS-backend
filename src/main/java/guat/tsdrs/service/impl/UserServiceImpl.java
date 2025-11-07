package guat.tsdrs.service.impl;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.mapper.UserMapper;
import guat.tsdrs.pojo.User;
import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.pojo.vo.LoginUser;
import guat.tsdrs.service.UserService;
import guat.tsdrs.utils.AliOSSUtils;
import guat.tsdrs.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AliOSSUtils aliOSSUtils;

    @Override
    public String login(UserLoginDTO userLoginDTO) {
        log.info(userLoginDTO.toString());
        Authentication authenticate = null;
        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            authenticate = authenticationManager.authenticate(auth);
        } catch (AuthenticationException e) {
            log.info(e.getMessage());
            if(e instanceof BadCredentialsException) {
                throw new BadCredentialsException(ResultEnum.USER_LOGIN_FAILED.getMsg());
            }
            else {
                throw new InternalAuthenticationServiceException(ResultEnum.ERROR.getMsg());
            }
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        if(redisTemplate.opsForValue().get(loginUser.getUsername()) != null) {
            throw new RememberMeAuthenticationException(ResultEnum.USER_HAS_LOGGED.getMsg());
        }
        redisTemplate.opsForValue().set(loginUser.getUsername(), ResultEnum.USER_HAS_LOGGED.getCode(), 1, TimeUnit.HOURS);
        String loginUserString = JSON.toJSONString(loginUser);
        return JwtUtils.createJwt(loginUserString);
    }

    @Override
    public Integer register(RegisterDTO registerDTO) {
        if(!registerDTO.getPassword().equals(registerDTO.getRePassword())) {
            return ResultEnum.INCORRECT_PASSWORD_CHECK.getCode();
        }
        User user = userMapper.getUserByName(registerDTO.getUsername());
        if(Objects.isNull(user)) {
            LocalDate currentDate = LocalDate.now();

            // 定义格式化模式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            // 将日期格式化为指定格式
            String formattedDate = currentDate.format(formatter);
            user = new User(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()), formattedDate);
            userMapper.insertUser(user);
            return ResultEnum.REGISTER_SUCCESS.getCode();
        } else {
            return ResultEnum.USER_HAD_EXIST.getCode();
        }
    }

    @Override
    public boolean logout(String token) {
        try {
            Claims claims = JwtUtils.parseJwt(token);
            String subject = claims.getSubject();
            LoginUser loginUser = JSON.parseObject(subject, LoginUser.class);
            redisTemplate.delete(loginUser.getUsername());
            return true;
        } catch (Exception e) {
            log.error("登出失败，认证已过期:{}", e.getMessage());
            return false;
        }
    }

    @Override
    public String getRegDate(String username) {
        return userMapper.getRegDate(username);
    }

    @Override
    public String uploadAvatar(String username, MultipartFile file) {
        String url = "";
        try {
            url = aliOSSUtils.upload(file);
            if(url.isEmpty()) {
                throw new IOException("头像文件上传失败");
            } else {
                log.info("头像文件上传成功");
                userMapper.uploadAvatar(username, url);
            }
            return url;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAvatar(String username) {
        return userMapper.getAvatar(username);
    }
}
