package com.blueteam.wineshop.service.wechatapplet;

import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.wechatapplet.FaceToFaceOrderDTO;
import com.blueteam.entity.dto.wechatapplet.OrderConfirmationDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.vo.CustomerOrderVO;
import org.codehaus.jackson.map.Serializers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 订单相关
 *
 * @author xiaojiang
 * @create 2018-01-05  16:30
 */
public interface OrderService {
    /**
     * 方法的功能描述: 获取用户收获地址列表和优惠券列表和支付类型列表
     *
     * @return
     * @methodName
     * @ * @param: null
     * @author xiaojiang 2018/1/5 16:51
     * @modifier
     * @since 1.4.0
     */
    BaseResult listCouponAndAddress(Integer userId, Integer vendorId, Long totalMoney);
    /**
     * 方法的功能描述: 获取支付类型包含的支付方式
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 10:40
     *@modifier
     */
    BaseResult listChannelPayWay(Integer vendorId, Integer payTypeId);
    /**
     * 方法的功能描述: 订单确认
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/8 11:41
     *@modifier
     */
    BaseResult saveOrderConfirmation(OrderConfirmationDTO orderConfirmationDTO,Integer userId);
    /**
     * 方法的功能描述: 修改订单状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/11 15:14
     *@modifier
     */
    BaseResult changeOrderStatus(String orderNo,Integer userId);
    /**
     * 方法的功能描述: 获取用户订单列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 14:28
     *@modifier
     */
    BaseResult listCustomerOrder(Integer userId,Integer state,Integer pageIndex,Integer pageSize);
    /**
     * 方法的功能描述: 删除订单 取消订单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 15:41
     *@modifier
     */
    BaseResult deleteOrder(String orderNo,Integer userId,Integer state);
    /**
     * 方法的功能描述: 获取订单详情
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 17:23
     *@modifier
     */
    BaseResult getOrderDetail(String orderNo,Integer userId);
    /**
     * 方法的功能描述: 面对面保存定单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 15:25
     *@modifier
     */
    Map<String,Object> saceFaceToFaceOrder(FaceToFaceOrderDTO faceToFaceOrderDTO);
    /**
     * 方法的功能描述: 30分钟未付款关闭订单
     *@methodName 
      * @param: null
     *@return 
     *@since 1.4.0
     *@author xiaojiang 2018/2/7 16:53
     *@modifier
     */
     void updateByUserOrderNoPay();

    /**
     * 获取结算信息
     * @param settlementId
     * @return
     */
    List<OrderPO> getSettlementOrder(Integer settlementId);

}
