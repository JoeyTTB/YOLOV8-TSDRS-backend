package guat.tsdrs.service.impl;

import guat.tsdrs.mapper.MembershipMapper;
import guat.tsdrs.mapper.PointsMapper;
import guat.tsdrs.pojo.dto.PaymentDTO;
import guat.tsdrs.pojo.dto.RechargeDTO;
import guat.tsdrs.pojo.dto.UpdateMembershipDTO;
import guat.tsdrs.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipMapper membershipMapper;
    @Autowired
    private PointsMapper pointsMapper;

    @Override
    @Transactional
    public void recharge(RechargeDTO rechargeDTO) {
        // 插入充值记录到membership_record表，初始状态为PENDING（未支付）
        int inserted = membershipMapper.insertRecharge(rechargeDTO);
        if (inserted == 0) {
            throw new RuntimeException("充值记录创建失败");
        }
        pointsMapper.payForAmount(rechargeDTO.getUsername(), rechargeDTO.getAmount());
    }

    @Override
    public Map<String, Object> getMemberInfo(String username, String startTime, String endTime) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 获取最新的已支付充值记录
            Map<String, Object> record = membershipMapper.findLatestSuccessRecord(username);
            
            // 查询所有记录（支持时间范围筛选）
            List<Map<String, Object>> allRecords = membershipMapper.findAllRecords(username, startTime, endTime);

            result.put("latestRecord", record);
            result.put("allRecords", allRecords);
            result.put("success", true);
        } catch (Exception e) {
            // 如果没有记录，返回基本信息
            result.put("latestRecord", null);
            result.put("allRecords", Collections.emptyList());
            result.put("success", false);
        }
        
        return result;
    }

    @Override
    @Transactional
    public void updateMembership(UpdateMembershipDTO updateDTO) {
        // 验证记录是否存在且属于该用户，并且状态为未支付
        int count = membershipMapper.validatePendingRecord(updateDTO.getId(), updateDTO.getUsername());
        if (count == 0) {
            throw new RuntimeException("无效的记录、无权限修改或订单已支付");
        }

        // 更新会员记录
        int updated = membershipMapper.updateMembership(updateDTO);

        if (updated == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional
    public void processPayment(PaymentDTO paymentDTO) {
        // 验证记录是否存在且属于该用户
        int count = membershipMapper.validatePendingRecord(paymentDTO.getId(), paymentDTO.getUsername());
        if (count == 0) {
            throw new RuntimeException("无效的记录或无权限支付");
        }

        // 更新支付状态
        int updated = membershipMapper.updatePaymentStatus(paymentDTO.getId());

        if (updated == 0) {
            throw new RuntimeException("支付失败");
        }
    }

    @Override
    @Transactional
    public void deleteRecord(Long id) {
        // 验证记录是否存在
        int count = membershipMapper.validateRecordExists(id);
        if (count == 0) {
            throw new RuntimeException("记录不存在");
        }

        // 删除记录
        int deleted = membershipMapper.deleteRecord(id);

        if (deleted == 0) {
            throw new RuntimeException("删除失败");
        }
    }
}