package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

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
}
