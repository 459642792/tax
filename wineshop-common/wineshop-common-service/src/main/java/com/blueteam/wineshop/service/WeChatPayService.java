package com.blueteam.wineshop.service;

import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.help.wechatpay.WXPay;
import com.blueteam.base.help.wechatpay.WXPayConstants;
import com.blueteam.base.help.wechatpay.WXPayUtil;
import com.blueteam.base.help.wechatpay.config.WXPayConfigOA;
import com.blueteam.base.lang.Dates;
import com.blueteam.base.lang.RMap;
import com.blueteam.base.util.Coder;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.*;
import com.blueteam.wineshop.mapper.CityInfoMapper;
import com.blueteam.wineshop.mapper.OrderInfoMapper;
import com.blueteam.wineshop.mapper.UserInfoMapper;
import com.blueteam.wineshop.mapper.VendorInfoMapper;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * Created by  NastyNas on 17/8/22.
 * <p/>
 * 微信支付服务
 */
@Service
public class WeChatPayService {

    private static final Logger logger = LoggerFactory.getLogger(WeChatPayService.class);

    @Autowired
    VendorInfoMapper vendorInfoMapper;

    @Autowired
    CityInfoMapper cityInfoMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    UserInfoMapper userInfoMapper;

    @Autowired
    private CouponInfoService couponInfoService;


    public BaseResult officialAccountsPay(Map transMap) {
        //准备入库参数并提交订单
        prepareAndSubmitOrder(transMap);
        //微信支付业务参数
        Map reqData = Maps.newHashMap();
        reqData.put("body", RMap.getStr(transMap, "body"));
        reqData.put("out_trade_no", RMap.getStr(transMap, "orderNo"));
        reqData.put("total_fee", RMap.getStr(transMap, "totalFee"));
//        reqData.put("total_fee", "1");

        reqData.put("spbill_create_ip", RMap.getStr(transMap, "ipAddress"));
        reqData.put("openid", RMap.getStr(transMap, "openId"));
        //调用微信支付
        return invokeWeChatPayForOA(reqData);
    }


    public String f5pay(Map transMap) {
        //准备入库参数并提交订单
        prepareAndSubmitOrder(transMap);
        //微信支付业务参数
        Map reqData = Maps.newHashMap();
        reqData.put("body", RMap.getStr(transMap, "body"));
        reqData.put("out_trade_no", RMap.getStr(transMap, "orderNo"));
        reqData.put("total_fee", RMap.getStr(transMap, "totalFee"));
        reqData.put("spbill_create_ip", RMap.getStr(transMap, "ipAddress"));
        reqData.put("openid", RMap.getStr(transMap, "openId"));
        //调用微信支付
        return invokeWeChatPayForF5(reqData);
    }

    private String invokeWeChatPayForF5(Map reqData) {
        Map responseMap = null;
        try {
            logger.info("F5支付开始,业务参数={}", reqData);
            //微信支付sdk
            WXPay wxPay = new WXPay(WXPayConfigOA.getInstance(), WXPayConstants.F5_NOTIFY_URL, false, false);
            //支付返回Map
            responseMap = wxPay.unifiedOrder(reqData);
            if (responseMap == null) {
                throw new BusinessException("F5支付服务调用结果为空");
            }
        } catch (Exception e) {
            logger.error("F5支付调用失败，调用参数={}", reqData);
            throw new BusinessException("F5支付服务调用失败");
        }
        try {
            logger.info("F5支付返回参数responseMap={}", responseMap);
            String retCode = RMap.getStr(responseMap, "return_code"); //返回状态码
            String retMsg = RMap.getStr(responseMap, "return_msg"); //返回信息
            //支付成功,为页面准备JSAPI调用参数
            if (WXPayConstants.SUCCESS.equals(retCode)) {
                if (WXPayConstants.SUCCESS.equals(RMap.getStr(responseMap, "result_code"))) {
                    logger.info("F5支付成功,重定向支付页面={}", RMap.getStr(responseMap, "mweb_url"));
                    return RMap.getStr(responseMap, "mweb_url");
                } else {
                    String errorCode = RMap.getStr(responseMap, "err_code");
                    String errorDesc = RMap.getStr(responseMap, "err_code_des");
                    throw new BusinessException(errorCode, errorDesc);
                }
            } else {
                throw new BusinessException(retCode, retMsg);
            }
        } catch (Exception e) {
            throw new BusinessException("获取跳转页面url失败", e);
        }

    }


