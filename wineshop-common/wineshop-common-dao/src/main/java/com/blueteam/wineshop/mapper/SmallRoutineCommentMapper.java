package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.SmallRoutineComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Marx
 */
public interface SmallRoutineCommentMapper {

    /**
     * 新增评论信息
     *
     * @param smallRoutineComment
     * @return
     */
    int insertComment(SmallRoutineComment smallRoutineComment);

    /**
     * 查询发现的评论列表
     *
     * @param ForeignKey
     * @param Type
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<SmallRoutineComment> CommentListC(@Param("ForeignKey") Integer ForeignKey, @Param("Type") String Type, @Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex);

    /**
     * 对发现的评论条数进行统计
     *
     * @param ForeignKey
     * @param Type
     * @return
     */
    int CommentCount(@Param("ForeignKey") Integer ForeignKey, @Param("Type") String Type);

    /**
     * 修改发布的点赞数量
     *
     * @param UpVote
     * @return
     */
    int updateUpVote(@Param("Id") Integer Id, @Param("UpVote") Integer UpVote);

    /**
     * 查询评论详细信息
     */
    SmallRoutineComment CommentDetail(@Param("Id") Integer Id);

    /**
     * 删除评论信息
     */
    int deleteComment(@Param("Id") Integer Id);
}
