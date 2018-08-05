package com.blueteam.base.constant;

import java.lang.annotation.Inherited;

/**
 * 管理员登录
 * 除了验证userType 还会验证 extendId 必须大于0
 *
 * @author libra
 */
@Inherited
@ApiLogin(userType = Enums.UserType.Admin, message = "您不是合法的管理员")
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AdminApiLogin {

}
