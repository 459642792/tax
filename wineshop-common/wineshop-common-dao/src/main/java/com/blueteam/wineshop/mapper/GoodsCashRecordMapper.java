package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.GoodsCashRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商家兑换记录表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface GoodsCashRecordMapper {

    /**
     * 保存兑换记录
     *
     * @param goodsCashRecord
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    Integer saveGoodsCashRecord(GoodsCashRecord goodsCashRecord);

    /**
     * 发货兑换商品
     *
     * @param
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer updateGoodsCashRecord(GoodsCashRecord goodsCashRecord);

    /**
     * 获取兑换商品详情
     *
     * @param id
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    Map<String, Object> getGoodsCashRecord(@Param("id") Integer id);


    /**
     * 获取兑换商品列表
     *
     * @param pageSize
     * @param pageIndex
     * @param orderNumber
     * @param vendorInfoName
     * @param cashStatus
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    List<Map<String, Object>> listGoodsCashRecord(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex,
                                                  @Param("orderNumber") String orderNumber, @Param("vendorInfoName") String vendorInfoName,
                                                  @Param("cashStatus") Integer cashStatus, @Param("beginTime") Date beginTime,
                                                  @Param("endTime") Date endTime, @Param("vendorInfoId") Integer vendorInfoId, @Param("tradedGoodsId") Integer tradedGoodsId);

    /**
     * 获取商品列表总数
     *
     * @param pageSize
     * @param pageIndex
     * @param orderNumber
     * @param vendorInfoName
     * @param cashStatus
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    int listCountGoodsCashRecord(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex,
                                 @Param("orderNumber") String orderNumber, @Param("vendorInfoName") String vendorInfoName,
                                 @Param("cashStatus") Integer cashStatus, @Param("beginTime") Date beginTime,
                                 @Param("endTime") Date endTime, @Param("vendorInfoId") Integer vendorInfoId, @Param("tradedGoodsId") Integer tradedGoodsId);

    /**
     * 获取单个对象详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    GoodsCashRecord getGoodsCashRecords(@Param("goodsCashRecordId") Integer goodsCashRecordId);


    /**
     * 根据兑换记录获取兑换信息 包括使用酒币，剩余酒币，商品名称等
     *
     * @param id
     * @return
     */
    GoodsCashRecord selectCashByID(@Param("id") Integer id);

}
