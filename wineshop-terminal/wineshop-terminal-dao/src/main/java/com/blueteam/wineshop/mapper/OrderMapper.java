package com.blueteam.wineshop.mapper;

import com.blueteam.entity.bo.wechatapplet.CustomerOrderBO;
import com.blueteam.entity.bo.wechatapplet.CustomerzSearchOrderBO;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.vo.UserOrderVo;
import com.blueteam.entity.vo.VendorOrderVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.Order;
import java.util.List;
import java.util.Map;


@Repository
public interface OrderMapper {


    OrderPO getByOrderId(Long orderId);

    List<VendorOrderVO> getOrderList(VendorOrderDTO dto);

    int getCount(VendorOrderDTO dto);

    /**
     * 确认接单
     * @param dto
     *
     * @return
     */
    int receiveOrder(VendorOrderDTO dto);


    /**
     * 确认送货
     * @param dto
     * @return
     */
    int confirmOrder(VendorOrderDTO dto);
    /**
     * 方法的功能描述: 保存订单
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/9 14:14
     *@modifier
     */
    int saveOrder(OrderPO orderPO);


    /**
     * 订单商品详情
     * @param orderId
     * @return
     */
    VendorOrderVO orderDetail(@Param("orderId") Long orderId);

    VendorOrderVO orderDetail2(@Param("orderNo") String orderNo,@Param("orderId") Long orderId);
    /**
     * 方法的功能描述: 根据orderNo 获取订单信息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 16:04
     *@modifier
     */
    OrderPO getByOrderNo(@Param("orderNo") String orderNo );
    /**
     * 方法的功能描述: 修改状态  不修改的字段别传
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/10 17:19
     *@modifier
     */
    int updateOrder(OrderPO record);


    int commentOrderFinish(@Param("orderId") Long orderId,@Param("score") Integer score);
    /**
     * 方法的功能描述: 获取用户订单列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/13 11:49
     *@modifier
     */
    List<CustomerOrderBO> listCustomerOrder(CustomerzSearchOrderBO customerzSearchOrder);

    int getCustomerOrderCount(CustomerzSearchOrderBO customerzSearchOrder);
    /**
     * 方法的功能描述: 获取C端用户订单详情
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/15 11:26
     *@modifier
     */
    UserOrderVo getOrderDetail(@Param("orderId") Long orderId, @Param("orderNo") String orderNo, @Param("userId") Integer userId);

    /**
     * 获取待接单订单数量
     * @param vendorId
     * @return
     */
    int getWaitReceiveOrder(Integer vendorId);

    /**
     * 获取低评分订单数量
     * @param vendorId
     * @return
     */
    int getLowerOrder(Integer vendorId);

    /**
     * 通过orderNo获取orderId
     * @param orderNo
     * @return
     */
    long getOrderIdByNo(@Param("orderNo") String orderNo);

    /**
     * 删除评价后，更新订单评分
     * @param orderId
     * @return
     */
    int updateScoreAndCommentStatus(Long orderId);

    /**
     * 获取今日销售额
     * @return
     */
    int getTodaySalesAmount(@Param("beginTime") String beginTime,@Param("endTime") String endTime,@Param("vendorId") int vendorId);
    /**
     * 方法的功能描述: 修改30分钟内 未付款订单状态
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/7 16:19
     *@modifier
     */
    void  updateByUserOrderNoPay();
    /**
     * 方法的功能描述:定时任务 获取所有60分钟未接单商家
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 10:58
     *@modifier
     */
    List<OrderPO> listOrderByVendorNoReceiving();
    /**
     * 方法的功能描述: 查询退款中的定时任务
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 10:58
     *@modifier
     */
    List<OrderPO> listOrderByRrundIng();
    /**
     * 方法的功能描述:商家为接单 退款  定时任务
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/9 11:24
     *@modifier
     */
    List<OrderPO> listOrderByVendorNoRefund();


    /**
     * 获取订单商品详情
     * @param orderId
     * @return
     */
    List<Map> getOrderGoodsByOrderId(@Param("orderId") Long orderId);

    /**
     * 获取已结算订单列表
     * @param settlementId
     * @return
     */
    List<OrderPO> getSettlementOrder(@Param("settlementId") Integer settlementId);
}