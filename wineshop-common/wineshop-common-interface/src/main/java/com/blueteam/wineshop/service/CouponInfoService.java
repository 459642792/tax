package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.CouponInfo;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.SimpleCouponInfoVo;

import java.util.List;

/**
 * @author Marx
 * <p>
 * CouponInfoService.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public interface CouponInfoService {

    /**
     * @param ForeignKey
     * @return
     */
    List<CouponInfo> CouponInfoList(int ForeignKey);

    /**
     * @param ForeignKey
     * @return
     */
    List<CouponInfo> CouponInfoListDetail(int ForeignKey,String cityCode, String Type);

    /**
     * @param Id
     * @return
     */
    CouponInfo couponDetail(int Id);

    /**
     * @param coupon
     * @return
     */
    int insertCoupon(CouponInfo coupon);

    /**
     * @param
     * @param Type
     * @return
     */
    List<CouponInfo> UserCouponList(int UserId, String Type);

    /**
     * 优惠券过期
     *
     * @param
     * @param Type
     * @return
     */
    List<CouponInfo> UserCouponLists(int VendorId, int UserId, String Type);

    /**
     * 查询用户锁领取咯优惠卷没有
     */
    List<CouponInfo> checkCoupon(int UserId, String Type, int Id);

    /**
     * 修改优惠券状态
     *
     * @param id
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    void updateCouponInfo(String costStatus, Integer id);

    int update(CouponInfo model);

    /**
     * 获取当前用户在该商铺的优惠券列表
     *
     * @param vendorInfoId
     * @param userId
     * @param totalMoney
     * @return
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    BaseResult listCouponInfoVendorInfo(int vendorInfoId, int userId, Double totalMoney);


    PageResult<List<SimpleCouponInfoVo>> vendorCouponList(Integer vendorId, String couponType, Integer pageIndex, Integer pageSize);

    boolean isLegalTime(String type, int vendorId, String beginTime, String endTime, int id);

    /**
     * 查询优惠券的使用张数
     */
    List<CouponInfo> couponxiaofeiCount(int Id, String Type);

    /**
     *
     */
    /**
     * 对平台后台管理的数据进行列表性查询(优惠券)
     */
    List<CouponInfo> admincouponList(Integer pageSize, Integer pageIndex, String Name, String Addr, String BeginTime, String EndTime);

    /**
     * 对平台后台管理的数据进行统计总条数
     */
    List<CouponInfo> admincouponCount(String Name, String Addr, String BeginTime, String EndTime);

    /**
     * 获取失效时间的优惠劵
     *
     * @param days
     * @return
     */
    List<CouponInfo> selectUserCouponExpireDays(Integer days);
}
