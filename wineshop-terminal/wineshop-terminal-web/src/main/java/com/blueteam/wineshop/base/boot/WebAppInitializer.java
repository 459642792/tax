package com.blueteam.wineshop.base.boot;

import com.blueteam.wineshop.base.spring.quartz.QuartzConfig;
import com.blueteam.wineshop.base.web.filter.WechatMomentCharsetFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration;

/**
 * Created by  NastyNas on 17/8/8.
 * <p/>
 * 启动设置类
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {


    /**
     * 设置根上下文
     *
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{RepositoryConfig.class, MybatisScannerConfig.class};
    }

    /**
     * 设置web上下文
     *
     * @return
     */

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class, QuartzConfig.class, AopConfig.class, WebSocketConfig.class};
    }

    /**
     * 默认匹配：精确匹配和模糊匹配依次都没有匹配成功 直接进入默认匹配
     *
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        registration.setLoadOnStartup(1);
    }

    /**
     * 添加字符过滤器
     *
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);
        return new Filter[]{characterEncodingFilter, new WechatMomentCharsetFilter()};
    }

}
