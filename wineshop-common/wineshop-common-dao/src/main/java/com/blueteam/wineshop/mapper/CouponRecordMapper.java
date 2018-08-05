package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.CouponRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponRecordDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Repository
public interface CouponRecordMapper {
    /**
     * @param CouponId
     * @param UserId
     * @return
     */
    List<CouponRecord> CouponRecordList(@Param("CouponId") int CouponId, @Param("UserId") int UserId);

    /**
     * 创建优惠券记录
     *
     * @param couponRecord
     * @return
     */
    int insertCouponRecord(CouponRecord couponRecord);

    /**
     * 查询优惠券消费信息
     *
     * @param couponId
     * @return
     */
    CouponRecord getCouponRecord(int couponId);

    /**
     * 查询优惠券信息
     *
     * @param couponId
     * @return
     */
    int vendorCouponRecordListCount(Integer couponId);

    /**
     * 查询优惠券信息
     *
     * @param couponId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<CouponRecord> vendorCouponRecordList(@Param("couponId") Integer couponId,
                                              @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 查询优惠券信息
     *
     * @param couponId
     * @return
     */
    List<CouponRecord> findByCouponId(Integer couponId);

    /**
     * 优惠券使用记录 物理删除 为了保证优惠券 couponId唯一
     *
     * @param couponInfoId
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    void removeCouponRecord(@Param("couponInfoId") int couponInfoId);
}
