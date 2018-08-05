package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.ScoreInfo;

import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * JiFenInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ScoreInfoService {

    /**
     * @param VendorId
     * @return
     */
    List<ScoreInfo> ScoreInfoList(int VendorId);

    /**
     * @param VendorId
     * @param Type
     * @return
     */
    List<ScoreInfo> CommonInfoList(int VendorId, String Type);

    /**
     * 查询商家回复
     *
     * @param
     * @param Type
     * @return
     */
    List<ScoreInfo> VendorBackList(String OrderNo, String Type);

    /**
     * @param score
     * @return
     */
    int insertComment(ScoreInfo score);

    /**
     * @param OrderNo
     * @param UserId
     * @return
     */
    ScoreInfo ScoreInfo(String OrderNo, int UserId, String Type);

    /**
     * 评论订单列表
     *
     * @param OrderNo
     * @param
     * @return
     */
    List<ScoreInfo> CommonOrderList(String OrderNo);

    /**
     * 某个商家获取其下所有的评论 、评分
     *
     * @param vendorId
     * @param type
     */
    Map<String, Object> findAllVendorScore(Integer vendorId, String type, Integer pageIndex, Integer pageSize, String vendorInfoType) throws Exception;

    /**
     * 方法的功能描述:TOD ps根据id获取商家评论
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/17 13:54
     * @since 1.3.0
     */
    ApiResult<List<Map<String, Object>>> findNewAllVendorScore(Integer vendorId, String type, Integer pageIndex, Integer pageSize, String vendorInfoType) throws Exception;

    ApiResult<List<ScoreInfo>> findAllVendorScore4List(Integer vendorId, String type, Integer pageIndex, Integer pageSize) throws Exception;

    /**
     * 方法的功能描述:TODO 获取 消息商家接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    MessageRecipient getMessageRecipient(Integer scoreInfoId);

    /**
     * 方法的功能描述:TODO 获取 消息用户接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    MessageRecipient getUserMessageRecipient(Integer scoreInfoId);


    /**
     * 获取评价内容，type,以及商家和用户名称
     *
     * @param id 评论回复ID
     * @return
     */
    ScoreInfo selectScoreAndUserByID(Integer id);

    /**
     * 数量(评论的数量)
     *
     * @return
     */
    int GetScoreCount(Integer VendorId, String Type);


}
