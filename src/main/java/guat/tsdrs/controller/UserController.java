package guat.tsdrs.controller;

import com.alibaba.fastjson.JSON;
import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserCheckLoginDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.pojo.vo.LoginUser;
import guat.tsdrs.service.UserService;
import guat.tsdrs.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/account")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        String jwt = userService.login(userLoginDTO);
        return Result.success(ResultEnum.LOGIN_SUCCESS.getCode() ,ResultEnum.LOGIN_SUCCESS.getMsg(), jwt);
    }

    @PostMapping("/register")
    public Result register(@RequestBody RegisterDTO registerDTO) {
        Integer code = userService.register(registerDTO);
        if(code == ResultEnum.REGISTER_SUCCESS.getCode()) {
            return Result.success(ResultEnum.REGISTER_SUCCESS.getCode(), ResultEnum.REGISTER_SUCCESS.getMsg());
        } else if(code == ResultEnum.INCORRECT_PASSWORD_CHECK.getCode()) {
            return Result.error(ResultEnum.INCORRECT_PASSWORD_CHECK.getCode(), ResultEnum.INCORRECT_PASSWORD_CHECK.getMsg());
        } else {
            return Result.error(ResultEnum.USER_HAD_EXIST.getCode(), ResultEnum.USER_HAD_EXIST.getMsg());
        }
    }

    @GetMapping("/logout/{username}")
    public Result logout(@PathVariable String username) {
        userService.logout(username);
        return Result.success(ResultEnum.USER_LOGOUT_SUCCESS.getCode(), ResultEnum.USER_LOGOUT_SUCCESS.getMsg());
    }

    @GetMapping("/check/{username}")
    public Result check(@PathVariable String username, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("token: " + token);
        try {
            Claims claims = JwtUtils.parseJwt(token);
            String loginUserString = claims.getSubject();
            LoginUser loginUser = JSON.parseObject(loginUserString, LoginUser.class);
            System.out.println("LoginUser username:" + loginUser.getUsername() + " username:" + username);
            if(!loginUser.getUsername().equals(username)) {
                throw new RuntimeException();
            }
            return Result.success(ResultEnum.LOGIN_SUCCESS.getCode(), ResultEnum.LOGIN_SUCCESS.getMsg());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/regdate/{username}")
    public Result regdate(@PathVariable String username) {
        String regdate = userService.getRegDate(username);
        return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), regdate);
    }

    @PostMapping("/upload/{username}")
    public Result upload(@PathVariable String username, MultipartFile file) {
        String url = userService.uploadAvatar(username, file);
        return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), url);
    }

    @GetMapping("/avatar/{username}")
    public Result getAvatar(@PathVariable String username) {
        String url = userService.getAvatar(username);
        return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), url);
    }
}
