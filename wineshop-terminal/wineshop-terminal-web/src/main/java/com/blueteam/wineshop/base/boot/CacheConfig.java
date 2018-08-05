package com.blueteam.wineshop.base.boot;

import org.apache.log4j.Logger;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 缓存 redis配置
 *
 * @author xiaojiang
 * @create 2017-10-27  14:53
 */
@Configuration
@EnableTransactionManagement
@IgnoreMultiComponentScan(ignore = true)
@EnableCaching
public class CacheConfig {

    private static final Logger log = Logger.getLogger(CacheConfig.class);

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("mymaster")
                .sentinel("127.0.0.1", 26379);
//        sentinelConfig.setPassword();
        return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory cf) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(cf);
        return redisTemplate;
    }

//    @Bean
//    public CacheManager cacheManager(RedisTemplate redisTemplate) {
////        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
////        //这里可以设置一个默认的过期时间
////        cacheManager.setDefaultExpiration(300);
//        return cacheManager;
//    }


}
