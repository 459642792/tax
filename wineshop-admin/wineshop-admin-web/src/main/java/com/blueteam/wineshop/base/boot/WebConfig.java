package com.blueteam.wineshop.base.boot;

import com.blueteam.wineshop.base.web.component.CommonsMultipartResolverByUE;
import com.blueteam.wineshop.base.web.component.ExceptionHandlerResolver;
import com.blueteam.wineshop.base.web.interceptor.LoginInterceptorAdmin;
import com.github.bingoohuang.westcache.spring.WestCacheableScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * web应用上下文配置
 * <p/>
 * Created by  NastyNas on 17/8/18.
 */
@Configuration
@EnableWebMvc
@ComponentScan(value = {"com.blueteam"}, basePackageClasses = {WestCacheableScan.class}, excludeFilters = {@ComponentScan.Filter(value = IgnoreMultiComponentScan.class, type = FilterType.ANNOTATION)})
@IgnoreMultiComponentScan(ignore = true)
@WestCacheableScan(basePackages = "com.blueteam")
public class WebConfig extends WebMvcConfigurerAdapter {


    @Autowired
    LoginInterceptorAdmin loginInterceptorAdmin;

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setPrefix("/WEB-INF/classes/com/wineshop/pages/jsp/");//问题来了：为什么不能识别classpath:/com/wineshop/pages/jsp/
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".jsp");   //解析成视图时添加的前缀和后缀
        resolver.setViewClass(JstlView.class);
        resolver.setContentType("text/html");
        return resolver;
    }


    /**
     * 当前DispatcherServlet是拦截的所有请求的请求"/"
     * 此配置要求DispatchServlet将对静态资源的请求转发到Servlet容器中默认的Servlet上，而不是使用DispatcherServlet本身来处理此类请求。
     * <p/>
     * <p/>
     * 原方法注释：
     * /**
     * Configure a handler to delegate unhandled requests by forwarding to the
     * Servlet container's "default" servlet. A common use case for this is when
     * the {@link org.springframework.web.servlet.DispatcherServlet} is mapped to "/" thus overriding the
     * Servlet container's default handling of static resources.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 设置静态资源处理路径
     * <p/>
     * configureDefaultServletHandling+addResourceHandlers配合使用才能DispatchServlet不处理静态资源请求，同时指定静态资源路径
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/");
    }

    /**
     * 设置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptorAdmin);
    }

    /**
     * 全局异常处理器
     *
     * @return
     */
    @Bean
    public HandlerExceptionResolver handlerExceptionResolver() {
        HandlerExceptionResolver handlerExceptionResolver = new ExceptionHandlerResolver();
        return handlerExceptionResolver;
    }

    /**
     * 文件上传解析器
     */
    @Bean
    public CommonsMultipartResolver commonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolverByUE();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSize(10485760000l);
        commonsMultipartResolver.setMaxInMemorySize(40960);
        return commonsMultipartResolver;
    }

    /**
     * 针对返回客户端的text/plain数据 ---通过@ResponseBody返回的数据
     * 设定字符集，默认为ISO-8859-1
     * 当前项目添加了CharacterEncodingFilter,返回客户端的数据无论什么类型都被通过response.setCharacterEncoding("UTF-8");
     *
     * @return
     */
 /*   @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", Charset.forName("UTF-8"))));
        return stringHttpMessageConverter;

    }*/

}
