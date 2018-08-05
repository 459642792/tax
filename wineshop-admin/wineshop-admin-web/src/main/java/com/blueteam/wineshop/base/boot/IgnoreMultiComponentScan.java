package com.blueteam.wineshop.base.boot;

import java.lang.annotation.*;

/**
 * Created by  NastyNas on 17/8/12.
 *
 * @Configuration注解的配置类应该避免被自动扫描防止二次装配
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreMultiComponentScan {

    boolean ignore() default false;
}
