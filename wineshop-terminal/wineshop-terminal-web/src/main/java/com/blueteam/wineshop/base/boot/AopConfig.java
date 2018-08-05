package com.blueteam.wineshop.base.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by  NastyNas on 17/8/15.
 */
@Configuration
@IgnoreMultiComponentScan(ignore = true)
@EnableAspectJAutoProxy
public class AopConfig {


}
