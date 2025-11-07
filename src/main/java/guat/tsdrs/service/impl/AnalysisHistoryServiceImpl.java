package guat.tsdrs.service.impl;

import guat.tsdrs.mapper.AnalysisHistoryMapper;
import guat.tsdrs.pojo.AnalysisHistory;
import guat.tsdrs.service.AnalysisHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnalysisHistoryServiceImpl implements AnalysisHistoryService {

    @Autowired
    private AnalysisHistoryMapper analysisHistoryMapper;

    @Override
    public List<AnalysisHistory> getHistoryByTimeRange(String startTime, String endTime, String username) {
        return analysisHistoryMapper.findByTimeRange(startTime, endTime, username);
    }

    @Override
    @Transactional
    public boolean deleteHistory(String fileName) {
        return analysisHistoryMapper.deleteByFileName(fileName) > 0;
    }

    @Override
    @Transactional
    public boolean batchDeleteHistory(List<String> fileNames) {
        if (fileNames == null || fileNames.isEmpty()) {
            return false;
        }
        return analysisHistoryMapper.batchDelete(fileNames) > 0;
    }

    @Override
    @Transactional
    public boolean saveHistory(String fileName, String fileType, String analysisResult, String username) {
        if (fileName == null || fileName.isEmpty() || 
            fileType == null || fileType.isEmpty() || 
            analysisResult == null || analysisResult.isEmpty()) {
            return false;
        }
        return analysisHistoryMapper.insert(fileName, fileType, analysisResult, username) > 0;
    }
} 