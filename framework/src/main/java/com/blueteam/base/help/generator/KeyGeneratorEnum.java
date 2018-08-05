package com.blueteam.base.help.generator;

/**
 * 主键key枚举类
 * Created by  NastyNas on 2017/12/26.
 */
public enum KeyGeneratorEnum {
    ORDER_ID("orderId", "订单id"),
    ORDER_NO("orderNo", "订单号"),
    REFUND_NO("refundId", "退款单号");
    String key;
    String name;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    KeyGeneratorEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
