package com.blueteam.wineshop.base.spring.quartz;

import com.blueteam.base.util.ueditor.define.State;
import com.blueteam.wineshop.base.boot.IgnoreMultiComponentScan;
import com.blueteam.wineshop.base.spring.quartz.job.OrderByRrundIng;
import com.blueteam.wineshop.base.spring.quartz.job.OrderByVendorNoRefund;
import com.blueteam.wineshop.base.spring.quartz.job.UserOrderNotPayJob;
import com.blueteam.wineshop.base.spring.quartz.job.VendorOrderNorReceivingJob;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

/**
 * Created by  NastyNas on 17/8/15.
 */
@Configuration
@IgnoreMultiComponentScan(ignore = true)
@EnableWebMvc
public class QuartzConfig {
//    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        @Autowired
//    CouponOrderStatusJob couponOrderStatusJob;
    @Autowired
    UserOrderNotPayJob userOrderNotPayJob;
    @Autowired
    VendorOrderNorReceivingJob vendorOrderNorReceivingJob;
    @Autowired
    OrderByRrundIng orderByRrundIng;
    @Autowired
    OrderByVendorNoRefund orderByVendorNoRefund;
//    /*微信订单状态任务 start*/
    @Bean
    public MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(userOrderNotPayJob);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(methodInvokingJobDetailFactoryBean().getObject());
        cronTriggerFactoryBean.setCronExpression("0 */1 * * * ?");
        return cronTriggerFactoryBean;
    }
    //
    @Bean
    public MethodInvokingJobDetailFactoryBean VendorOrderNorReceivingJob() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(vendorOrderNorReceivingJob);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerVendorOrderNorReceivingJob() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(VendorOrderNorReceivingJob().getObject());
        cronTriggerFactoryBean.setCronExpression("0 */2 * * * ?");
        return cronTriggerFactoryBean;
    }
    //
    @Bean
    public MethodInvokingJobDetailFactoryBean VendorOrderByRrundIng() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(orderByRrundIng);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerOrderByRrundIng() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(VendorOrderByRrundIng().getObject());
        cronTriggerFactoryBean.setCronExpression("*/30 */1 * * * ?");
        return cronTriggerFactoryBean;
    }

    //商家3天未退款
    @Bean
    public MethodInvokingJobDetailFactoryBean VendorOrderByVendorNoRefund() {
        MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        methodInvokingJobDetailFactoryBean.setTargetObject(orderByVendorNoRefund);
        methodInvokingJobDetailFactoryBean.setTargetMethod("execute");
        return methodInvokingJobDetailFactoryBean;
    }
    @Bean
    public CronTriggerFactoryBean cronTriggerOrderByVendorNoRefund() {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(VendorOrderByVendorNoRefund().getObject());
        cronTriggerFactoryBean.setCronExpression("0 0 */1 * * ?");
        return cronTriggerFactoryBean;
    }
//
    @Bean
    @Lazy(value = false)
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(new Trigger[]{
                cronTriggerFactoryBean().getObject(),
                cronTriggerVendorOrderNorReceivingJob().getObject(),
                cronTriggerOrderByRrundIng().getObject(),
                cronTriggerOrderByVendorNoRefund().getObject()});
        return schedulerFactoryBean;
    }
    /*微信订单状态任务 end*/

//    @Bean
//    public QuartzJobListener executorListener() {
//        return new QuartzJobListener();
//    }

//    @Bean
//    @Lazy(value = false)
//    public  SchedulerFactoryBean schedulerFactoryBean() throws IOException {
//        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//        //QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
//        factory.setOverwriteExistingJobs(true);
//        // 延时启动
//        factory.setStartupDelay(50);
//        // 自定义Job Factory，用于Spring注入
//        factory.setJobFactory(new MyJobFactory());
////        factory.setSchedulerFactoryClass(schedulerFactory.getClass());
//        //设置自动启动
//        factory.setAutoStartup(true);
////        startJobs();
//        return factory;
//    }


//
//    public  Boolean  startJobs(){
//        addJob("30分钟未付款，订单关闭", "订单", "ORDER_CLOSE", "ORDER", UserOrderNotPayJob.class, "1 * * * * ?");
////        addJob("60分钟商家未接单，订单退款给用户", "订单", "ORDER_REFUND", "ORDER", VendorOrderNorReceivingJob.class, "1 * * * * ?");
//        return true;
//    }


}
