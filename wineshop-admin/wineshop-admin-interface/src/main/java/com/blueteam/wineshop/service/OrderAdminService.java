package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.ManualRefundDTO;
import com.blueteam.entity.dto.OrderListSearchDTO;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.entity.vo.AdminOrderDetailVO;
import com.blueteam.entity.vo.AdminOrderListVO;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/8.
 */
public interface OrderAdminService {
    /**
     * 分页查询订单列表VO
     *
     * @param orderListSearchDTO
     * @return
     */
    PageResult<List<AdminOrderListVO>> listOrderInfo(OrderListSearchDTO orderListSearchDTO);

    /**
     * 根据订单id获取订单详情VO
     *
     * @param orderId
     * @return
     */
    AdminOrderDetailVO getOrderDetail(String orderId);

    /**
     * 根据订单id保存人工退款信息
     *
     * @param orderId
     */
    void manualRefund(ManualRefundDTO orderId);
}
