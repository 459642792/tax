package com.blueteam.base.cache.redis;

import com.blueteam.base.util.DiamondUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.beans.ConstructorProperties;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 附近酒行redis配置 连接池获取
 * Created by  NastyNas on 2017/12/18.
 * redis
 */
public final class Redis {
    private static String redisHost;
    private static Integer redisPort;
    private static String redisPassword;
    private static Integer redisTimeout;
    private static String redisDataBase;
    private static JedisPool pool;

    static {
        redisHost = DiamondUtil.getPre("REDIS_DATA", "redis.host");
        redisPort = Integer.parseInt(DiamondUtil.getPre("REDIS_DATA", "redis.port"));
        redisPassword = DiamondUtil.getPre("REDIS_DATA", "redis.pass");
        redisTimeout = Integer.parseInt(DiamondUtil.getPre("REDIS_DATA", "redis.expiration"));
        redisDataBase = DiamondUtil.getPre("REDIS_DATA", "redis.dbIndex");
        pool = new JedisPool(new JedisPoolConfig(), redisHost, redisPort, redisTimeout, redisPassword, Integer.parseInt(redisDataBase));
    }

    /**
     * 利用jdk动态代理从连接池获取redis连接，使用后不用关闭连接
     *
     * @return
     */
    public static JedisCommands getJedis() {
        return (JedisCommands) Proxy.newProxyInstance(Redis.JedisInvocationHandler.class.getClassLoader(), new Class[]{JedisCommands.class}, new Redis.JedisInvocationHandler(pool));
    }

    /**
     * redis connect close aop handler
     */
    public static class JedisInvocationHandler implements InvocationHandler {
        final JedisPool pool;

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            final Jedis jedis = this.pool.getResource();
            Object result;
            try {
                result = method.invoke(jedis, args);
            } finally {
                if (jedis != null) {
                    jedis.close();
                }
            }
            return result;
        }

        @ConstructorProperties({"pool"})
        public JedisInvocationHandler(JedisPool pool) {
            this.pool = pool;
        }
    }


    public static void main(String[] args) {
        JedisCommands redis = Redis.getJedis();
        redis.set("zhangxin", "1234");
        redis.persist("zhangxin");
    }

}
