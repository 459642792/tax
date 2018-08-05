package com.blueteam.base.help.message;

/**
 *  消息枚举类
 * @author xiaojiang
 * @create 2018-01-23  10:07
 */
public enum MessageEnum {
    ORDER_MESSAGE("order_message","订单消息key");
    String classify;
    String description;

    MessageEnum(String classify, String description) {
        this.classify = classify;
        this.description = description;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
