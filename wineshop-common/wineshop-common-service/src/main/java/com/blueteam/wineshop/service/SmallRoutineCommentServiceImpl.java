package com.blueteam.wineshop.service;

import com.blueteam.wineshop.mapper.SmallRoutineCommentMapper;
import com.blueteam.entity.po.SmallRoutineComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmallRoutineCommentServiceImpl implements SmallRoutineCommentService {

    @Autowired
    SmallRoutineCommentMapper smallRoutineCommentMapper;

    /**
     * 新增评论信息
     */
    @Override
    public int insertComment(SmallRoutineComment smallRoutineComment) {
        return smallRoutineCommentMapper.insertComment(smallRoutineComment);
    }

    /**
     * 查询评论列表
     */
    @Override
    public List<SmallRoutineComment> CommentListC(Integer ForeignKey, String Type, Integer pageSize,
                                                  Integer pageIndex) {
        return smallRoutineCommentMapper.CommentListC(ForeignKey, Type, pageSize, pageIndex);
    }

    /**
     * 查询评论总条数
     */
    @Override
    public int CommentCount(Integer ForeignKey, String Type) {
        return smallRoutineCommentMapper.CommentCount(ForeignKey, Type);
    }

    /**
     * 修改评论点赞数
     */
    @Override
    public int updateUpVote(Integer Id, Integer UpVote) {
        return smallRoutineCommentMapper.updateUpVote(Id, UpVote);
    }

    /**
     * 详情信息
     */
    @Override
    public SmallRoutineComment CommentDetail(Integer Id) {
        return smallRoutineCommentMapper.CommentDetail(Id);
    }


    /**
     * 删除所有的评论信息
     */
    @Override
    public int deleteComment(Integer Id) {
        return smallRoutineCommentMapper.deleteComment(Id);
    }
}

