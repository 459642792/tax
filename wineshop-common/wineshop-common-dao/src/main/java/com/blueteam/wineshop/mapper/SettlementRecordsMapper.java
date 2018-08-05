package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.SettlementRecords;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface SettlementRecordsMapper {

    /**
     * 保存结算信息
     *
     * @param settlementRecords
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    void insertSettlementRecords(SettlementRecords settlementRecords);

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
    List<Map<String, Object>> listLimitVendorSettlementRecords(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("tradingArea") String tradingArea,
                                                               @Param("vendorName") String vendorName, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

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
    int countVendorSettlementRecords(@Param("tradingArea") String tradingArea,
                                     @Param("vendorName") String vendorName, @Param("beginTime") Date beginTime, @Param("endTime") Date endTime);

    /**
     * 获取某个商家结算详情
     *
     * @param vendorInfoId
     * @return
     * @author xiaojiang 2017年4月7日
     * @version 1.0
     * @since 1.0 2017年4月7日
     */
    List<Map<String, Object>> listSettlementRecords(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("vendorInfoId") Integer vendorInfoId);

    int listCountSettlementRecords(@Param("vendorInfoId") Integer vendorInfoId);

    /**
     * 方法的功能描述:TODO 获取商家详情（名称，交易数据）
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 10:19
     * @since 1.3.0
     */
    List<Map<String, Object>> getVendorInfoSettlement(@Param("vendorInfoId") Integer vendorInfoId);

    /**
     * 方法的功能描述:TODO 根据商家id 时间 获取结算信息（已结算 未结算）
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 15:04
     * @since 1.3.0
     */
    List<Map<String, Object>> listVendorInfoForTheSettlement(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("vendorInfoId") Integer vendorInfoId,
                                                             @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Map<String, Object> listCountVendorInfoForTheSettlement(@Param("vendorInfoId") Integer vendorInfoId,
                                                            @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    /**
     * 方法的功能描述:TODO 获取已结算时间
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 15:05
     * @since 1.3.0
     */
    Map<String, Object> getSettlementDate(@Param("vendorInfoId") Integer vendorInfoId);

    /**
     * 方法的功能描述:TODO 获取商家结算记录p3
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/5/15 17:10
     * @since 1.3.0
     */
    List<Map<String, Object>> getSettlementRecords(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("vendorInfoId") Integer vendorInfoId);

    int getCountSettlementRecords(@Param("vendorInfoId") Integer vendorInfoId);

    Map<String, Object> getSettlementRecord(@Param("settlementRecordId") Integer settlementRecordId);

    /**
     * 根据结算记录ID获取结算记录
     *
     * @param id
     * @return
     */
    SettlementRecords getSettlementRecordById(@Param("id") Integer id);
}
