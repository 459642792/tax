package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.SimpleCouponInfoVo;
import com.blueteam.wineshop.mapper.CouponInfoMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CouponInfoServiceImpl implements CouponInfoService {

    @Autowired
    CouponInfoMapper couponInfoMapper;

    @Autowired
    VendorInfoMapper vendorInfoMapper;

    @Override
    public List<CouponInfo> CouponInfoList(int ForeignKey) {
        return couponInfoMapper.CouponInfoList(ForeignKey);
    }

    @Override
    public List<CouponInfo> CouponInfoListDetail(int ForeignKey, String cityCode,String Type) {
        return couponInfoMapper.CouponInfoListDetail(ForeignKey,cityCode, Type);
    }

    @Override
    public CouponInfo couponDetail(int Id) {
        return couponInfoMapper.couponDetail(Id);
    }

    @Override
    public int insertCoupon(CouponInfo coupon) {
        return couponInfoMapper.insertCoupon(coupon);
    }

    @Override
    public List<CouponInfo> UserCouponList(int UserId, String Type) {
        return couponInfoMapper.UserCouponList(UserId, Type);
    }

    @Override
    public List<CouponInfo> UserCouponLists(int VendorId, int UserId, String Type) {
        return couponInfoMapper.UserCouponLists(VendorId, UserId, Type);
    }

    @Override
    public List<CouponInfo> checkCoupon(int UserId, String Type, int Id) {
        return couponInfoMapper.checkCoupon(UserId, Type, Id);
    }

    @Override
    public PageResult<List<SimpleCouponInfoVo>> vendorCouponList(Integer vendorId, String type, Integer pageIndex,
                                                                 Integer pageSize) {
        List<SimpleCouponInfoVo> simpleCouponInfos = new ArrayList<SimpleCouponInfoVo>();
        List<CouponInfo> couponInfos = null;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        couponInfos = couponInfoMapper.vendorCouponAndRecordsNew(vendorId, type, pageIndex, pageSize);
        for (CouponInfo couponInfo : couponInfos) {
            //获取带动消费
            BigDecimal driveMoney = BigDecimal.ZERO;
            List<CouponRecord> records = couponInfo.getCouponRecords();
            for (CouponRecord record : records) {
                if (null != record.getTotalMoney() && null != record.getMoney()) {
                    driveMoney = driveMoney.add(record.getTotalMoney().subtract(record.getMoney()).doubleValue() <= BigDecimal.ZERO.doubleValue() ? new BigDecimal("0.01") : record.getTotalMoney().subtract(record.getMoney()));
                }
            }
            //设置优惠券状态的字符串
            try {
                couponInfo.setCouponStatus(now);
                SimpleCouponInfoVo simpleCouponInfo = new SimpleCouponInfoVo();
                simpleCouponInfo.setId(couponInfo.getId());
                simpleCouponInfo.setBeginTime(sdf.format(sdf.parse(couponInfo.getBeginTime())));
                simpleCouponInfo.setEndTime(sdf.format(sdf.parse(couponInfo.getEndTime())));
                simpleCouponInfo.setCostLimitMoney(couponInfo.getCostLimitMoney());
                simpleCouponInfo.setMoney(couponInfo.getMoney());
                simpleCouponInfo.setDriveMoney(driveMoney);
                simpleCouponInfo.setStatus(couponInfo.getStatusStr());
                simpleCouponInfo.setTitle(couponInfo.getTitle());
                simpleCouponInfo.setUsedCount(records.size());
                simpleCouponInfo.setCondition(couponInfo.getCondition() == null ? "" : couponInfo.getCondition());
                simpleCouponInfo.setTotalCount(couponInfo.getCount());
                simpleCouponInfos.add(simpleCouponInfo);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PageResult<List<SimpleCouponInfoVo>> result = new PageResult<List<SimpleCouponInfoVo>>();
        result.setList(simpleCouponInfos);
        result.setCount(couponInfoMapper.vendorCouponAndRecordsNewCount(vendorId, type));
        return result;
    }

    @Override
    public void updateCouponInfo(String costStatus, Integer id) {
        couponInfoMapper.updateCouponInfo(costStatus, id);
    }


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
    @Override
    public BaseResult listCouponInfoVendorInfo(int vendorInfoId, int userId, Double totalMoney) {
        VendorInfo vendorInfo = vendorInfoMapper.vendorDetail(vendorInfoId);
        Map<String, Object> mapV = new HashMap<String, Object>();
        Map<String, Object> mapVendorInfo = new HashMap<String, Object>();
        mapVendorInfo.put("vendorInfoImage", vendorInfo.getImage());
        mapVendorInfo.put("vendorInfoName", vendorInfo.getName());
        mapVendorInfo.put("vendorInfoAddress",vendorInfo.getAddr());
        mapV.put("vendorInfoDetail", mapVendorInfo);
        List<Map<String, Object>> listCouponInfo = couponInfoMapper.listCouponInfoVendorInfo(vendorInfoId, userId, totalMoney);
        List<Map<String, Object>> listCouponInfoV = new ArrayList<Map<String, Object>>();
        if (null != listCouponInfo && listCouponInfo.size() != 0) {
            for (Map<String, Object> map : listCouponInfo) {
                Map<String, Object> mapL = new HashMap<String, Object>();
                if (null == map.get("condition") || "".equals(map.get("condition"))) {
                    mapL.put("describe", "满" + mathRoundDouble(map.get("costLimitMoney")) + "元减" + mathRoundDouble(map.get("money")) + "元");
                } else {
                    mapL.put("describe", "无门槛直减" + mathRoundDouble(map.get("money")) + "元");
                }
                mapL.put("couponId", map.get("couponInfoId"));
                mapL.put("money", map.get("money"));
                listCouponInfoV.add(mapL);
            }
        }
        mapV.put("couponInfoDetail", listCouponInfoV);
        return ApiResult.success(mapV);
    }

    /**
     * 如没有小数去除
     *
     * @param o
     * @return
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    public String mathRoundDouble(Object o) {
        Double d = Double.parseDouble(o.toString());
        if (Math.round(d) - d == 0D) {
            return String.valueOf(Math.round(d));
        }
        return String.valueOf(d);
    }

    @Override
    public int update(CouponInfo model) {
        return couponInfoMapper.updateByPrimaryKeySelective(model);
    }

    @Override
    public boolean isLegalTime(String type, int vendorId, String beginTime, String endTime, int id) {
        int count = couponInfoMapper.countOfRepeatTime(type, vendorId, beginTime, endTime, id);
        return count == 0;
    }

    @Override
    public List<CouponInfo> couponxiaofeiCount(int Id, String Type) {
        return couponInfoMapper.couponxiaofeiCount(Id, Type);
    }

    /**
     * 对后台优惠券库数据进行维护处理
     */
    public List<CouponInfo> admincouponList(Integer pageSize, Integer pageIndex, String Name, String Addr, String BeginTime, String EndTime) {
        return couponInfoMapper.admincouponList(pageSize, pageIndex, Name, Addr, BeginTime, EndTime);
    }

    /**
     * 对后台优惠券管理的数据进行统计（此统计用于分页）
     */
    public List<CouponInfo> admincouponCount(String Name, String Addr, String BeginTime, String EndTime) {
        return couponInfoMapper.admincouponCount(Name, Addr, BeginTime, EndTime);
    }

    /**
     * 获取失效时间的优惠劵
     *
     * @param days
     * @return
     */
    @Override
    public List<CouponInfo> selectUserCouponExpireDays(Integer days) {
        return couponInfoMapper.selectUserCouponExpireDays(days);
    }
}

