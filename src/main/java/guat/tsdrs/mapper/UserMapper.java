package guat.tsdrs.mapper;

import guat.tsdrs.pojo.User;
import guat.tsdrs.pojo.vo.LoginUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user_info where username = #{username}")
    public User getUserByName(String username);
}
