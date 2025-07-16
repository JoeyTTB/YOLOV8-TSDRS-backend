package guat.tsdrs.mapper;

import guat.tsdrs.pojo.User;
import guat.tsdrs.pojo.vo.LoginUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT user_id as userId, username, password, avatar, regdate FROM user_info WHERE username = #{username}")
    public User getUserByName(String username);

    @Insert("INSERT INTO user_info(username, password, regdate) VALUES(#{username}, #{password}, #{regdate})")
    public void insertUser(User user);

    @Select("select regdate from user_info where username = #{username}")
    public String getRegDate(String username);

    @Update("UPDATE user_info SET avatar = #{url} WHERE username = #{username}")
    void uploadAvatar(String username, String url);

    @Select("SELECT avatar from user_info WHERE username = #{username}")
    String getAvatar(String username);
}
