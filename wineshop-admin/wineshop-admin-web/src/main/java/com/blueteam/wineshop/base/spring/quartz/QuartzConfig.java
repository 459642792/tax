package com.blueteam.wineshop.base.spring.quartz;

import com.blueteam.wineshop.base.boot.IgnoreMultiComponentScan;
import com.blueteam.wineshop.base.spring.quartz.job.CouponOrderStatusJob;
import com.blueteam.wineshop.base.spring.quartz.job.PromotionStatusJob;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


/**
 * Created by  NastyNas on 17/8/15.
 */
@Configuration
@IgnoreMultiComponentScan(ignore = true)
public class QuartzConfig {

    @Autowired
    CouponOrderStatusJob couponOrderStatusJob;

    @Autowired
    PromotionStatusJob promotionStatusJob;

    /*微信订单状态任务 start*/
    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(couponOrderStatusJob);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0 */4 * * ?");
        return cronTriggerFactoryBean;
    }
    /*微信订单状态任务 end*/

    /*首页活动状态更新任务 start*/
    @Bean
    public MethodInvokingJobDetailFactoryBean promotiongJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(promotionStatusJob);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean promotionTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(promotiongJobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0 */1 * * ?");
        return cronTriggerFactoryBean;
    }
    /*首页活动状态更新任务 end*/

    @Bean
    @Lazy(value = false)
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(new Trigger[]{cronTriggerFactoryBean().getObject(), promotionTriggerFactoryBean().getObject()});
        return schedulerFactoryBean;
    }

}
