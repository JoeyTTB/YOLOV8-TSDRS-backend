package guat.tsdrs.service.impl;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.mapper.UserMapper;
import guat.tsdrs.pojo.User;
import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.pojo.vo.LoginUser;
import guat.tsdrs.service.UserService;
import guat.tsdrs.utils.JwtUtils;
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
            user = new User(registerDTO.getUsername(), passwordEncoder.encode(registerDTO.getPassword()));
            userMapper.insertUser(user);
            return ResultEnum.REGISTER_SUCCESS.getCode();
        } else {
            return ResultEnum.USER_HAD_EXIST.getCode();
        }
    }

    @Override
    public void logout(String username) {
        redisTemplate.delete(username);
    }
}
