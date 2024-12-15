package guat.tsdrs.filter;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.exception.CustomAuthenticationException;
import guat.tsdrs.pojo.vo.LoginUser;
import guat.tsdrs.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.naming.AuthenticationException;
import javax.naming.InsufficientResourcesException;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String uri = request.getRequestURI();
            System.out.println("request uri: " + uri);
            if(!(uri.equals("/account/login") || uri.equals("/account/register"))) {
                this.validateToken(request);
            }
        } catch (CustomAuthenticationException e) {
            throw new RuntimeException(e);
        }
        filterChain.doFilter(request, response);
    }

    private void validateToken(HttpServletRequest request) throws CustomAuthenticationException {
        String token = request.getHeader("Authorization");
        log.info("Authorization token: " + token);
/*        if(!StringUtils.hasLength(token)) {
            log.info("令牌为空");
            response.setContentType("application/json;charset=utf-8");
            Result err = Result.error(ResultEnum.USER_NEED_AUTH.getCode(), ResultEnum.USER_NEED_AUTH.getMsg());
            String res = JSONObject.toJSONString(err);
            response.getWriter().write(res);
        }*/
        LoginUser loginUser = null;
        try {
            Claims claims = JwtUtils.parseJwt(token);
            String loginUserString = claims.getSubject();
            loginUser = JSON.parseObject(loginUserString, LoginUser.class);
        } catch (ExpiredJwtException e) {
            log.info("令牌过期");
            throw new CustomAuthenticationException(ResultEnum.TOKEN_EXPIRED.getMsg());
        }catch (Exception e) {
            log.info("令牌无效或空，需要登录");
            throw new CustomAuthenticationException(ResultEnum.USER_NEED_AUTH.getMsg());
        }
        if(redisTemplate.opsForValue().get(loginUser.getUsername()) == null) {
            log.info("已登出，需要重新登录");
            throw new InsufficientAuthenticationException(null);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
