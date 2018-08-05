package com.blueteam.wineshop.service;

import com.blueteam.entity.po.SmallRoutineComment;

import java.util.List;

/**
 * @author Marx
 */
public interface SmallRoutineCommentService {

    /**
     * C端发现信息进行评论
     *
     * @param smallRoutineComment
     * @return
     */
    int insertComment(SmallRoutineComment smallRoutineComment);

    /**
     * C端评论信息进行查询
     *
     * @param ForeignKey
     * @param Type
     * @param pageSize
     * @return
     */
    List<SmallRoutineComment> CommentListC(Integer ForeignKey, String Type, Integer pageSize, Integer pageIndex);

    /**
     * 对发现的评论条数进行统计
     *
     * @param ForeignKey
     * @param Type
     * @return
     */
    int CommentCount(Integer ForeignKey, String Type);

    /**
     * 修改发布的点赞数量
     *
     * @param UpVote
     * @return
     */
    int updateUpVote(Integer Id, Integer UpVote);

    /**
     * 查询评论详细信息
     *
     * @param Id
     * @return
     */
    SmallRoutineComment CommentDetail(Integer Id);

    /**
     * 删除评论信息
     *
     * @param Id
     * @return
     */
    int deleteComment(Integer Id);
}
