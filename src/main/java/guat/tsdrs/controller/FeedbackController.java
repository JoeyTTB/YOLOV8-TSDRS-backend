package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.dto.FeedbackDTO;
import guat.tsdrs.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public Result addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            feedbackService.addFeedback(feedbackDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "添加成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "添加失败：" + e.getMessage());
        }
    }

    @PutMapping("/update")
    public Result updateFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        try {
            feedbackService.updateFeedback(feedbackDTO);
            return Result.success(ResultEnum.SUCCESS.getCode(), "修改成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "修改失败：" + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteFeedback(@PathVariable Long id) {
        try {
            feedbackService.deleteFeedback(id);
            return Result.success(ResultEnum.SUCCESS.getCode(), "删除成功");
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "删除失败：" + e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result getFeedbackList(@RequestParam(required = false) String content) {
        try {
            return Result.success(ResultEnum.SUCCESS.getCode(), "获取成功", 
                feedbackService.getFeedbackList(content));
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), "获取失败：" + e.getMessage());
        }
    }
} 