package guat.tsdrs.mapper;

import guat.tsdrs.pojo.dto.FeedbackDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface FeedbackMapper {
    
    /**
     * 添加反馈意见
     */
    @Insert("INSERT INTO opinions (username, opinion_content, created_at) " +
            "VALUES (#{username}, #{opinion_content}, NOW())")
    int insert(FeedbackDTO feedbackDTO);
    
    /**
     * 验证记录是否存在且属于该用户
     */
    @Select("SELECT COUNT(*) FROM opinions WHERE id = #{id} AND username = #{username}")
    int validateOwnership(@Param("id") Long id, @Param("username") String username);
    
    /**
     * 更新反馈意见
     */
    @Update("UPDATE opinions SET opinion_content = #{opinion_content} " +
            "WHERE id = #{id} AND username = #{username}")
    int update(FeedbackDTO feedbackDTO);
    
    /**
     * 根据ID查询用户名
     */
    @Select("SELECT username FROM opinions WHERE id = #{id}")
    String findUsernameById(Long id);
    
    /**
     * 删除反馈意见
     */
    @Delete("DELETE FROM opinions WHERE id = #{id} AND username = #{username}")
    int delete(@Param("id") Long id, @Param("username") String username);
    
    /**
     * 查询反馈列表（支持内容搜索）
     */
    @Select("<script>" +
            "SELECT * FROM opinions WHERE 1=1 " +
            "<if test='content != null and content != \"\"'>" +
            "AND opinion_content LIKE CONCAT('%', #{content}, '%') " +
            "</if>" +
            "ORDER BY created_at DESC" +
            "</script>")
    List<Map<String, Object>> findList(@Param("content") String content);
}
