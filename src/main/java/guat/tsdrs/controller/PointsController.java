package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.PointsExchangeDTO;
import guat.tsdrs.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
public class PointsController {

    @Autowired
    private PointsService pointsService;

    @GetMapping("/info/{username}")
    public Result getPointsInfo(@PathVariable String username) {
        try {
            return Result.success(ResultEnum.SUCCESS.getCode(), "获取成功", 
                pointsService.getPointsInfo(username));
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "获取失败：" + e.getMessage());
        }
    }

    @PostMapping("/exchange")
    public Result exchangePoints(@RequestBody PointsExchangeDTO exchangeDTO) {
        try {
            pointsService.exchangePoints(exchangeDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "兑换成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "兑换失败：" + e.getMessage());
        }
    }

    @PostMapping("/update/{username}")
    public Result updatePoints(@PathVariable String username) {
        try {
            boolean success = pointsService.updatePoints(username);
            if (success) {
                return Result.success(ResultEnum.SUCCESS.getCode(), "积分更新成功");
            } else {
                return Result.error(ResultEnum.ERROR.getCode(), "识别额度不足");
            }
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "积分更新失败：" + e.getMessage());
        }
    }
} 