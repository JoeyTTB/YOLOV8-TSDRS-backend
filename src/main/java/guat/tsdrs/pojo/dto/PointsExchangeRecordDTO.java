package guat.tsdrs.pojo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PointsExchangeRecordDTO {
    private LocalDateTime exchangeTime;
    private Integer pointsConsumed;
    private Integer quotaGained;
} 