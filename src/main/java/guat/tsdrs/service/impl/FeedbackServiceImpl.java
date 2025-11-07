package guat.tsdrs.service.impl;

import guat.tsdrs.mapper.FeedbackMapper;
import guat.tsdrs.pojo.dto.FeedbackDTO;
import guat.tsdrs.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    @Transactional
    public void addFeedback(FeedbackDTO feedbackDTO) {
        // 验证内容长度
        if (feedbackDTO.getOpinion_content() == null || 
            feedbackDTO.getOpinion_content().length() > 50) {
            throw new RuntimeException("意见内容不能为空且不能超过50字");
        }

        int inserted = feedbackMapper.insert(feedbackDTO);

        if (inserted == 0) {
            throw new RuntimeException("添加失败");
        }
    }

    @Override
    @Transactional
    public void updateFeedback(FeedbackDTO feedbackDTO) {
        // 验证内容长度
        if (feedbackDTO.getOpinion_content() == null || 
            feedbackDTO.getOpinion_content().length() > 50) {
            throw new RuntimeException("意见内容不能为空且不能超过50字");
        }

        // 验证记录是否存在且属于该用户
        int count = feedbackMapper.validateOwnership(feedbackDTO.getId(), feedbackDTO.getUsername());
        
        if (count == 0) {
            throw new RuntimeException("无效的记录或无权限修改");
        }

        int updated = feedbackMapper.update(feedbackDTO);

        if (updated == 0) {
            throw new RuntimeException("更新失败");
        }
    }

    @Override
    @Transactional
    public void deleteFeedback(Long id) {
        // 验证记录是否存在
        try {
            String username = feedbackMapper.findUsernameById(id);
            if (username == null) {
                throw new RuntimeException("记录不存在");
            }
            
            // 确保用户只能删除自己的意见
            int deleted = feedbackMapper.delete(id, username);

            if (deleted == 0) {
                throw new RuntimeException("删除失败或无权限删除");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除失败：" + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getFeedbackList(String content) {
        return feedbackMapper.findList(content);
    }
} 