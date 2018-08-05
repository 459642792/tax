package com.blueteam.wineshop.base.spring.quartz;

import com.blueteam.wineshop.base.boot.IgnoreMultiComponentScan;
import com.blueteam.wineshop.base.spring.quartz.job.UserOrderNotPayJob;
import org.quartz.SchedulerException;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.rmi.server.ServerCloneException;

/**
 * @author xiaojiang
 * @create 2018-02-08  11:26
 */
@Configuration
public class QuartzJobListener extends  QuartzInitializerListener {

    public static final String MY_CONTEXT_NAME = "servletContext";

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        super.contextDestroyed(sce);
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // TODO Auto-generated method stub
        super.contextInitialized(sce);
        ServletContext servletContext = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) servletContext
                .getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
        try {
            factory.getScheduler().getContext()
                    .put(QuartzJobListener.MY_CONTEXT_NAME, servletContext);
        } catch (SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
