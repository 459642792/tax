package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageRecipient;
import com.blueteam.entity.po.SettlementRecords;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SettlementRecordsService {
    /**
     * 保存结算信息
     *
     * @param
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    Map<String, Object> insertSettlementRecords(Integer vendorInfoId, BigDecimal amounts, Date startDate, Date endDate);

    /**
     * 获取所有商家结算记录
     *
     * @param pageSize
     * @param pageIndex
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    List<Map<String, Object>> listLimitVendorSettlementRecords(Integer pageSize, Integer pageIndex, String tradingArea,
                                                               String vendorName, Date beginTime, Date endTime);

    /**
     * 获取所有商家结算条数
     *
     * @param tradingArea
     * @param vendorName
     * @param beginTime
     * @param endTime
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    int countVendorSettlementRecords(String tradingArea,
                                     String vendorName, Date beginTime, Date endTime);

    /**
     * 获取某个商家结算详情
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    List<Map<String, Object>> listSettlementRecords(Integer pageSize, Integer pageIndex, Integer vendorInfoId);

    /**
     * 方法的功能描述:TODO 获取商家详情（名称，交易数据）
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 10:19
     * @since 1.3.0
     */
    BaseResult getVendorInfoSettlement(Integer vendorIndoId);

    /**
     * 方法的功能描述:TODO 获取商家待结算数据
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 11:55
     * @since 1.3.0
     */
    BaseResult listVendorInfoForTheSettlement(Integer pageSize, Integer pageIndex, Integer vendorInfoId, Date startDate, Date endDate);

    /**
     * 方法的功能描述:TODO获取商家待结算数据
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 15:03
     * @since 1.3.0
     */
    BaseResult getSettlementDate(Integer vendorIndoId);

    /**
     * 方法的功能描述:TODO 获取商家结算记录p3
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 17:10
     * @since 1.3.0
     */
    BaseResult getSettlementRecords(Integer pageSize, Integer pageInde, Integer vendorIndoId);

    /**
     * 方法的功能描述:TODO 获取 消息接收者实体
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/22 15:11
     * @since 1.3.0
     */
    MessageRecipient getMessageRecipient(Integer settlementRecordsId);


    /**
     * 根据结算记录ID获取结算记录
     *
     * @param id
     * @return
     */
    SettlementRecords getSettlementRecordById(Integer id);
}
