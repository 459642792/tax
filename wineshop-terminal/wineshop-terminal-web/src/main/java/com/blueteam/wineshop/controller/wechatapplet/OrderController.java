package com.blueteam.wineshop.controller.wechatapplet;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.FaceToFaceOrderDTO;
import com.blueteam.entity.dto.wechatapplet.OrderConfirmationDTO;
import com.blueteam.entity.dto.wechatapplet.RefundDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.wineshop.controller.BaseController;
import com.blueteam.wineshop.service.wechatapplet.OrderPayService;
import com.blueteam.wineshop.service.wechatapplet.OrderRefundService;
import com.blueteam.wineshop.service.wechatapplet.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 订单相关
 *
 * @author xiaojiang
 * @create 2018-01-05  16:29
 */
@Controller
@RequestMapping("/wechat/order")
public class OrderController extends BaseController {
    /**
     * 订单相关
     */
    @Autowired
    private OrderService orderService;
    /** 订单支付相关*/
    @Autowired
    private OrderPayService orderPayService;
    /** 订单退款相关*/
    @Autowired
    private OrderRefundService orderRefundService;
    /**
     * 方法的功能描述: 获取用户所有的可使用优惠券和地址列表和支付类型列表
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2018/1/5 17:20
     * @modifier
     * @since 1.4.0
     */
    @RequestMapping(value = "/listCouponAndAddress", method = RequestMethod.GET)
    @ResponseBody
    @ApiLogin
    public BaseResult listCouponAndAddress(@RequestParam("vendorId") Integer vendorId, @RequestParam("totalMoney") Long totalMoney) {
        return   orderService.listCouponAndAddress(this.getCurrentUserID(),vendorId,totalMoney);
//        return orderService.listCouponAndAddress(8, vendorId, totalMoney);
    }
    /**
     * 方法的功能描述:  获取支付类型包含的支付方式
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 10:40
     *@modifier
     */
    @RequestMapping(value = "/listChannelPayWay", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listChannelPayWay(@RequestParam("vendorId") Integer vendorId, @RequestParam("payTypeId") Integer payTypeId) {
        return orderService.listChannelPayWay(vendorId, payTypeId);
    }
    /**
     * 方法的功能描述: 确认订单信息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 15:49
     *@modifier
     */
    @RequestMapping(value = "/saveOrderConfirmation", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult saveOrderConfirmation(@RequestBody  OrderConfirmationDTO orderConfirmationDTO){
        return   orderService.saveOrderConfirmation(orderConfirmationDTO,this.getCurrentUserID());
//        return   orderService.saveOrderConfirmation(orderConfirmationDTO,8);
    }
    /**
     * 方法的功能描述:获取与支付信息
     *@methodName getForPay
     * @param: orderNo 订单编号
     * @param: body 描述 以后提示要用
     *@return com.blueteam.entity.dto.BaseResult
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 16:12
     *@modifier
     */
    @RequestMapping(value = "/getForPay", method = RequestMethod.POST)
    @ResponseBody
    @ApiLogin
    public BaseResult getForPay(@RequestParam("orderNo")String orderNo,@RequestParam("body")String body){
        return   orderPayService.getForPay(orderNo, body,this.getIpAddr());
    }
    /**
     * 方法的功能描述: 微信回调
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 16:24
     *@modifier
     */
    @RequestMapping(value = "/payBack", method = RequestMethod.POST)
    public @ResponseBody String payBack(HttpServletRequest request, HttpServletResponse response) {
        return orderPayService.payBack(request,response);
    }
    /**
     * 方法的功能描述: 支付成功修改订单状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 15:46
     *@modifier
     */
    @RequestMapping(value = "/changeOrderStatus", method = RequestMethod.POST )
    @ApiLogin
    public @ResponseBody BaseResult  changeOrderStatus(@RequestParam("orderNo")String orderNo){
        return orderService.changeOrderStatus(orderNo,this.getCurrentUserID());
    }
    /**
     * 方法的功能描述: 退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 15:50
     *@modifier
     */
    @RequestMapping(value = "/refund", method = RequestMethod.POST )
    @ApiLogin
    public @ResponseBody BaseResult  refund(RefundDTO refundDTO){
        return orderRefundService.applyToRefund(refundDTO,this.getCurrentUserID());
    }
    /**
     * 方法的功能描述: 取消申请退款
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/12 16:04
     *@modifier
     */
    @RequestMapping(value = "/cancelRefund", method = RequestMethod.POST )
    @ApiLogin
    public @ResponseBody BaseResult  cancelRefund(@RequestParam("orderNo") String orderNo){
        return orderRefundService.cancelRefund(orderNo,this.getCurrentUserID());
    }
    /**
     * 方法的功能描述: 获取用户订单列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 15:30
     *@modifier
     */
    @RequestMapping(value = "/listCustomerOrder", method = RequestMethod.GET )
    @ApiLogin
    public   @ResponseBody BaseResult listCustomerOrder(@RequestParam("state")Integer state,
                                                        @RequestParam("pageIndex") Integer pageIndex,
                                                        @RequestParam("pageSize") Integer pageSize){
        return orderService.listCustomerOrder(this.getCurrentUserID(),state,pageIndex,pageSize);
//        return orderService.listCustomerOrder(8,state,pageIndex,pageSize);
    }
    /**
     * 方法的功能描述: 删除订单0 取消订单2
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 14:22
     *@modifier
     */
    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST )
    @ApiLogin
    public @ResponseBody BaseResult  deleteOrder(@RequestParam("orderNo") String orderNo,@RequestParam("state") Integer state){
//        return orderService.deleteOrder(orderNo,8,state);
        return orderService.deleteOrder(orderNo,this.getCurrentUserID(),state);
    }

    /**
     * 方法的功能描述:订单详情
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 14:23
     *@modifier
     */
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
    @ApiLogin
    @ResponseBody
    public BaseResult getOrderDetail(@RequestParam("orderNo") String orderNo){
        return orderService.getOrderDetail(orderNo,this.getCurrentUserID());
//        return orderService.getOrderDetail(orderNo,8);
    }
    /**
     * 方法的功能描述: 面对面付款
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 17:24
     *@modifier
     */
    @RequestMapping(value = "/payFaceToFaceOrder", method = RequestMethod.POST)
    @ApiLogin
    @ResponseBody
    public BaseResult payFaceToFaceOrder(FaceToFaceOrderDTO faceToFaceOrderDTO){
        faceToFaceOrderDTO.setUserId(this.getCurrentUserID());
        Map<String,Object> map =  orderService.saceFaceToFaceOrder(faceToFaceOrderDTO);
        boolean flag = (boolean)map.get("flag");
        if (flag){
            String order = (String)map.get("orderNo");
            return orderPayService.facePay(order,this.getIpAddr(),faceToFaceOrderDTO.getBody());
        }
        return ApiResult.error(map.get("msg").toString());
    }
}
