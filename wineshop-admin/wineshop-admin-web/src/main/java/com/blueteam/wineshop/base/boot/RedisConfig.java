package com.blueteam.wineshop.base.boot;

import com.blueteam.base.util.DiamondUtil;
import com.github.bingoohuang.westcache.utils.Redis;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis连接池配置
 * Created by  NastyNas on 2017/11/16.
 */
@Configuration
@IgnoreMultiComponentScan(ignore = true)
public class RedisConfig {
    /**
     * 附近酒行后台管理redis连接
     *
     * @return
     */
    @Bean(name = "wineShopAdminJedis")
    public JedisCommands wineShopAdminJedisCommands() {
        String redisHost = DiamondUtil.getPre("REDIS_DATA", "redis.host");
        Integer redisPort = Integer.parseInt(DiamondUtil.getPre("REDIS_DATA", "redis.port"));
        String redisPassword = DiamondUtil.getPre("REDIS_DATA", "redis.pass");
        Integer redisTimeout = Integer.parseInt(DiamondUtil.getPre("REDIS_DATA", "redis.expiration"));
        String redisDataBase = DiamondUtil.getPre("REDIS_DATA", "redis.dbIndex");
        JedisPool pool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword, Integer.parseInt(redisDataBase));
        return Redis.proxyJedisCommands(pool);
    }
}
