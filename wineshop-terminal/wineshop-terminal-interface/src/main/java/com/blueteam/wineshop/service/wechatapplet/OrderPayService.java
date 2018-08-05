package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.OrderInfo;
import com.blueteam.entity.po.OrderPO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单支付相关
 *
 * @author xiaojiang
 * @create 2018-01-11  10:11
 */
public interface OrderPayService {
    /**
     * 方法的功能描述: 获取预支付信息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 15:52
     *@modifier
     */
    BaseResult getForPay(String orderNo, String body, String ipAddr);
    /**
     * 方法的功能描述: 微信回调方法
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 16:25
     *@modifier
     */
    String payBack(HttpServletRequest request, HttpServletResponse response);
    /**
     * 方法的功能描述: 面对面付款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 11:43
     *@modifier
     */
    BaseResult facePay(String  orderNo,String spbillCreateIp, String body);
}
