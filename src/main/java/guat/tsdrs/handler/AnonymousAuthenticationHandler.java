package guat.tsdrs.handler;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.exception.CustomAuthenticationException;
import guat.tsdrs.pojo.Result;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 匿名用户无权限访问资源 和 客户端认证数据提交异常
 */
@Component
@Slf4j
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("解决认证异常: " + authException);
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        String jsonString = "";
        if (authException instanceof CustomAuthenticationException) {
            jsonString = JSON.toJSONString(authException.getMessage());
        }
        else if(authException instanceof BadCredentialsException) {
            jsonString = JSON.toJSONString(Result.error(ResultEnum.USER_LOGIN_FAILED.getCode(), ResultEnum.USER_LOGIN_FAILED.getMsg()));
        }
        else if(authException instanceof InternalAuthenticationServiceException) {
            jsonString = JSON.toJSONString(Result.error(ResultEnum.ACCOUNT_NOT_FOUND.getCode(), ResultEnum.ACCOUNT_NOT_FOUND.getMsg()));
        }
        else if(authException instanceof InsufficientAuthenticationException) {
            jsonString = JSON.toJSONString(Result.error(ResultEnum.USER_NEED_AUTH.getCode(), ResultEnum.USER_NEED_AUTH.getMsg()));
        }
        else if(authException instanceof RememberMeAuthenticationException) {
            jsonString = JSON.toJSONString(Result.error(ResultEnum.USER_HAS_LOGGED.getCode(), ResultEnum.USER_HAS_LOGGED.getMsg()));
        }
        else {
            jsonString = JSON.toJSONString(Result.error(ResultEnum.ILLEGAL_OPERATION.getCode(), ResultEnum.ILLEGAL_OPERATION.getMsg()));
        }
        outputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
