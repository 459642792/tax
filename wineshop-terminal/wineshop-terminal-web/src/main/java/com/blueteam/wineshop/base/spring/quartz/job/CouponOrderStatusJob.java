package com.blueteam.wineshop.base.spring.quartz.job;

import com.blueteam.wineshop.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询微信订单状态并更新本地数据库
 *
 * @author xiaojiang 2017年3月7日
 * @version 1.0
 * @since 1.0 2017年3月7日
 */
@Component
public class CouponOrderStatusJob {
    @Autowired
    OrderInfoService orderInfoService;

    /**
     * 定时查询微信订单 并关闭数据
     *
     * @author xiaojiang 2017年3月9日
     * @version 1.0
     * @since 1.0 2017年3月9日
     */
    public void execute() {
        System.out.println("执行一次定时任务");
//        orderInfoService.queryCouponsOrderStatus();
    }


}