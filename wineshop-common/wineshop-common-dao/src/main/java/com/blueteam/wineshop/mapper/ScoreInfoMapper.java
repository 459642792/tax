package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.ScoreInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * JiFenInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface ScoreInfoMapper {
    /**
     * @param VendorId
     * @return
     */
    List<ScoreInfo> ScoreInfoList(@Param("VendorId") int VendorId);

    /**
     * @param VendorId
     * @param Type
     * @return
     */
    List<ScoreInfo> CommonInfoList(@Param("VendorId") int VendorId, @Param("Type") String Type);

    /**
     * @return
     */
    int insertComment(ScoreInfo score);

    /**
     * 评论订单列表
     *
     * @param OrderNo
     * @return
     */
    List<ScoreInfo> CommonOrderList(@Param("OrderNo") String OrderNo);


    /**
     * 查询商家回复
     *
     * @param OrderNo
     * @param Type
     * @return
     */
    List<ScoreInfo> VendorBackList(@Param("OrderNo") String OrderNo, @Param("Type") String Type);

    /**
     * @param OrderNo
     * @param UserId
     * @return
     */
    ScoreInfo ScoreInfo(@Param("OrderNo") String OrderNo, @Param("UserId") int UserId, @Param("Type") String Type);

    int findAllVendorScoreCount(@Param("vendorId") Integer vendorId, @Param("type") String type);


    Map<String, Object> countVendorScore(@Param("vendorId") Integer vendorId, @Param("type") String type);

    List<ScoreInfo> findAllVendorScore(@Param("vendorId") Integer vendorId, @Param("type") String type,
                                       @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 方法的功能描述:TODO 根据id获取商家平方数据
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/17 14:25
     * @since 1.3.0
     */
    List<Map<String, Object>> findNewAllVendorScore(@Param("vendorId") Integer vendorId, @Param("type") String type,
                                                    @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize, @Param("vendorInfoType") String vendorInfoType);

    int findNewAllVendorScoreCount(@Param("vendorId") Integer vendorId, @Param("type") String type, @Param("vendorInfoType") String vendorInfoType);

    /**
     * 方法的功能描述:TODO 根据评论id 获取商家id，用户id,。运营商id
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 16:42
     * @since 1.3.0
     */
    Map<String, Object> getScoreInfo(@Param("scoreInfoId") Integer scoreInfoId);

    /**
     * 评论（条数）
     *
     * @return
     */
    int GetScoreCount(@Param("VendorId") Integer VendorId, @Param("Type") String Type);

    /**
     * 获取评价内容，type,以及商家和用户名称
     *
     * @param id 评论回复ID
     * @return
     */
    ScoreInfo selectScoreAndUserByID(@Param("id") Integer id);

    /**
     * 根据评论回复ID获取 被回复者用户ID
     *
     * @param id
     * @return
     */
    ScoreInfo selectBeAnsweredUserID(@Param("id") Integer id);
}
