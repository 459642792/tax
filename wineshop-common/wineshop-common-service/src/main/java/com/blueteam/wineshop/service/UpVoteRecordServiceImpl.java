package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.UpVoteRecordMapper;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.SmallRoutineComment;
import com.blueteam.entity.po.UpVoteRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpVoteRecordServiceImpl implements UpvoteRecordService {

    @Autowired
    UpVoteRecordMapper upVoteRecordMapper;

    /**
     * 评论点赞
     */
    @Autowired
    private SmallRoutineCommentService smallRoutineCommentService;

    /**
     * 新增点赞记录
     */
    @Override
    public int insertUpvoteRecord(UpVoteRecord upVoteRecord) {
        return upVoteRecordMapper.insertUpvoteRecord(upVoteRecord);
    }

    /**
     * 修改点赞记录（取消点赞信息）
     */
    @Override
    public int deleteUpvoteRecord(Integer ForeignKey, Integer UserId) {
        return upVoteRecordMapper.deleteUpvoteRecord(ForeignKey, UserId);
    }

    /**
     * 防止多次点击
     */
    @Override
    public int RecordCount(Integer ForeignKey, Integer UserId) {
        return upVoteRecordMapper.RecordCount(ForeignKey, UserId);
    }

    /**
     * 查询是否点赞
     */
    @Override
    public UpVoteRecord upvoteRecordDetail(Integer ForeignKey, Integer UserId) {
        return upVoteRecordMapper.upvoteRecordDetail(ForeignKey, UserId);
    }

    /**
     * 删除所有的点赞信息
     */
    @Override
    public int deleteUpvote(Integer ForeignKey) {
        return upVoteRecordMapper.deleteUpvote(ForeignKey);
    }

    @Override
    public UpVoteRecord getRecord(Integer Id) {
        return upVoteRecordMapper.getRecord(Id);
    }


    /**
     * 根据点赞记录ID
     *
     * @param id 点赞记录ID
     * @return
     */
    @Override
    public UpVoteRecord selectDiscoverTitleAndNikeName(Integer id) {
        return upVoteRecordMapper.selectDiscoverTitleAndNikeName(id);
    }

    @Override
    public MessageRecipient getEveryUser(Integer id) {
        MessageRecipient messageRecipient = new MessageRecipient();
        UpVoteRecord upVoteRecord = upVoteRecordMapper.getRecord(id);
        if (upVoteRecord == null)
            return messageRecipient;

        SmallRoutineComment smallRoutineComment = smallRoutineCommentService.CommentDetail(upVoteRecord.getForeignKey());
        if (smallRoutineComment == null)
            return messageRecipient;

        messageRecipient.setUserId(smallRoutineComment.getUserId());

        return messageRecipient;
    }
}
