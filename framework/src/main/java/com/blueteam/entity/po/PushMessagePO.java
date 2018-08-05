package com.blueteam.entity.po;

import java.util.Date;

/**
 * Created by huangqijun on 18/2/28.
 */
public class PushMessagePO {
    private Integer pushMessageId;//主键ID
    private String title;//消息标题
    private String content;//消息内容
    private Integer type;//消息类型 1-系统消息 2-短信
    private Integer targetType;//推送对象类型 -1-部分用户 0-全部 1-用户 2-商户
    private String phones;//短信接受者手机号，用分号隔开
    private Integer pushCount;//消息接收人数
    private String createBy;//发布消息的管理员名称
    private Date createTime;//发布时间

    public Integer getPushMessageId() {
        return pushMessageId;
    }

    public void setPushMessageId(Integer pushMessageId) {
        this.pushMessageId = pushMessageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    public Integer getPushCount() {
        return pushCount;
    }

    public void setPushCount(Integer pushCount) {
        this.pushCount = pushCount;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
