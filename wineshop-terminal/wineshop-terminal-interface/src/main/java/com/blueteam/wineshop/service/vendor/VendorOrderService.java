package com.blueteam.wineshop.service.vendor;

import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.VendorOrderDTO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.vo.OrderStatisticsVO;
import com.blueteam.entity.vo.VendorOrderVO;

import java.util.List;

/**
 * 订单接口
 *
 * ljc 2018年1月8日 14:39:15
 */
public interface VendorOrderService {

    /**
     * 获取订单列表
     * @param dto
     * @return
     */
    ApiResult<List<VendorOrderVO>> getOrderList(VendorOrderDTO dto);


    /**
     * 接单
     * @param dto
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
     * 订单详情
     * @param orderId
     * @return
     */
    VendorOrderVO orderDetail(Long orderId);

    VendorOrderVO orderDetail2(String orderNo,Long orderId);


    /**
     * 评价完成，修改订单状态
     * @param orderId
     * @return
     */
    int commentOrderFinish(Long orderId,Integer score);


    /**
     * 订单统计
     * @param vendorId
     * @return
     */
    OrderStatisticsVO orderStatistics(Integer vendorId);

    Long getOrderIdByNo(String orderNo);

    int updateScoreAndCommentStatus(Long orderId);

    OrderPO getByOrderNo(String orderNo);

    OrderPO getByOrderId(Long orderId);

    /**
     * 首页数据
     * @param vendorId
     * @return
     */
    OrderStatisticsVO indexStatistics(Integer vendorId);


    int getCount(VendorOrderDTO dto);
}
