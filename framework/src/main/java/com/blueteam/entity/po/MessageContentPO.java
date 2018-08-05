
package com.blueteam.entity.po;

import java.util.Date;

/**
 * 消息详细内容(MESSAGE_CONTENT)
 * 
 * @author xiaojiang
 * @version 1.0.0 2018-01-22
 */

public class MessageContentPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5685034827758460617L;
    
    /** id */
    private Integer messageContentId;
    
    /** 主题id */
    private Integer messageSubjectId;
    
    /** 主题id */
    private String messageContent;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 创建人 */
    private String createBy;
    
    /**
     * 获取id
     * 
     * @return id
     */
    public Integer getMessageContentId() {
        return this.messageContentId;
    }
     
    /**
     * 设置id
     * 
     * @param messageContentId
     *          id
     */
    public void setMessageContentId(Integer messageContentId) {
        this.messageContentId = messageContentId;
    }
    
    /**
     * 获取主题id
     * 
     * @return 主题id
     */
    public Integer getMessageSubjectId() {
        return this.messageSubjectId;
    }
     
    /**
     * 设置主题id
     * 
     * @param messageSubjectId
     *          主题id
     */
    public void setMessageSubjectId(Integer messageSubjectId) {
        this.messageSubjectId = messageSubjectId;
    }
    
    /**
     * 获取主题id
     * 
     * @return 主题id
     */
    public String getMessageContent() {
        return this.messageContent;
    }
     
    /**
     * 设置主题id
     * 
     * @param messageContent
     *          主题id
     */
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    
    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }
     
    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 获取创建人
     * 
     * @return 创建人
     */
    public String getCreateBy() {
        return this.createBy;
    }
     
    /**
     * 设置创建人
     * 
     * @param createBy
     *          创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public MessageContentPO() {
    }

    public MessageContentPO(Integer messageContentId, Integer messageSubjectId, String messageContent, Date createTime, String createBy) {
        this.messageContentId = messageContentId;
        this.messageSubjectId = messageSubjectId;
        this.messageContent = messageContent;
        this.createTime = createTime;
        this.createBy = createBy;
    }

    @Override
    public String toString() {
        return "MessageContentPO{" +
                "messageContentId=" + messageContentId +
                ", messageSubjectId=" + messageSubjectId +
                ", messageContent='" + messageContent + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                '}';
    }
}