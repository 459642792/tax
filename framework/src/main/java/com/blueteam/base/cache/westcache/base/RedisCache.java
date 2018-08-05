package com.blueteam.base.cache.westcache.base;

import com.github.bingoohuang.westcache.WestCacheable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简介
 * 使用redis作为缓存存储引擎，支持分布式缓存，线程安全，可自定义缓存刷新策略，自定义缓存生效时间，同时可针对大数据进行快照存储方便快速获取缓存
 * <p>
 * 一 使用场景：
 * 几乎所有的业务都可以使用此类缓存
 * <p>
 * 二 被缓存数据来源：
 * RedisCache注解添加的方法的返回值，RedisCache注解应用于有返回值的方法上
 * <p>
 * 三 数据缓存方式：
 * 使用redis作为缓存存储引擎（支持分布式）
 * <p>
 * 注：redisBean=wineShopAdminJedis表明使用的Redis配置bean为wineShopAdminJedis（由RedisConfig类注入容器，通过代理方法使用JedisCommands）
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WestCacheable(manager = "redis", keyer = "simple", specs = "redisBean=wineShopAdminJedis")
public @interface RedisCache {
    /**
     * 不使用默认缓存key值生成方案，自定义缓存key值
     */
    String key() default "";

    /**
     * 缓存失效时间
     * 失效时间必须是整数，单位是 d/h/m/s 分别代表天/小时/分钟/秒
     * 例如：expireAfterWrite="10h"
     *
     * @return
     */
    String expireAfterWrite() default "";

    /**
     * 缓存刷新策略
     * quartz:定时任务刷新缓存
     * table:基于WESTCACHE_FLUSHER表的缓存刷新，使用
     * <p>
     * 当选择quartz作为刷新策略时，需要指定执行计划scheduled
     *
     * @return
     */
    String flusher() default "";

    /**
     * quartz缓存刷新策略的执行计划
     * <p>
     * Every 1 minute 表示每1分钟
     * Every 30 minutes 表示每30分钟
     * Every 10 hours 表示每10小时
     * Every 60 seconds 表示每60秒
     * At 03:00 表示每天凌晨3点
     * At ??:40 表示每小时的第40分钟
     * 0 20 * * * ? 表示每小时开始20分钟
     * Every 30 minutes from 2016-10-10 to 2017-10-12 表示从2016年10月10日到2017年10月12日之间的每天凌晨3点
     * At 03:00 to 2013-11-01
     * 0 20 * * * ? from 2013-10-10 14:00:00
     *
     * @return
     */
    String scheduled() default "";


    /**
     * 缓存数据太大可以选择设置快照，当检测到当存数据因为太大读取超时时，直接从快照（系统文件快照/redis快照）中读取快照。
     * file：文件快照
     * redis：redis快照
     */
    String snapshot() default "";

}
