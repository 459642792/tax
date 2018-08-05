package com.blueteam.wineshop.base.spring.quartz.job;

import com.blueteam.wineshop.service.wechatapplet.OrderRefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 退款中的订单
 *
 * @author xiaojiang
 * @create 2018-02-09  11:33
 */
@Component
public class OrderByRrundIng {
    @Autowired
    private OrderRefundService orderRefundService;
    public void execute() {
        //退款中
        orderRefundService.updateOrderByRrundIng();
    }
}
