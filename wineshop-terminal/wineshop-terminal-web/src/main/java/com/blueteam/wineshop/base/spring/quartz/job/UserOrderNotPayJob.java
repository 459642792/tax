package com.blueteam.wineshop.base.spring.quartz.job;


import com.blueteam.wineshop.service.wechatapplet.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaojiang
 * @create 2018-02-07  14:27
 */

@Component
public class UserOrderNotPayJob{
    @Autowired
    private OrderService orderService;
    public void execute() {
        orderService.updateByUserOrderNoPay();
    }
}

