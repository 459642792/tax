package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.CurrencyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 酒币明细表
 *
 * @author xiaojiang 2017年4月19日
 * @version 1.0
 * @since 1.0 2017年4月19日
 */
public interface CurrencyRecordMapper {
    /**
     * 查询当前用户当天一共产生多少枚酒币
     *
     * @param userInfoId
     * @param status
     * @return
     * @author xiaojiang 2017年4月20日
     * @version 1.0
     * @since 1.0 2017年4月20日
     */
    Integer countUserInfoCurrencyRecord(@Param("userInfoId") Integer userInfoId, @Param("vendorInfoId") Integer vendorInfoId, @Param("status") int status);

    /**
     * 保存酒币
     *
     * @param currencyRecord
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    int saveCurrencyRecord(CurrencyRecord currencyRecord);

    /**
     * 该商家剩余多少个酒币
     *
     * @param
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    Integer countVendorInfoCurrencyRecord(@Param("vendorInfoId") Integer vendorInfoId);

    /**
     * 获取酒币记录
     *
     * @param
     * @return
     * @author xiaojiang 2017年4月21日
     * @version 1.0
     * @since 1.0 2017年4月21日
     */
    List<CurrencyRecord> listCurrencyRecord(@Param("pageSize") Integer pageSize,
                                            @Param("pageIndex") Integer pageIndex,
                                            @Param("vendorInfoId") Integer vendorInfoId);

    int listCountCurrencyRecord(@Param("vendorInfoId") Integer vendorInfoId);

}
