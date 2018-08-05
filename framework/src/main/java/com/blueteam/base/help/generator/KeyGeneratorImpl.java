package com.blueteam.base.help.generator;

import java.lang.annotation.*;

/**
 * key生成器标志
 * Created by  NastyNas on 2017/12/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER})
@Documented
public @interface KeyGeneratorImpl {
    String key();
}
