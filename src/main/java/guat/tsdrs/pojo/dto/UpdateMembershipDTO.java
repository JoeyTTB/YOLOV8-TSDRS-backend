package guat.tsdrs.pojo.dto;

import lombok.Data;

@Data
public class UpdateMembershipDTO {
    private Long id;            // 充值记录ID
    private String username;    // 用户名
    private Integer amount;     // 新的充值金额
    private String memberType;  // 新的会员类型
} 