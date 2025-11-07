package guat.tsdrs.mapper;

import guat.tsdrs.pojo.vo.PointsInfoVO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface PointsMapper {
    
    @Select("SELECT * FROM member_points WHERE username = #{username}")
    PointsInfoVO findByUsername(String username);
    
    @Update("UPDATE member_points SET points = points + 1 WHERE username = #{username}")
    int incrementPoints(String username);
    
    @Update("UPDATE member_points SET points = points - #{points}, amount = amount + #{quota} WHERE username = #{username} AND points >= #{points}")
    int exchange(@Param("username") String username, @Param("points") int points, @Param("quota") int quota);
    
    @Insert("INSERT INTO member_points(username, points, amount) VALUES(#{username}, 0, 50)")
    int insert(String username);
    
    @Update("UPDATE member_points SET amount = amount - 1 WHERE username = #{username} AND amount > 0")
    int decrementAmount(String username);

    @Update("UPDATE member_points SET amount = amount + #{amount} WHERE username = #{username}")
    void payForAmount(String username, int amount);
} 