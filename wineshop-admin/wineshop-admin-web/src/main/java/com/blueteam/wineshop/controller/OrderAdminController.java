package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.AdminApiLogin;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.vo.AdminOrderDetailVO;
import com.blueteam.entity.vo.AdminOrderListVO;
import com.blueteam.wineshop.service.OrderAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 订单管理
 * Created by  NastyNas on 2018/1/8.
 */
@Controller
@RequestMapping("/orderAdmin")
@AdminApiLogin
public class OrderAdminController extends BaseController {

    @Autowired
    OrderAdminService orderAdminService;

    /**
     * 订单列表页分页展示
     *
     * @return
     */
    @RequestMapping(value = "/listOrder", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult listOrder(OrderListSearchDTO orderListSearchDTO) {
        PageResult<List<AdminOrderListVO>> orderInfoList = orderAdminService.listOrderInfo(orderListSearchDTO);
        return ApiResult.success(orderInfoList.getList(), orderInfoList.getCount());
    }

    /**
     * 订单详情页展示
     *
     * @return
     */
    @RequestMapping(value = "/getOrderDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult getOrderDetail(@RequestParam("orderId") String orderId) {
        AdminOrderDetailVO orderDetailVO = orderAdminService.getOrderDetail(orderId);
        return ApiResult.success(orderDetailVO);
    }

    /**
     * 人工退款
     *
     * @return
     */
    @RequestMapping(value = "/manualRefund", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult manualRefund(ManualRefundDTO manualRefundDTO) {
        orderAdminService.manualRefund(manualRefundDTO);
        return ApiResult.success();
    }


    /**
     * 页面跳转
     *
     * @return
     */
    @RequestMapping(value = "/showOrderList", method = RequestMethod.GET)
    public ModelAndView showOrderList() {
        return new ModelAndView("tradeinfo/orderadmin/order_admin_list");
    }


}
