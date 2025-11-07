package guat.tsdrs.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RechargeDTO {
    private String username;
    private Integer amount;
    private String memberType;
} 