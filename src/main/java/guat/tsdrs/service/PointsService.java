package guat.tsdrs.service;

import guat.tsdrs.pojo.dto.PointsExchangeDTO;
import guat.tsdrs.pojo.vo.PointsInfoVO;

public interface PointsService {
    PointsInfoVO getPointsInfo(String username);
    void exchangePoints(PointsExchangeDTO exchangeDTO);
    boolean updatePoints(String username);
} 