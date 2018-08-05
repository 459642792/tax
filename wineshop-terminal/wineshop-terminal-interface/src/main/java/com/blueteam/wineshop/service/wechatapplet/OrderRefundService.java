package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.RefundDTO;

/**
 * 订单退款
 *
 * @author xiaojiang
 * @create 2018-01-11  16:03
 */
public interface OrderRefundService {
    /**
     * 方法的功能描述: 申请退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 16:17
     *@modifier
     */
    BaseResult applyToRefund(RefundDTO refundDTO, Integer userId);
    /**
     * 方法的功能描述: 取消退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 15:54
     *@modifier
     */
    BaseResult cancelRefund(String orderNo,Integer userId);


    /**
     * 商家同意退款接口
     * @param orderId
     * @return
     */
    int refundOrder(Long orderId,Integer userId);
    /**
     * 方法的功能描述: 定时任务 定时任务退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 10:47
     *@modifier
     */
    void  vendorOrderRufund();
    /**
     * 方法的功能描述: 定时任务 修改退款中的状态
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:06
     *@modifier
     */
    void updateOrderByRrundIng();
    /**
     * 方法的功能描述: 定时任务 商家3天未退款自动退款
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:25
     *@modifier
     */
    void updateOrderByVendorNoRefund();
}
