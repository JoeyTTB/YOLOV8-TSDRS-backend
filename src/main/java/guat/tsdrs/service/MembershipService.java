package guat.tsdrs.service;

import guat.tsdrs.pojo.dto.PaymentDTO;
import guat.tsdrs.pojo.dto.RechargeDTO;
import guat.tsdrs.pojo.dto.UpdateMembershipDTO;
import java.util.Map;

public interface MembershipService {
    void recharge(RechargeDTO rechargeDTO);
    Map<String, Object> getMemberInfo(String username, String startTime, String endTime);
    void updateMembership(UpdateMembershipDTO updateDTO);
    void processPayment(PaymentDTO paymentDTO);
    void deleteRecord(Long id);
} 