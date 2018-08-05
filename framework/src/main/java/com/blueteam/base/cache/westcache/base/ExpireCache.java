package com.blueteam.base.cache.westcache.base;

import com.github.bingoohuang.westcache.WestCacheable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 简介：
 * 使用expiringmap作为缓存引擎，可以自定义失效时间的内存缓存方案。
 * <p>
 * 一使用场景:
 * 非分布式的数据内存临时存储。 如：获取微信的access token，由于微信access token接口端会限制每天的访问次数以及2小时生效时间
 * 因此可以通过设置ExpireCache的生效时间为2小时可以完美解决问题。
 * <p>
 * 二数据缓存方式：
 * expiringmap内存缓存(内存缓存本质上数据都存储在map中)
 * <p>
 * 二缓存刷新的两种情况
 * 1 服务器重启，内存缓存失效
 * 2 缓存超过生效时间
 * <p>
 * <p>
 * 注：expiringmap是一款高性能，低开小，线程安全的基于ConcurrentMap的可以灵活设置缓存生效时间的缓存存储方案。
 * 参考资料：https://github.com/jhalterman/expiringmap
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@WestCacheable(manager = "expiring", keyer = "simple")
public @interface ExpireCache {
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
}
