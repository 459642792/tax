package com.blueteam.wineshop.base.spring.quartz.job;

import com.blueteam.wineshop.service.wechatapplet.OrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商家3天未退款
 *
 * @author xiaojiang
 * @create 2018-02-09  11:32
 */


@Component
public class OrderByVendorNoRefund {
    @Autowired
    private OrderRefundService orderRefundService;
    public void execute() {
        orderRefundService.updateOrderByVendorNoRefund();
    }
}
