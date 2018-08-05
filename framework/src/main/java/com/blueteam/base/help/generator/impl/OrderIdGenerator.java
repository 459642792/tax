package com.blueteam.base.help.generator.impl;

import com.blueteam.base.cache.redis.Redis;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.help.generator.KeyGenerator;
import com.blueteam.base.help.generator.KeyGeneratorImpl;
import com.blueteam.base.lang.ThreadSafeSimpleDateFormat;
import com.blueteam.base.util.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 订单内部标识orderId生成器
 * Created by  NastyNas on 2017/12/26.
 */
@KeyGeneratorImpl(key = "orderId")
public class OrderIdGenerator implements KeyGenerator {
    private static final Logger logger = LoggerFactory.getLogger(OrderIdGenerator.class);
    private static final ThreadSafeSimpleDateFormat sdf = new ThreadSafeSimpleDateFormat("yyMMddHHmmss");
    //@TODO  redis SEQ_ORDER_ID key 定时任务置0
    private static final String SEQUENCE_KEY = "SEQ_ORDER_ID";
    private static final Long REDIS_NUMBER_BASE = 100000L;

    @Override
    public synchronized Long generateKey() {
        //12位时间字符串
        String timeString = sdf.format(new Date());
        //6位流水号，每天凌晨00：00从零开始
        Long serialNumber;
        try {
            serialNumber = Redis.getJedis().incr(SEQUENCE_KEY);
            if (serialNumber == null) {
                throw new BusinessException();
            }
            serialNumber = REDIS_NUMBER_BASE + serialNumber;
        } catch (Exception e) {
            serialNumber = Long.parseLong(RandomUtils.genRandomNum(6));
        }
        return Long.parseLong(timeString + String.valueOf(serialNumber));
    }
}
