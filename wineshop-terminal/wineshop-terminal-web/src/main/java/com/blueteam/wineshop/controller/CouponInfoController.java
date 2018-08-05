package com.blueteam.wineshop.controller;

import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.dto.SimpleCouponInfoVo;
import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.base.constant.EnabledOrDisabled;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.MyValidator;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.service.CouponInfoService;
import com.blueteam.wineshop.service.CouponRecordService;
import com.blueteam.wineshop.service.ScoreInfoService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Marx
 * @author Eric Lee, 2017-03-01 <br/>
 * CouponInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/coupon")
@ApiLogin
public class CouponInfoController extends BaseController {
    @Autowired
    CouponInfoService couponInfoService;
    @Autowired
    CouponRecordService couponRecordService;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    CityInfoMapper cityInfoMapper;
    public Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @param Id
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/couponinsert", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult couponInsert(@RequestParam("Id") int Id, HttpServletResponse response) throws Exception {
        if (Id < 0) {
            return ApiResult.error("参数错误");
        }
        CouponInfo objInfo = couponInfoService.couponDetail(Id);
        if (null == objInfo) {
            return ApiResult.error("该优惠券信息不存在");
        }
        if (objInfo.getStatus() != null) {
            if (objInfo.getStatus() == 90) {
                return ApiResult.error("该优惠券已经停止发放");
            }
        }
        List<CouponInfo> lstCoupon = couponInfoService.UserCouponLists(Id, getCurrentUserID(), Constants.DISCOUNT_COUPON_CODE);
        if (lstCoupon.size() > 0) {
            return ApiResult.error("该优惠券已经领取不能再次领取");
        } else {
            List<CouponInfo> lstCoupons = couponInfoService.couponxiaofeiCount(objInfo.getId(), Constants.DISCOUNT_COUPON_CODE);
            if (lstCoupons.size() >= objInfo.getCount()) {
                return ApiResult.error("该优惠券已经领取完");
            }
            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(now);
            CouponInfo objInfos = new CouponInfo();
            objInfos.setType(Constants.DISCOUNT_COUPON_CODE);
            objInfos.setTitle(objInfo.getTitle());
            objInfos.setMoney(objInfo.getMoney());
            objInfos.setBeginTime(objInfo.getBeginTime());
            objInfos.setEndTime(objInfo.getEndTime());
            objInfos.setCount(1);
            objInfos.setDetail("");
            objInfos.setCostLimitMoney(objInfo.getCostLimitMoney());
            objInfos.setCityCode(objInfo.getCityCode());
            if (objInfo.getType().equals(Constants.CREATE_COUPON_CODE_VENDOR)) {
                objInfos.setForeignKey(objInfo.getForeignKey());//存放商家ID
            } else {
                objInfos.setForeignKey(0);
            }

            objInfos.setCreateBy("");
            objInfos.setCreateDate(date);
            objInfos.setUpdateBy("");
            objInfos.setUpdateDate(date);
            objInfos.setExpandId(Id);//存放领取的优惠券Id
            objInfos.setUserId(getCurrentUserID());
            objInfos.setCostStatus("N");
            objInfos.setCondition(objInfo.getCondition());
            couponInfoService.insertCoupon(objInfos);
            return ApiResult.success();
        }
    }

    /**
     * @param
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userCoupon", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult userCoupon(@RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, HttpServletResponse response) throws Exception {
        if (pageSize < 0 || pageIndex < 0) {
            return ApiResult.error("参数错误");
        }
        List<CouponInfo> lstCoupon = couponInfoService.UserCouponList(getCurrentUserID(), Constants.DISCOUNT_COUPON_CODE);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        Date date = calendar.getTime();
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        calendar2.add(calendar2.DAY_OF_YEAR, -1);
        Date date2 = calendar2.getTime();
        for (int i = 0; i < lstCoupon.size(); i++) {
            if (lstCoupon.get(i).getStatus() == CouponInfo.STATUS_STOP) {
                lstCoupon.get(i).setStatusStr("已停止");
            } else {
                if (date.compareTo(sdf.parse(lstCoupon.get(i).getBeginTime())) > 0 && date2.compareTo(sdf.parse(lstCoupon.get(i).getEndTime())) < 0) {
                    lstCoupon.get(i).setStatusStr("进行中");
                } else if (date.compareTo(sdf.parse(lstCoupon.get(i).getBeginTime())) <= 0) {
                    lstCoupon.get(i).setStatusStr("未开始");
                } else {
                    lstCoupon.get(i).setStatusStr("已过期");
                }
            }
        }
        int count = (pageIndex - 1) * pageSize;
        int count2 = pageIndex * pageSize;
        if (lstCoupon.size() - (pageIndex - 1) * pageSize < pageSize) {
            count2 = lstCoupon.size();
        }
        List<CouponInfo> lstRetrunVendor = new ArrayList<CouponInfo>();
        for (int i = count; i < count2; i++) {
            lstRetrunVendor.add(lstCoupon.get(i));
        }
        return ApiResult.success(lstRetrunVendor, lstCoupon.size());
    }

    /**
     * @param couponId
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/couponDetail", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult couponDetail(@RequestParam("couponId") int couponId, HttpServletResponse response) throws Exception {
        CouponInfo objInfo = couponInfoService.couponDetail(couponId);
        if (null == objInfo) {
            return ApiResult.error("该优惠券信息不存在");
        }
        if (objInfo.getForeignKey() > 0) {
            VendorInfo objVendorInfo = vendorInfoService.vendorDetail(objInfo.getForeignKey());
            if (null == objVendorInfo && objInfo.getForeignKey() > 0) {
                return ApiResult.error("该优惠券所关联的商家不存在");
            }
            if (objVendorInfo.getAdvantage() != null && !objVendorInfo.getAdvantage().trim().isEmpty()) {
                objVendorInfo.setLstAdvantage(objVendorInfo.getAdvantage().split(","));
            }
            //评论记录
            double newScore = 0;
            List<ScoreInfo> objInfos = scoreInfoService.CommonInfoList(objVendorInfo.getId(), Constants.COMMENTVENDOR_CODE_VENDOR);
            for (int i = 0; i < objInfos.size(); i++) {
                newScore = add(newScore, objInfos.get(i).getScore());
            }
            if (objInfos.size() > 0) {
                objVendorInfo.setOutScore(BigDecimal.valueOf(newScore / objInfos.size()));
            } else {
                objVendorInfo.setOutScore(BigDecimal.valueOf(0));
            }
            objInfo.setVendorInfo(objVendorInfo);
        }
        return ApiResult.success(objInfo);
    }

    /**
     * 两个Double记性计算
     */
    public double add(Number value1, Number value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1.doubleValue()));
        BigDecimal b2 = new BigDecimal(Double.toString(value2.doubleValue()));
        return b1.add(b2).doubleValue();
    }


    /**
     * 获取当前用户在该商铺的优惠券列表
     *
     * @param vendorInfoId
     * @param totalMoney
     * @param response
     * @return
     * @author xiaojiang 2017年3月2日
     * @version 1.0
     * @since 1.0 2017年3月2日
     */
    @RequestMapping(value = "/listCouponInfoVendorInfo", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listCouponInfoVendorInfo(@RequestParam("vendorInfoId") String vendorInfoId, @RequestParam("totalMoney") String totalMoney, HttpServletResponse response) {
        if (null == totalMoney || "".equals(totalMoney)) {
            totalMoney = "0";
        }
        if (null == vendorInfoId || "".equals(vendorInfoId)) {
            return ApiResult.error("商户ID不能为空!");
        }
        return couponInfoService.listCouponInfoVendorInfo(Integer.parseInt(vendorInfoId), this.getCurrentUserID(), Double.parseDouble(totalMoney));
    }


    /**
     * 获取优惠券列表
     *
     * @return
     * @author Eric Lee
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "vendorList", method = RequestMethod.GET)
    public BaseResult vendorList(@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                 @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            int vendorId = getIdentify().getExtendId();
            String couponType = Constants.CREATE_COUPON_CODE_VENDOR;
            PageResult<List<SimpleCouponInfoVo>> result =
                    couponInfoService.vendorCouponList(vendorId, couponType, pageIndex, pageSize);
            return ApiResult.success(result.getList(), result.getCount());
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 获取优惠券详细信息
     *
     * @return
     * @author Eric Lee
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "vendorDetailList", method = RequestMethod.GET)
    public BaseResult vendorDetailList(@RequestParam("couponId") Integer couponId,
                                       @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                       @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) {
        try {
            if (!isVendorUser()) {
                return BaseResult.error("对不起，您不是商家用户！");
            }
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CouponInfo model = couponInfoService.couponDetail(couponId);
            String date1 = model.getBeginTime();
            model.setBeginTime(sdf1.format(sdf1.parse(date1)));
            model.setEndTime(sdf1.format(sdf1.parse(model.getEndTime())));
            if (model == null) {
                return BaseResult.error("未查询到优惠券");
            }
            PageResult<List<CouponRecord>> pageModel =
                    couponRecordService.vendorCouponRecordList(couponId, pageIndex, pageSize);
            List<CouponRecord> records = pageModel.getList();
            model.setCouponRecords(records);

            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
            BigDecimal sum = BigDecimal.ZERO;
            for (CouponRecord record : records) {
                Date d = sdf.parse(record.getCreateDate());
                record.setCreateDate(sdf2.format(d));
                sum = sum.add(record.getTotalMoney().subtract(record.getMoney()).compareTo(new BigDecimal("0")) <= 0 ? new BigDecimal("0.01") : record.getTotalMoney().subtract(record.getMoney()));
            }
            model.setDriveMoney(sum); // 带动消费
            model.setCouponStatus(new Date());

            return ApiResult.success(model, pageModel.getCount());
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
    }

    /**
     * 优惠券记录列表
     * 供优惠券详情下面的优惠券记录列表使用（新增：苟永山）
     */
    @ResponseBody
    @RequestMapping(value = "couponRecord", method = RequestMethod.GET)
    public BaseResult couponRecordsList(Integer couponId, @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                        @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) throws ParseException {
        PageResult<List<CouponRecord>> pageModel =
                couponRecordService.vendorCouponRecordList(couponId, pageIndex, pageSize);
        List<CouponRecord> records = pageModel.getList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd");
        for (CouponRecord record : records) {
            Date d = sdf.parse(record.getCreateDate());
            record.setCreateDate(sdf2.format(d));
        }
        return ApiResult.success(records, records.size());

    }

    /**
     * 修改优惠券信息
     *
     * @return
     * @throws Exception
     * @author Eric Lee
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "editVendorCoupon", method = RequestMethod.POST, params = {"Id"})
    public BaseResult editVendorCoupon(CouponInfo model) throws Exception {

        int vendorId = getIdentify().getExtendId();
        CouponInfo selectModel = couponInfoService.couponDetail(model.getId());
        //验证器还有bug,暂时不用  TODO
//			MyValidator4Vendor validator = MyValidator4Vendor.newInstance();
//			validator.isMatch(isVendorUser(), "对不起，您不是商家用户！")
//			 		 .cmpStrDate(model.getBeginTime(), model.getEndTime(), null, -1, "生效时间必须小于失效时间")
//					 .isMatch(couponInfoService.isLegalTime(Constants.CREATE_COUPON_CODE_VENDOR,vendorId,
//							model.getBeginTime(),model.getEndTime()), "您的生效起止时间与其它优惠券的时间有重叠");
//			validator.couponModify(selectModel, model);
//			if(validator.hasErrors()){
//				return BaseResult.error(validator.getErrorMessages().get(0));
//			}


        if (!isVendorUser()) {
            return BaseResult.error("对不起，您不是商家用户！");
        }

        String beginTime = model.getBeginTime(); //model.getBeginTime();
        String endTime = model.getEndTime();
        if (!beginTime.trim().isEmpty() && !endTime.trim().isEmpty()) {
            if (DateUtil.cmpDate(beginTime, endTime, null, 1)) {
                return BaseResult.error("生效时间不能晚于失效时间");
            }
            boolean isLegalTime = couponInfoService.isLegalTime(Constants.CREATE_COUPON_CODE_VENDOR,
                    vendorId, beginTime, endTime, model.getId());
            if (!isLegalTime) {
                return BaseResult.error("您的生效起止时间与其它优惠券的时间有重叠");
            }
        } else {
            return BaseResult.error("请同时输入生效时间和失效时间,或者都不输入");
        }
        selectModel.setTitle(model.getTitle());
        selectModel.setMoney(model.getMoney());
        selectModel.setCostLimitMoney(model.getCostLimitMoney() == null ? null : model.getCostLimitMoney());
        selectModel.setCondition(model.getCondition() == null || model.getCondition() == "" ? "" : model.getCondition());
        selectModel.setBeginTime(beginTime);
        selectModel.setEndTime(endTime);
        selectModel.setCount(model.getCount());
        selectModel.setUpdateBy(getUserName());
        selectModel.setUpdateDate(getCurrentDateString());
        int count = couponInfoService.update(selectModel);
        if (count > 0)
            return BaseResult.success();

        return BaseResult.error("修改优惠券失败!");
    }


    /**
     * 新增优惠券信息
     *
     * @return
     * @throws ParseException
     * @author Eric Lee
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "addVendorCoupon", method = RequestMethod.POST,
            params = {"Money", "BeginTime", "EndTime", "Count", "Title"})
    public BaseResult addVendorCoupon(@RequestParam(value = "CostLimitMoney", required = false) String CostLimitMoney, CouponInfo model) {
        try {
            int vendorId = getIdentify().getExtendId();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            MyValidator<?> validator = MyValidator.newInstance()
                    .isMatch(isVendorUser(), "对不起，您不是商家用户！")
                    .isMatch((sdf.parse(model.getBeginTime()).getTime() - sdf.parse(sdf.format(new Date())).getTime()) >= 0, "优惠券生效时间不能小于当前时间")
                    .isMatch(cmpDate(model.getBeginTime(), model.getEndTime()) <= 0, "生效时间必须小于失效时间")
                    .isMatch(couponInfoService.isLegalTime(Constants.CREATE_COUPON_CODE_VENDOR, vendorId,
                            model.getBeginTime(), model.getEndTime(), 0), "您的生效起止时间与其它优惠券的时间有重叠");

            if (validator.hasErrors()) {
                return BaseResult.error(validator.getErrorMessages().get(0));
            }

            model.setId(null);
            VendorInfo vendorInfo = vendorInfoService.vendorDetail(vendorId);
            model.setCityCode(vendorInfo.getCityCode());
            model.setForeignKey(vendorId);
            model.setUserId(getCurrentUserID());
            model.setCostStatus(EnabledOrDisabled.DISABLED);
            model.setType(Constants.CREATE_COUPON_CODE_VENDOR);
            model.setCreateBy(getUserName());
            model.setCreateDate(getCurrentDateString());
            String[] strsArea = model.getCityCode().split("\\_", -1);
            CityInfo cityInfo = null;
            String area = "";
            for (int i = 0; i < strsArea.length; i++) {
                switch (i) {
                    case 0:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 1:
                        cityInfo = cityInfoMapper.selectCityName(strsArea[0] + "_" + strsArea[i]);
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    case 2:
                        cityInfo = cityInfoMapper.selectCityName(model.getCityCode());
                        area += cityInfo != null ? cityInfo.getName() : "";
                        break;
                    default:
                        break;
                }
            }
            model.setAddr(area);
            couponInfoService.insertCoupon(model);
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            return BaseResult.error(e.getMessage());
        }
        return BaseResult.success();
    }

    /**
     * 结束优惠券
     *
     * @return
     * @author Eric Lee
     */
    @ApiLogin
    @ResponseBody
    @RequestMapping(value = "endVendorCoupon", method = RequestMethod.POST)
    public BaseResult endVendorCoupon(Integer couponId) {
//		try {
        if (!isVendorUser()) {
            return BaseResult.error("对不起，您不是商家用户！");
        }
        CouponInfo model = couponInfoService.couponDetail(couponId);
        if (model == null) {
            BaseResult.error("未查询到优惠券");
        }
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			model.setEndTime( sdf.format(new Date()) );
        model.setCreateBy(getUserName());
        model.setCreateDate(getCurrentDateString());
        model.setStatus(CouponInfo.STATUS_STOP);
        couponInfoService.update(model);
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//			e.printStackTrace();
//			return BaseResult.error(e.getMessage());
//		}
        return BaseResult.success();
    }

    /**
     * 比较两个字符串时间的大小
     *
     * @param endTime
     * @param endTime2
     * @return endTime - endTime2
     * @throws Exception
     */
    private long cmpDate(String endTime, String endTime2) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(endTime).getTime() - sdf.parse(endTime2).getTime();
    }
}
