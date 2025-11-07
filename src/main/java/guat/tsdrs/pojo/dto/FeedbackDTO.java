package guat.tsdrs.pojo.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Long id;                // 意见ID
    private String username;        // 用户名
    private String opinion_content; // 意见内容
} 