package com.blueteam.wineshop.mapper;

import com.blueteam.entity.po.CouponInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Marx
 * <p>
 * CouponInfoDao.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Repository
public interface CouponInfoMapper {
    /**
     * @param ForeignKey
     * @return
     */
    List<CouponInfo> CouponInfoList(@Param("ForeignKey") int ForeignKey);

    /**
     * @param ForeignKey
     * @return
     */
    List<CouponInfo> CouponInfoListDetail(@Param("ForeignKey") int ForeignKey,@Param("cityCode") String cityCode, @Param("Type") String Type);

    /**
     * @param Id
     * @return
     */
    CouponInfo couponDetail(@Param("Id") int Id);

    /**
     * 修改优惠券状态
     *
     * @param Id
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    void updateCouponInfo(@Param("costStatus") String costStatus, @Param("Id") int Id);

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
    List<CouponInfo> UserCouponList(@Param("UserId") int UserId, @Param("Type") String Type);

    /**
     * 过期优惠券再次领取逻辑
     *
     * @param UserId
     * @param Type
     * @return
     */
    List<CouponInfo> UserCouponLists(@Param("VendorId") int VendorId, @Param("UserId") int UserId, @Param("Type") String Type);

    List<CouponInfo> vendorCouponList(@Param("ForeignKey") Integer ForeignKey, @Param("Type") String Type);


    /**
     * 判断用户是否领取了优惠券
     *
     * @param UserId
     * @param Type
     * @return
     */
    List<CouponInfo> checkCoupon(@Param("UserId") int UserId, @Param("Type") String Type, @Param("Id") int Id);

    List<CouponInfo> checkCoupon(@Param("UserId") Integer UserId, @Param("Type") String Type, @Param("Id") int Id);


    /**
     * 修改优惠券状态
     *
     * @param id
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    void updateCouponInfo(Integer id);

    int updateByPrimaryKeySelective(CouponInfo record);

    /**
     * 查询使用优惠券的张数
     *
     * @param
     * @return
     */
    List<CouponInfo> couponxiaofeiCount(@Param("Id") int Id, @Param("Type") String Type);

    /**
     * 　获取当前用户优惠券列表
     *
     * @param vendorInfoId 商户id
     * @param userId       用户id
     * @param totalMoney   钱
     * @return
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    List<Map<String, Object>> listCouponInfoVendorInfo(@Param("vendorInfoId") int vendorInfoId, @Param("userId") int userId, @Param("totalMoney") Double totalMoney);

    List<Map<String, Object>> vendorCouponAndRecords(@Param("vendorId") Integer vendorId, @Param("type") String type);

    int vendorCouponAndRecordsNewCount(@Param("vendorId") Integer vendorId, @Param("type") String type);

    List<CouponInfo> vendorCouponAndRecordsNew(@Param("vendorId") Integer vendorId,
                                               @Param("type") String type, @Param("pageIndex") Integer pageIndex, @Param("pageSize") Integer pageSize);

    /**
     * 判断指定的时间范围是否与所有的生效时间范围重叠
     *
     * @param type
     * @param vendorId
     * @param beginTime 指定的开始时间
     * @param endTime   指定的结束时间
     * @return 返回重叠时间的个数
     */
    int countOfRepeatTime(@Param("type") String type, @Param("vendorId") Integer vendorId,
                          @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("id") Integer id);

    /**
     * 查询管理后台优惠券库的数据
     *
     * @param Name
     * @param pageSize
     * @param pageIndex
     * @return
     */
    List<CouponInfo> admincouponList(@Param("pageSize") Integer pageSize, @Param("pageIndex") Integer pageIndex, @Param("Name") String Name, @Param("Addr") String Addr, @Param("BeginTime") String BeginTime, @Param("EndTime") String EndTime);

    /**
     * 对查询管理后台优惠券的数据进行统计
     */
    List<CouponInfo> admincouponCount(@Param("Name") String Name, @Param("Addr") String Addr, @Param("BeginTime") String BeginTime, @Param("EndTime") String EndTime);

    /**
     * 获取失效时间的优惠劵
     *
     * @param days
     * @return
     */
    List<CouponInfo> selectUserCouponExpireDays(@Param("days") Integer days);
}
