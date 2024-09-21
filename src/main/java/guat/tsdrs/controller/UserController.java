package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.service.UserService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
