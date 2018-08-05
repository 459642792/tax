package com.blueteam.base.constant;

import java.lang.annotation.Inherited;

/**
 * 运营商登录
 * 除了验证userType 还会验证 extendId 必须大于0
 *
 * @author libra
 */
@Inherited
@ApiLogin(userType = Enums.UserType.Carriers, message = "您不是合法的运营商")
@java.lang.annotation.Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@ExtendApiLogin(message = "您不是合法的运营商")
public @interface CarriersApiLogin {

}
