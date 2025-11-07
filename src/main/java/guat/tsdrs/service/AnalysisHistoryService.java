package guat.tsdrs.service;

import guat.tsdrs.pojo.AnalysisHistory;

import java.util.Date;
import java.util.List;

public interface AnalysisHistoryService {
    
    /**
     * 根据时间范围查询历史记录
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 历史记录列表
     */
    List<AnalysisHistory> getHistoryByTimeRange(String startTime, String endTime, String username);

    /**
     * 删除单条历史记录
     * @param fileName 文件名
     * @return 是否删除成功
     */
    boolean deleteHistory(String fileName);

    /**
     * 批量删除历史记录
     * @param fileNames 文件名列表
     * @return 是否删除成功
     */
    boolean batchDeleteHistory(List<String> fileNames);

    /**
     * 保存识别分析历史记录
     * @param fileName 文件名
     * @param fileType 文件类型
     * @param analysisResult 分析结果URL
     * @return 是否保存成功
     */
    boolean saveHistory(String fileName, String fileType, String analysisResult, String username);
} 