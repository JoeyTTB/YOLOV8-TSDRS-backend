package guat.tsdrs.handler;

import com.alibaba.fastjson.JSONObject;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("解决登录失败异常: " + exception);
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream out = response.getOutputStream();
        Result err = null;
        if(exception instanceof BadCredentialsException){
            err = Result.error(ResultEnum.USER_LOGIN_FAILED.getCode(), ResultEnum.USER_LOGIN_FAILED.getMsg());
        }
        else if(exception instanceof InternalAuthenticationServiceException){
            err = Result.error(ResultEnum.ACCOUNT_NOT_FOUND.getCode(), ResultEnum.ACCOUNT_NOT_FOUND.getMsg());
        }
        else {
            err = Result.error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMsg());
        }
        String res = JSONObject.toJSONString(err);
        out.write(res.getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();
    }
}
