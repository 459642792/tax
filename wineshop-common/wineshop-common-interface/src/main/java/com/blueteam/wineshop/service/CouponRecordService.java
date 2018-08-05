package com.blueteam.wineshop.service;

import com.blueteam.entity.po.CouponRecord;
import com.blueteam.entity.dto.PageResult;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponRecordService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface CouponRecordService {

    /**
     * @param CouponId
     * @param UserId
     * @return
     */
    List<CouponRecord> CouponRecordList(int CouponId, int UserId);

    /**
     * 查询商家优惠券记录
     *
     * @param couponId
     * @param pageSize
     * @param pageIndex
     * @return
     */
    PageResult<List<CouponRecord>> vendorCouponRecordList(Integer couponId, Integer pageIndex, Integer pageSize);
}
