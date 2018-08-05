package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.UpVoteRecord;
import org.apache.ibatis.annotations.Param;

/**
 * @author Marx
 * <p>
 * CityInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface UpVoteRecordMapper {

    /**
     * 新增点赞记录
     *
     * @param upVoteRecord
     * @return
     */
    int insertUpvoteRecord(UpVoteRecord upVoteRecord);

    /**
     * 删除该评论的点赞记录
     *
     * @param ForeignKey
     * @return
     */
    int deleteUpvoteRecord(@Param("ForeignKey") Integer ForeignKey, @Param("UserId") Integer UserId);

    /**
     * 防止多次点击
     *
     * @param ForeignKey
     * @param UserId
     * @return
     */
    int RecordCount(@Param("ForeignKey") Integer ForeignKey, @Param("UserId") Integer UserId);

    /**
     * 查询该优惠券的点赞记录信息
     *
     * @param ForeignKey
     * @param UserId
     * @return
     */
    UpVoteRecord upvoteRecordDetail(@Param("ForeignKey") Integer ForeignKey, @Param("UserId") Integer UserId);

    /**
     * 删除所有的点赞信息
     *
     * @param ForeignKey
     * @return
     */
    int deleteUpvote(@Param("ForeignKey") Integer ForeignKey);

    /**
     * 查询点赞记录
     */
    UpVoteRecord getRecord(@Param("Id") Integer Id);


    /**
     * 根据点赞记录ID
     *
     * @param id 点赞记录ID
     * @return
     */
    UpVoteRecord selectDiscoverTitleAndNikeName(@Param("id") Integer id);
}
