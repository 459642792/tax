package com.blueteam.wineshop.controller;


import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.service.WeChatPayService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
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
import java.util.Map;

/**
 * Created by  NastyNas on 17/8/22.
 * <p/>
 * 微信支付后台服务
 */
@Controller
@RequestMapping("/pay")
public class WeChatPayController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(WeChatPayController.class);
    @Autowired
    WeChatPayService weChatPayService;

    /**
     * 公共号支付：用户在微信内进入商家H5页面，页面内调用JSSDK完成支付
     *
     * @return
     */
    @RequestMapping(value = "/officialAccounts", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult payForOfficialAccounts(@RequestParam(value = "totalMoney") String totalMoney,
                                             @RequestParam(value = "couponId", required = false) String couponId,
                                             @RequestParam(value = "body") String body,
                                             @RequestParam(value = "vendorInfoId") String vendorInfoId) {
        try {
            logger.info("微信公共号支付start,用户ID={}", getCurrentUserID());
            //传输层数据
            Map transMap = Maps.newHashMap();
            //校验支付参数
            checkPayReqParam(totalMoney, body, vendorInfoId);
            //准备传输层数据
            prepareTransParam(transMap, totalMoney, couponId, body, vendorInfoId);
            //订单提交：准备入库参数->提交订单->调用支付服务->处理支付结果并返回
            return weChatPayService.officialAccountsPay(transMap);
        } catch (BusinessException e) {
            logger.error("业务异常,用户ID={},异常原因={}", getCurrentUserID(), e.getMessage());
            return BaseResult.error(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常,用户ID={},异常原因={}", getCurrentUserID(), ExceptionUtil.stackTraceString(e));
            return BaseResult.error("系统异常");
        }
    }


    /**
     * F5支付：用户在微信浏览器以外的手机浏览器唤起微信支付
     *
     * @return
     */
    @RequestMapping(value = "/f5", method = RequestMethod.POST)
    @ApiLogin
    public BaseResult payForF5(@RequestParam(value = "totalMoney") String totalMoney,
                               @RequestParam(value = "couponId", required = false) String couponId,
                               @RequestParam(value = "body") String body,
                               @RequestParam(value = "vendorInfoId") String vendorInfoId, HttpServletResponse response) {
        try {
            logger.info("F5支付start,用户ID={}", getCurrentUserID());
            //传输层数据
            Map transMap = Maps.newHashMap();
            //校验支付参数
            checkPayReqParam(totalMoney, body, vendorInfoId);
            //准备传输层数据
            prepareTransParam(transMap, totalMoney, couponId, body, vendorInfoId);
            //订单提交：准备入库参数->提交订单->调用支付服务->返回支付重定向url
            String redirectUrl = weChatPayService.f5pay(transMap);
            response.sendRedirect(redirectUrl);
            return BaseResult.success();
        } catch (BusinessException e) {
            logger.error("业务异常,用户ID={},异常原因={}", getCurrentUserID(), e.getMessage());
            return BaseResult.error(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常,用户ID={},异常原因={}", getCurrentUserID(), ExceptionUtil.stackTraceString(e));
            return BaseResult.error("系统异常");
        }
    }


    private void prepareTransParam(Map transMap, String totalMoney, String couponId, String body, String vendorInfoId) {
        transMap.put("userId", this.getCurrentUserID());
        transMap.put("createBy", this.getUserName());
        transMap.put("updateBy", this.getUserName());
        transMap.put("ipAddress", this.getIpAddr());

//        transMap.put("userId", "1205");
//        transMap.put("createBy", "张鑫");
//        transMap.put("updateBy", "张鑫");
//        transMap.put("ipAddress", this.getIpAddr());

        transMap.put("totalMoney", totalMoney);
        transMap.put("couponId", couponId);
        transMap.put("body", body);
        transMap.put("vendorInfoId", vendorInfoId);
    }

    private void checkPayReqParam(String totalMoney, String body, String vendorInfoId) {
        if (Strings.isNullOrEmpty(totalMoney)) {
            throw new BusinessException("付款总金额不能为空");
        }
        if (new BigDecimal(totalMoney).compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("付款总金额不能小于0");
        }
        if (Strings.isNullOrEmpty(body)) {
            throw new BusinessException("商品描述不能为空");
        }
        if (Strings.isNullOrEmpty(vendorInfoId)) {
            throw new BusinessException("商家id不能为空");
        }
    }


}
