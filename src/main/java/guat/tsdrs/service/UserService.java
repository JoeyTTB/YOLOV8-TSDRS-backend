package guat.tsdrs.service;

import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;

public interface UserService {

    public String login(UserLoginDTO userLoginDTO);

    public Integer register(RegisterDTO registerDTO);
}
