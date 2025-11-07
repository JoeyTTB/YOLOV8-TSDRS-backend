package guat.tsdrs.service.impl;

import guat.tsdrs.mapper.PointsMapper;
import guat.tsdrs.pojo.dto.PointsExchangeDTO;
import guat.tsdrs.pojo.vo.PointsInfoVO;
import guat.tsdrs.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointsServiceImpl implements PointsService {

    @Autowired
    private PointsMapper pointsMapper;

    @Override
    public PointsInfoVO getPointsInfo(String username) {
        PointsInfoVO points = pointsMapper.findByUsername(username);
        if (points == null) {
            pointsMapper.insert(username);
            points = pointsMapper.findByUsername(username);
        }
        return points;
    }

    @Override
    @Transactional
    public void exchangePoints(PointsExchangeDTO exchangeDTO) {
        // 检查积分是否足够
        PointsInfoVO points = getPointsInfo(exchangeDTO.getUsername());
        if (points.getPoints() < exchangeDTO.getPoints()) {
            throw new RuntimeException("积分不足");
        }
        
        // 执行兑换
        int rows = pointsMapper.exchange(
            exchangeDTO.getUsername(), 
            exchangeDTO.getPoints(), 
            exchangeDTO.getQuota()
        );
        
        if (rows <= 0) {
            throw new RuntimeException("兑换失败");
        }
    }

    @Override
    @Transactional
    public boolean updatePoints(String username) {
        // 先检查用户是否有足够的额度
        PointsInfoVO points = getPointsInfo(username);
        if (points.getAmount() <= 0) {
            return false;
        }
        
        // 扣除额度并增加积分
        if (pointsMapper.decrementAmount(username) > 0) {
            return pointsMapper.incrementPoints(username) > 0;
        }
        return false;
    }
} 