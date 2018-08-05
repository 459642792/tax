package com.blueteam.entity.dto;


import com.blueteam.base.util.DateUtil;

import java.util.Date;

/**
 * Created by libra on 2017/5/23.
 * <p>
 * 用于商家列表显示的信息dto
 */
public class VendorMessage {

    /**
     * 消息组
     */
    private String messageGroup;

    /**
     * 类型编码
     */
    private String typeCode;
    /**
     * 未读数量
     */
    private int notReadCount;
    /**
     * 最后一次的消息
     */
    private String lastMessage;

    /**
     * 发送时间
     */
    private Date sendingTime;

    public String getSendingTimeStr() {
        if (this.getSendingTime() != null)
            return DateUtil.format(this.getSendingTime());
        return "";
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getMessageGroup() {
        return messageGroup;
    }

    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getNotReadCount() {
        return notReadCount;
    }

    public void setNotReadCount(int notReadCount) {
        this.notReadCount = notReadCount;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
