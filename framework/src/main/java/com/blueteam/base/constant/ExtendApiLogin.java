package com.blueteam.base.constant;

import java.lang.annotation.Inherited;

/**
 * 登录 注解
 * 加上该注解表示必须登录
 * 并且会验证登录里面的extendId 必须大于0
 *
 * @author libra
 */
@Inherited
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ExtendApiLogin {
    /**
     * 认证失败提示消息
     *
     * @return
     */
    String message() default "不合法的用户";
}
