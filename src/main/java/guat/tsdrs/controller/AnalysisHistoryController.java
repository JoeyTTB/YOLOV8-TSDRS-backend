package guat.tsdrs.controller;

import guat.tsdrs.common.ResultEnum;
import guat.tsdrs.pojo.Result;
import guat.tsdrs.pojo.AnalysisHistory;
import guat.tsdrs.service.AnalysisHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
@CrossOrigin
public class AnalysisHistoryController {

    @Autowired
    private AnalysisHistoryService analysisHistoryService;

    @GetMapping("/list/{username}")
    public Result getHistoryList(
            @PathVariable String username,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String startTime,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") String endTime) {
        try {
            List<AnalysisHistory> historyList = analysisHistoryService.getHistoryByTimeRange(startTime, endTime, username);
            return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), historyList);
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }

    @PostMapping("/delete")
    public Result deleteHistory(@RequestBody Map<String, String> params) {
        try {
            String fileName = params.get("fileName");
            if (fileName == null || fileName.isEmpty()) {
                return Result.error(ResultEnum.ERROR.getCode(), "文件名不能为空");
            }
            boolean success = analysisHistoryService.deleteHistory(fileName);
            if (success) {
                return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
            } else {
                return Result.error(ResultEnum.ERROR.getCode(), "删除失败");
            }
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }

    @PostMapping("/batch-delete")
    public Result batchDeleteHistory(@RequestBody Map<String, Object> params) {
        try {
            @SuppressWarnings("unchecked")
            List<String> fileNames = (List<String>) params.get("fileNames");
            if (fileNames == null || fileNames.isEmpty()) {
                return Result.error(ResultEnum.ERROR.getCode(), "文件名列表不能为空");
            }
            boolean success = analysisHistoryService.batchDeleteHistory(fileNames);
            if (success) {
                return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
            } else {
                return Result.error(ResultEnum.ERROR.getCode(), "批量删除失败");
            }
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }

    @PostMapping("/save")
    public Result saveHistory(@RequestBody Map<String, String> params) {
        try {
            String fileName = params.get("fileName");
            String fileType = params.get("fileType");
            String analysisResult = params.get("analysisResult");
            String username = params.get("username");

            if (fileName == null || fileName.isEmpty()) {
                return Result.error(ResultEnum.ERROR.getCode(), "文件名不能为空");
            }
            if (fileType == null || fileType.isEmpty()) {
                return Result.error(ResultEnum.ERROR.getCode(), "文件类型不能为空");
            }
            if (analysisResult == null || analysisResult.isEmpty()) {
                return Result.error(ResultEnum.ERROR.getCode(), "分析结果不能为空");
            }

            boolean success = analysisHistoryService.saveHistory(fileName, fileType, analysisResult, username);
            if (success) {
                return Result.success(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
            } else {
                return Result.error(ResultEnum.ERROR.getCode(), "保存失败");
            }
        } catch (Exception e) {
            return Result.error(ResultEnum.ERROR.getCode(), e.getMessage());
        }
    }
} 