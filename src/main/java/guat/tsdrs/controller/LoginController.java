package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import guat.tsdrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        String jwt = userService.login(userLoginDTO);
        return Result.success(ResultEnum.LOGIN_SUCCESS.getCode() ,ResultEnum.LOGIN_SUCCESS.getMsg(), jwt);
    }
}
