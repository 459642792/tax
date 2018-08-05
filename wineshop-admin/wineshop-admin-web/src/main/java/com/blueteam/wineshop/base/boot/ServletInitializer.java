package com.blueteam.wineshop.base.boot;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * Created by  NastyNas on 17/8/18.
 * <p/>
 * 设置Servlet以及对应Filter，包括DispatcherServlet以及自定义Servlet
 */
public class ServletInitializer implements WebApplicationInitializer {
    // SpringMVC默认servlet:DispatchServlet的servletName
    public static final String DEFAULT_SERVLET_NAME = "dispatcher";

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter("webAppRootKey", "admin-wineshop");

        //激活profile 不同环境下配置不同：test/dev/prod,当前使用diamond配置，因此不再使用此环境配置
//        servletContext.setInitParameter("spring.profiles.active", "test");
        //为DispatchServlet添加过滤器
//        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(DEFAULT_SERVLET_NAME, MyFilter3.class);
//        filterRegistration.addMappingForServletNames(EnumSet.of(DispatcherType.REQUEST), false, DEFAULT_SERVLET_NAME);

    }
}
