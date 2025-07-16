package guat.tsdrs.service;

import guat.tsdrs.pojo.dto.RegisterDTO;
import guat.tsdrs.pojo.dto.UserLoginDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    public String login(UserLoginDTO userLoginDTO);

    public Integer register(RegisterDTO registerDTO);

    public void logout(String username);

    public String getRegDate(String username);

    String uploadAvatar(String username, MultipartFile file);

    String getAvatar(String username);
}
