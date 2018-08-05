package com.blueteam.base.util;
/**
 * DTO字段校验注解
 * Created by  NastyNas on 2017/10/28.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface FieldValidate {
    //不为空
    boolean notNull() default false;

    //不为空的校验类型
    String[] notNullValidateTypes() default {};

    //定长
    int fixLen() default 0;

    //最大长度
    int maxLen() default 0;

    //最小长度
    int minLen() default 0;

}