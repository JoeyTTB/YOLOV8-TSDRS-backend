package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.PaymentDTO;
import guat.tsdrs.pojo.dto.RechargeDTO;
import guat.tsdrs.pojo.dto.UpdateMembershipDTO;
import guat.tsdrs.service.MembershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @PostMapping("/recharge")
    public Result recharge(@RequestBody RechargeDTO rechargeDTO) {
        try {
            membershipService.recharge(rechargeDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "充值成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "充值失败：" + e.getMessage());
        }
    }

    @GetMapping("/info/{username}")
    public Result getMemberInfo(
            @PathVariable String username,
            @RequestParam(required = false) String startTime,
            @RequestParam(required = false) String endTime) {
        try {
            return Result.success(ResultEnum.SUCCESS.getCode(), "获取成功", 
                membershipService.getMemberInfo(username, startTime, endTime));
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "获取失败：" + e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result updateMembership(@RequestBody UpdateMembershipDTO updateDTO) {
        try {
            membershipService.updateMembership(updateDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "修改成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "修改失败：" + e.getMessage());
        }
    }

    @PostMapping("/pay")
    public Result pay(@RequestBody PaymentDTO paymentDTO) {
        try {
            membershipService.processPayment(paymentDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "支付成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "支付失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteRecord(@PathVariable Long id) {
        try {
            membershipService.deleteRecord(id);
            return Result.success(ResultEnum.SUCCESS.getCode(), "删除成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "删除失败：" + e.getMessage());
        }
    }
} 