package guat.tsdrs.mapper;

import guat.tsdrs.pojo.dto.RechargeDTO;
import guat.tsdrs.pojo.dto.UpdateMembershipDTO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface MembershipMapper {
    
    /**
     * 插入充值记录
     */
    @Insert("INSERT INTO membership_record (username, amount, create_time, status, remark) " +
            "VALUES (#{username}, #{amount}, NOW(), 'PENDING', #{memberType})")
    int insertRecharge(RechargeDTO rechargeDTO);
    
    /**
     * 获取最新的已支付充值记录
     */
    @Select("SELECT * FROM membership_record " +
            "WHERE username = #{username} AND status = 'SUCCESS' " +
            "ORDER BY create_time DESC LIMIT 1")
    Map<String, Object> findLatestSuccessRecord(String username);
    
    /**
     * 查询所有充值记录（支持时间范围筛选）
     */
    @Select("<script>" +
            "SELECT * FROM membership_record WHERE username = #{username} " +
            "<if test='startTime != null and startTime != \"\"'>" +
            "AND create_time &gt;= #{startTime} " +
            "</if>" +
            "<if test='endTime != null and endTime != \"\"'>" +
            "AND create_time &lt;= #{endTime} " +
            "</if>" +
            "ORDER BY create_time DESC" +
            "</script>")
    List<Map<String, Object>> findAllRecords(@Param("username") String username,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);
    
    /**
     * 验证记录是否存在且属于该用户且状态为PENDING
     */
    @Select("SELECT COUNT(*) FROM membership_record " +
            "WHERE id = #{id} AND username = #{username} AND status = 'PENDING'")
    int validatePendingRecord(@Param("id") Long id, @Param("username") String username);
    
    /**
     * 更新会员记录
     */
    @Update("UPDATE membership_record SET amount = #{amount}, remark = #{memberType} " +
            "WHERE id = #{id}")
    int updateMembership(UpdateMembershipDTO updateDTO);
    
    /**
     * 更新支付状态为SUCCESS
     */
    @Update("UPDATE membership_record SET status = 'SUCCESS' WHERE id = #{id}")
    int updatePaymentStatus(Long id);
    
    /**
     * 验证记录是否存在
     */
    @Select("SELECT COUNT(*) FROM membership_record WHERE id = #{id}")
    int validateRecordExists(Long id);
    
    /**
     * 删除充值记录
     */
    @Delete("DELETE FROM membership_record WHERE id = #{id}")
    int deleteRecord(Long id);
}
