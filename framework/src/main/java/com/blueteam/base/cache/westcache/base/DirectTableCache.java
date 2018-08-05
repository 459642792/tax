package com.blueteam.base.cache.westcache.base;

import com.github.bingoohuang.westcache.WestCacheable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简介：
 * 使用guawa作为缓存引擎，利用表WESTCACHE_FLUSHER作为缓存刷新控制的缓存方案
 * <p>
 * 一使用场景：
 * 1 需要缓存的数据比较固定，通常是一些特定固定数据
 * 2 一旦数据变更需要及时刷新缓存
 * <p>
 * <p>
 * 如：省份地市信息
 * <p>
 * 二被缓存数据来源：
 * 1 redis（利用redis作为数据库的持久化特性）--不常用
 * 2 Callable的回调类的call方法返回结果
 * 3 WESTCACHE_FLUSHER表DIRECT_VALUE字段（json）
 * 注：数据存储位置根据需求三选一，由于被缓存数据来源不是注解添加方法的返回，因此DirectTableCache注释直接添加到接口上
 * <p>
 * 三数据缓存方式：
 * guava内存缓存(内存缓存本质上数据都存储在map中)
 * <p>
 * 四缓存刷新的两种情况：
 * 1 服务器重启，内存缓存失效
 * 2 每隔一分钟检查WESTCACHE_FLUSHER表数据，找到缓存版本号发生变动的缓存key值，对这些缓存进行刷新。
 * <p>
 * // * <p>
 * <p>
 * Created by  NastyNas on 2017/12/6.
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WestCacheable(keyer = "simple", flusher = "table")
public @interface DirectTableCache {
    //不使用默认缓存key值生成方案，自定义缓存key值
    String key() default "";
}
