package com.blueteam.base.constant;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 登录 注解
 * 加上该注解表示必须登录
 *
 * @author libra
 * Test
 */
@Inherited
@Retention(value = RUNTIME)
public @interface ApiLogin {
    /**
     * 可以登录的用户角色
     *
     * @return
     */
    int userType() default (1 << 30) - 1;


    /**
     * 认证失败提示消息
     *
     * @return
     */
    String message() default "不合法的用户";
}

