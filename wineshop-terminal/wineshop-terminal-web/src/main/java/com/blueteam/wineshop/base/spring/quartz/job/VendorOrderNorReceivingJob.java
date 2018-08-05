package com.blueteam.wineshop.base.spring.quartz.job;

import com.blueteam.wineshop.service.wechatapplet.OrderRefundService;
import com.blueteam.wineshop.service.wechatapplet.OrderService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商家60分钟未接单
 *
 * @author xiaojiang
 * @create 2018-02-07  16:27
 */
@Component
public class VendorOrderNorReceivingJob {
        @Autowired
        private OrderRefundService orderRefundService;
        public void execute() {
                orderRefundService.vendorOrderRufund();
        }
}