    private void prepareAndSubmitOrder(Map transMap) {
        /*准备入库参数start*/
        logger.info("准备入库参数开始,transMap={}", transMap);
        UserInfo userInfo;
        try {
            userInfo = userInfoMapper.selectByPrimaryKey(RMap.getInt(transMap, "userId"));
            if (userInfo == null) {
                throw new BusinessException();
            }
        } catch (Exception e) {
            throw new BusinessException("用户信息获取失败");
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserid(RMap.getInt(transMap, "userId"));
        orderInfo.setCreateby(RMap.getStr(transMap, "createBy"));
        orderInfo.setUpdateby(RMap.getStr(transMap, "updateBy"));
        orderInfo.setTotalmoney(new BigDecimal(RMap.getStr(transMap, "totalMoney")));
        BigDecimal totalMoney = orderInfo.getTotalmoney();
        orderInfo.setVendorid(Integer.parseInt(RMap.getStr(transMap, "vendorInfoId")));
        Date currentDate = new Date();
        orderInfo.setCreatedate(currentDate);
        orderInfo.setUpdatedate(currentDate);
        VendorInfo vendorInfo = vendorInfoMapper.vendorDetail(orderInfo.getVendorid());
        if (vendorInfo != null) {
            orderInfo.setTitle(vendorInfo.getName());
        }
        //订单价格价格计算
        computePrice(orderInfo, totalMoney, RMap.getStr(transMap, "couponId"));
        orderInfo.setCount(1);
        String serialCode = Coder.getSerialCode20();
        serialCode += Coder.getSerialCode4();
        orderInfo.setOrderno(serialCode);//订单号
        orderInfo.setOrderstatus(OrderInfo.ORDER_STATUS_WAITING_PAY);
        orderInfo.setPayway("WX");
        orderInfo.setTradingArea(obtainTradingArea(vendorInfo.getCityCode()));
        /*准备入库参数end*/
        logger.info("入库参数准备完毕,orderInfo={}", orderInfo);
        //订单提交
        submit(orderInfo);
        //传输层数据补充
        transMap.put("openId", userInfo.getWxopenid());
        transMap.put("orderNo", orderInfo.getOrderno());
        transMap.put("totalFee", String.valueOf(orderInfo.getDiscountamount().intValue() * 100));//@TODO doubleValue()?

    }

    private BaseResult invokeWeChatPayForOA(Map reqData) {
        Map responseMap = null;
        try {
            logger.info("公共号支付开始,业务参数={}", reqData);
            //微信支付sdk
            WXPay wxPay = new WXPay(WXPayConfigOA.getInstance(), WXPayConstants.OA_NOTIFY_URL, false, false);
            //支付返回Map
            responseMap = wxPay.unifiedOrder(reqData);
            if (responseMap == null) {
                throw new BusinessException("公共号支付服务调用结果为空");
            }
        } catch (Exception e) {
            logger.error("公共号支付调用失败，调用参数={}", reqData);
            return BaseResult.error("公共号支付服务调用失败");
        }
        try {
            logger.info("公共号支付返回参数responseMap={}", responseMap);
            String retCode = RMap.getStr(responseMap, "return_code"); //返回状态码
            String retMsg = RMap.getStr(responseMap, "return_msg"); //返回信息
            //支付成功,为页面准备JSAPI调用参数
            if (WXPayConstants.SUCCESS.equals(retCode)) {
                if (WXPayConstants.SUCCESS.equals(RMap.getStr(responseMap, "result_code"))) {
                    Map retMap = Maps.newHashMap();
                    retMap.put("appId", WXPayConstants.OA_APP_ID);
                    retMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
                    retMap.put("nonceStr", WXPayUtil.generateUUID());
                    //业务结果
                    retMap.put("package", "prepay_id=" + RMap.getStr(responseMap, "prepay_id"));
                    retMap.put("signType", WXPayConstants.SignType.MD5);
                    //签名
                    retMap.put("paySign", WXPayUtil.generateSignature(retMap, WXPayConstants.OA_KEY, WXPayConstants.SignType.MD5));
                    retMap.put("outTradeNo", RMap.getStr(reqData, "out_trade_no"));
                    logger.info("公共号支付成功,返回前台JSAPI调用参数={}", retMap);
                    return ApiResult.success(retMap);
                } else {
                    String errorCode = RMap.getStr(responseMap, "err_code");
                    String errorDesc = RMap.getStr(responseMap, "err_code_des");
                    return ApiResult.error(errorCode, errorDesc);
                }
            } else {
                return ApiResult.error(retCode, retMsg);
            }
        } catch (Exception e) {
            logger.error("准备JSAPI调用参数发生异常,返回信息responseMap={},异常原因={}", responseMap, ExceptionUtil.stackTraceString(e));
            return BaseResult.error("准备JSAPI调用参数发生异常");
        }
    }

    /**
     * 计算商品价格
     *
     * @param orderInfo
     * @param totalMoney
     * @param couponId
     */
    private void computePrice(OrderInfo orderInfo, BigDecimal totalMoney, String couponId) {
        //使用优惠券
        if (!Strings.isNullOrEmpty(couponId)) {
            orderInfo.setCouponid(Integer.valueOf(couponId));
            CouponInfo couponInfo = couponInfoService.couponDetail(orderInfo.getCouponid());
            if (orderInfo.getUserid() != null && !orderInfo.getUserid().equals(couponInfo.getUserId())) {
                throw new BusinessException("不能使用他人优惠券");
            }
            if (orderInfo.getVendorid() != null && !(orderInfo.getVendorid().equals(couponInfo.getForeignKey()) || couponInfo.getForeignKey().equals(0))) {
                throw new BusinessException("不能使用其他商家优惠券");
            }
            if (totalMoney != null && decideCoupon(totalMoney.doubleValue(), couponInfo) && !CouponInfo.COST_STATUS_USED.equals(couponInfo.getCostStatus())) {
                BigDecimal money = couponInfo.getMoney();
                if (totalMoney.subtract(money).doubleValue() <= 0) {//判断优惠券是否大于总金额
                    orderInfo.setDiscountamount(new BigDecimal("0.01"));//最后金额
                    orderInfo.setPrice(new BigDecimal("0.01"));
                } else {
                    orderInfo.setDiscountamount(totalMoney.subtract(money));//最后金额
                    orderInfo.setPrice(totalMoney.subtract(money));
                }
            } else {
                if (totalMoney.doubleValue() * 100 <= couponInfo.getCostLimitMoney().intValue() * 100) {
                    throw new BusinessException("付款金额不满足优惠券使用条件");
                } else {
                    throw new BusinessException("优惠券已使用");
                }
            }
            if (couponInfo.getForeignKey().equals(0)) {
                orderInfo.setSettlement_money(orderInfo.getTotalmoney());
            } else {
                orderInfo.setSettlement_money(orderInfo.getDiscountamount());
            }
        } else {
            //不使用优惠券
            orderInfo.setDiscountamount(orderInfo.getTotalmoney());//最后金额
            orderInfo.setPrice(orderInfo.getTotalmoney());
            orderInfo.setSettlement_money(orderInfo.getTotalmoney());
        }
    }


    private void submit(OrderInfo orderInfo) {
        try {
            int status = orderInfoMapper.insert(orderInfo);
            if (status != 1) {
                throw new BusinessException();
            }
        } catch (Exception e) {
            logger.error("订单提交入库失败,orderInfo={},异常原因={}", orderInfo, ExceptionUtil.stackTraceString(e));
            throw new BusinessException("订单提交入库失败");
        }
    }

    private String obtainTradingArea(String cityCode) {
        String[] strsArea = cityCode.toString().split("\\_", -1);
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
                    cityInfo = cityInfoMapper.selectCityName(cityCode);
                    area += cityInfo != null ? cityInfo.getName() : "";
                    break;
                default:
                    break;
            }
        }

        return area;
    }

    /**
     * 判断优惠券是否可用
     *
     * @param num
     * @param couponInfo
     * @return
     */

    public boolean decideCoupon(Double num, CouponInfo couponInfo) {
        boolean flag = false;
        Date date = Dates.parse3(Dates.format3(new Date()));
        String beginTimeStr = couponInfo.getBeginTime();
        String endTimeStr = couponInfo.getEndTime();

        if (!Strings.isNullOrEmpty(couponInfo.getCondition())) {
            //无条件
            if (Dates.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= Dates.parse(endTimeStr).getTime()) {
                flag = true;
            }
        } else {
            //满多少
            if (num * 100 >= couponInfo.getCostLimitMoney().intValue() * 100) {
                if (Dates.parse(beginTimeStr).getTime() <= date.getTime() && date.getTime() <= Dates.parse(endTimeStr).getTime()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

}
