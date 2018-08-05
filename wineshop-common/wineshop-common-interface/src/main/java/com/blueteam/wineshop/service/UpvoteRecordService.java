package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.UpVoteRecord;

/**
 * @author Marx
 * <p>
 * CityInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface UpvoteRecordService {

    /**
     * 新增点赞记录
     *
     * @param upVoteRecord
     * @return
     */
    int insertUpvoteRecord(UpVoteRecord upVoteRecord);

    /**
     * 删除点赞记录（取消点赞记录）
     *
     * @param ForeignKey
     * @param UserId
     * @return
     */
    int deleteUpvoteRecord(Integer ForeignKey, Integer UserId);

    /**
     * 防止多次点击
     */
    int RecordCount(Integer ForeignKey, Integer UserId);

    /**
     * 查询点赞信息（查看该用户是否点赞	）
     *
     * @param ForeignKey
     * @param UserId
     * @return
     */
    UpVoteRecord upvoteRecordDetail(Integer ForeignKey, Integer UserId);

    /**
     * 删除所有的点赞信息
     *
     * @param ForeignKey
     * @return
     */
    int deleteUpvote(Integer ForeignKey);

    /**
     * 查询点赞记录
     */
    UpVoteRecord getRecord(Integer Id);


    /**
     * 根据点赞记录ID
     *
     * @param id 点赞记录ID
     * @return
     */
    UpVoteRecord selectDiscoverTitleAndNikeName(Integer id);

    /**
     * 获取普通用户接收者信息
     *
     * @param id
     * @return
     */
    MessageRecipient getEveryUser(Integer id);
}
