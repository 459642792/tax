package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.GoodsCashRecord;

import java.util.Date;

/**
 * 酒币记录表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface GoodsCashRecordService {
    /**
     * 兑换商品
     *
     * @param tradedGoodsId
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult cashGoods(Integer tradedGoodsId, Integer vendorInfoId);

    /**
     * 获取商家剩余酒币
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult countVendorInfoCurrencyRecord(Integer vendorInfoId);

    /**
     * 获取商家酒币记录
     *
     * @param pageSize
     * @param pageIndex
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult listVendorInfoCurrencyRecord(Integer pageSize, Integer pageIndex, Integer vendorInfoId);

    /**
     * 获取兑换记录列表
     *
     * @param pageSize
     * @param pageIndex
     * @param orderNumber
     * @param vendorInfoName
     * @param cashStatus
     * @param beginTime
     * @param endTime
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult listGoodsCashRecord(Integer pageSize, Integer pageIndex,
                                   String orderNumber, String vendorInfoName,
                                   Integer cashStatus, Date beginTime,
                                   Date endTime, Integer vendorInfoId, Integer tradedGoodsId);

    /**
     * 获取兑换商品详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    BaseResult getGoodsCashRecord(Integer goodsCashRecordId);

    /**
     * 兑换商品发货
     *
     * @param
     * @param userName
     * @param expressCompany
     * @param expressNumbers
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    BaseResult expressGoods(Integer goodsCashRecordId, String userName, String expressCompany, String expressNumbers);

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    MessageRecipient getMessageRecipient(Integer goodsCashRecordId);


    /**
     * 获取单个对象详情
     *
     * @param goodsCashRecordId
     * @return
     * @author xiaojiang 2017年4月22日
     * @version 1.0
     * @since 1.0 2017年4月22日
     */
    GoodsCashRecord getGoodsCashRecords(Integer goodsCashRecordId);

//    /**
//     * 根据兑换记录获取兑换信息 包括使用酒币，剩余酒币，商品名称等
//     *
//     * @param id
//     * @return
//     */
//    GoodsCashRecord selectCashByID(Integer id);
}
