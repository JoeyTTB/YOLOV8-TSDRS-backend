package guat.tsdrs.service;

import guat.tsdrs.pojo.dto.FeedbackDTO;
import java.util.List;
import java.util.Map;

public interface FeedbackService {
    void addFeedback(FeedbackDTO feedbackDTO);
    void updateFeedback(FeedbackDTO feedbackDTO);
    void deleteFeedback(Long id);
    List<Map<String, Object>> getFeedbackList(String content);
} 