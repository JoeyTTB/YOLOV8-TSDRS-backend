package guat.tsdrs.service.impl;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.pojo.vo.LoginUser;
import guat.tsdrs.service.UserService;
import guat.tsdrs.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

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
        String loginUserString = JSON.toJSONString(loginUser);
        return JwtUtils.createJwt(loginUserString);
    }
}
