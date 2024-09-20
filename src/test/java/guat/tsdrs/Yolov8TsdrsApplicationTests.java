package guat.tsdrs;

import guat.tsdrs.common.ResultEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class Yolov8TsdrsApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        System.out.println(ResultEnum.SUCCESS.getCode() + " " + ResultEnum.SUCCESS.getMsg());
    }

}
