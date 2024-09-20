package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @GetMapping("/hello")
    public Result hello() {
        System.out.println("hello已执行");
        return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), "Hello World");
    }
}
