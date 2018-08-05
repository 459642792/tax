package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Constants;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.CouponInfo;
import com.blueteam.entity.po.OrderInfo;
import com.blueteam.entity.po.ScoreInfo;
import com.blueteam.entity.po.VendorInfo;
import com.blueteam.wineshop.service.CouponInfoService;
import com.blueteam.wineshop.service.OrderInfoService;
import com.blueteam.wineshop.service.ScoreInfoService;
import com.blueteam.wineshop.service.VendorInfoService;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marx
 * <p>
 * OrderInfoController.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
@Controller
@RequestMapping("/order")
public class OrderInfoController extends BaseController {
    @Autowired
    OrderInfoService orderInfoService;
    @Autowired
    ScoreInfoService scoreInfoService;
    @Autowired
    VendorInfoService vendorInfoService;
    @Autowired
    CouponInfoService couponInfoService;


    static HttpClient client = HttpClientBuilder.create().build();

    /* *
     * @param Userid
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orderList", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult orderList(@RequestParam("pageSize") int pageSize, @RequestParam("pageIndex") int pageIndex, HttpServletResponse response) throws Exception {
        List<OrderInfo> objorderInfo = orderInfoService.OrderInfoList(getCurrentUserID());
        List<OrderInfo> lstOrder = new ArrayList<OrderInfo>();
        for (int i = 0; i < objorderInfo.size(); i++) {
            ScoreInfo objInfo = scoreInfoService.ScoreInfo(objorderInfo.get(i).getOrderno(), objorderInfo.get(i).getUserid(), Constants.COMMENTVENDOR_CODE_VENDOR);
            if (null == objInfo) {
                objorderInfo.get(i).setStateName("待评论");
            } else {
                objorderInfo.get(i).setStateName("已评论");
            }

            VendorInfo objvendor = vendorInfoService.vendorDetail(objorderInfo.get(i).getVendorid());
            if (null != objvendor) {
                objorderInfo.get(i).setVendorInfo(objvendor);
            }
            /**
             * 对支付时间进行格式化转换
             */
            if (objorderInfo.get(i).getPaytime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objorderInfo.get(i).setPayTimeStr(sdf.format(objorderInfo.get(i).getPaytime()));
            }
        }
        int count = (pageIndex - 1) * pageSize;
        int count2 = pageIndex * pageSize;
        if (objorderInfo.size() - pageIndex * pageSize < pageSize) {
            count2 = objorderInfo.size();
        }
        for (int u = count; u < count2; u++) {
            lstOrder.add(objorderInfo.get(u));
        }
        return ApiResult.success(lstOrder, objorderInfo.size());
    }

    /**
     * @param response
     * @return BaseResult
     * @Description:订单生产接口
     * @author xiaojiang
     * @date 2017年2月23日 下午5:11:10
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult saveOrderInfo(@RequestParam("totalMoney") String totalMoney, @RequestParam(value = "couponId", required = false) String couponId, @RequestParam("body") String body, @RequestParam("vendorInfoId") String vendorInfoId, HttpServletResponse response) {

        if (null == totalMoney || "".equals(totalMoney)) {
            return ApiResult.error("付款总金额不能为null");
        }
        if (new BigDecimal(totalMoney).compareTo(BigDecimal.ZERO) <= 0) {
            return ApiResult.error("付款总金额不能小于0");
        }
        if (null == body || "".equals(body)) {
            return ApiResult.error("商品描述不能为null");
        }
        if (null == vendorInfoId || "".equals(vendorInfoId)) {
            return ApiResult.error("商家id不能为null");
        }
        OrderInfo orderInfo = new OrderInfo();
        if (null != couponId && !"".equals(couponId)) {
            orderInfo.setCouponid(Integer.valueOf(couponId));
        }
        orderInfo.setTotalmoney(new BigDecimal(totalMoney));
        orderInfo.setUserid(this.getCurrentUserID());
        orderInfo.setCreateby(this.getUserName());
        orderInfo.setUpdateby(this.getUserName());
        orderInfo.setVendorid(Integer.parseInt(vendorInfoId));
        return orderInfoService.saveOrderInfo(orderInfo, this.getIpAddr(), body);
    }

    /**
     * 支付微信回调方法
     *
     * @param request
     * @param response
     * @return
     * @author xiaojiang 2017年2月28日
     * @version 1.0
     * @since 1.0 2017年2月28日
     */
    @RequestMapping(value = "/payWXSyntony", method = RequestMethod.POST)
    public @ResponseBody
    String payWXSyntony(HttpServletRequest request, HttpServletResponse response) {
        return orderInfoService.payWXSyntony(request);
    }

    /**
     * 查询订单状态
     *
     * @param outTradeNo
     * @param response
     * @return
     * @author xiaojiang 2017年3月8日
     * @version 1.0
     * @since 1.0 2017年3月8日
     */
    @RequestMapping(value = "/getOrderInfo", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getOrderInfo(@RequestParam("outTradeNo") String outTradeNo, HttpServletResponse response) {
        return orderInfoService.getOrderInfo(outTradeNo);
    }

    /**
     * C端订单详情页面
     *
     * @param response 2代码满减 0代表没有优惠券 1代表无条件使用
     * @return
     */
    @RequestMapping(value = "/getOrderInfoC", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getOrderInfoC(@RequestParam("orderno") String orderno, HttpServletResponse response) {
        OrderInfo objOrder = orderInfoService.getOrderInfoC(orderno);
        if (null == objOrder) return ApiResult.error("不存在该订单详情信息");
        VendorInfo objvendor = vendorInfoService.vendorDetail(objOrder.getVendorid());
        if (null == objvendor) return ApiResult.error("不存在该关联商家信息");
        objOrder.setVendorName(objvendor.getName());
        objOrder.setVendorImage(objvendor.getImage());
        int state = 0;
        CouponInfo objCoupon = null;
        if (objOrder.getCouponid() == null || objOrder.getCouponid().equals("")) {
            state = 0;
        } else {
            objCoupon = couponInfoService.couponDetail(objOrder.getCouponid());
            if (objCoupon.getCondition() == null) {
                state = 2;
            } else {
                state = 1;
            }
        }
        return ApiResult.success(objOrder, state, objCoupon == null ? "" : (objCoupon.getCostLimitMoney() == null ? "" : objCoupon.getCostLimitMoney().toString()));
    }


    /**
     * 方法的功能描述:TODO 微信登陆获取openid
     *
     * @param
     * @return
     * @methodName
     * @author xiaojiang 2017/6/16 14:08
     * @since 1.3.0
     */
    @RequestMapping(value = "/getWXOpenid", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getWXOpenid(@RequestParam("wxcode") String wxcode, HttpServletResponse response) {
        return orderInfoService.getWXOpenid(wxcode);
    }
    @RequestMapping(value = "/getWXAccessToken", method = RequestMethod.GET)
    public @ResponseBody
    BaseResult getWXAccessToken( HttpServletResponse response) {
        return orderInfoService.getWXAccessToken();
    }

}
