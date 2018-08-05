package com.blueteam.wineshop.mapper;

import com.blueteam.entity.bo.*;
import com.blueteam.entity.dto.OrderListSearchDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.po.RefundResultPO;
import com.blueteam.entity.po.StatePO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by  NastyNas on 2018/1/8.
 */
public interface OrderAdminMapper {
    Integer countOrderList(OrderListSearchDTO orderListSearchDTO);

    List<AdminOrderBO> listOrderInfo(OrderListSearchDTO orderListSearchDTO);

    AdminOrderDetailBO getOrderDetail(Long orderId);

    List<AdminOrderGoodsBO> getOrderGoodsDetail(Long orderId);

    List<AdminOrderApplyRefundBO> listApplyRefundInfo(Long orderId);

    List<AdminOrderRefundResultBO> listRefundResultInfo(Long orderId);

    StatePO getStatePO(@Param("state") Integer state, @Param("type") Integer type);

    Integer updateOrderInfo(OrderPO orderPO);

    Integer saveManualRefundResult(RefundResultPO refundResultPO);
}
