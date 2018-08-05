package com.blueteam.wineshop.base.spring.help;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by  NastyNas on 17/8/29.
 */
@Component
public class SpringContextHelper implements ApplicationContextAware {
    /**
     * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
     */
    private volatile static ApplicationContext springContext; //Spring应用上下文环境

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springContext = applicationContext;
    }

    /**
     * 获取bean
     *
     * @param cls
     * @param <E>
     * @return
     */
    public static <E> E getBean(Class<E> cls) {
        return springContext.getBean(cls);
    }

    public static Object getBean(String name) {
        return springContext.getBean(name);
    }
}
