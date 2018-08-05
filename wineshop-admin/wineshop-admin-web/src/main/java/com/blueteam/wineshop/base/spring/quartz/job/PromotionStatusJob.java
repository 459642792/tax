package com.blueteam.wineshop.base.spring.quartz.job;

import com.blueteam.wineshop.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by huangqijun on 18/2/5.
 */
@Component
public class PromotionStatusJob {

    @Autowired
    PromotionService promotionService;

    public void execute(){
        System.out.println("promotion job start");
        promotionService.executeCheckStatus();
    }
}
