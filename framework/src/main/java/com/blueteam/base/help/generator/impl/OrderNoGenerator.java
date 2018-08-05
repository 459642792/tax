package com.blueteam.base.help.generator.impl;

import com.blueteam.base.help.generator.KeyGenerator;
import com.blueteam.base.help.generator.KeyGeneratorImpl;
import com.blueteam.base.help.snowflake.SnowflakeIdWorker;

/**
 * 订单号orderNo生成器
 * Created by  NastyNas on 2017/12/26.
 */
@KeyGeneratorImpl(key = "orderNo")
public class OrderNoGenerator implements KeyGenerator {
    @Override
    public Long generateKey() {
        //使用twitter snowflake id生成方案
        return SnowflakeIdWorker.getDefaultWorker().nextId();
    }

    public static void main(String[] args) {
        System.out.println(new OrderNoGenerator().generateKey());
    }
}
