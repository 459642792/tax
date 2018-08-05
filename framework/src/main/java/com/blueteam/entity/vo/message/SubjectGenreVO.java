package com.blueteam.entity.vo.message;

import java.util.List;

/**
 * 消息分类
 *
 * @author xiaojiang
 * @create 2018-01-26  17:59
 */
public class SubjectGenreVO {
    /** 类型名称 */
    private String subjectGenreName;
    /**  类型值 */
    private int subjectGenreValue;
    /**  类型未读的数量*/
    private int subjectUnreadCount;
    /**  消息列表*/
    private List<MessageVo> messages;

    public SubjectGenreVO() {
    }

    public SubjectGenreVO(String subjectGenreName, int subjectGenreValue, int subjectUnreadCount, List<MessageVo> messages) {
        this.subjectGenreName = subjectGenreName;
        this.subjectGenreValue = subjectGenreValue;
        this.subjectUnreadCount = subjectUnreadCount;
        this.messages = messages;
    }

    public String getSubjectGenreName() {
        return subjectGenreName;
    }

    public void setSubjectGenreName(String subjectGenreName) {
        this.subjectGenreName = subjectGenreName;
    }

    public int getSubjectGenreValue() {
        return subjectGenreValue;
    }

    public void setSubjectGenreValue(int subjectGenreValue) {
        this.subjectGenreValue = subjectGenreValue;
    }

    public int getSubjectUnreadCount() {
        return subjectUnreadCount;
    }

    public void setSubjectUnreadCount(int subjectUnreadCount) {
        this.subjectUnreadCount = subjectUnreadCount;
    }

    public List<MessageVo> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageVo> messages) {
        this.messages = messages;
    }
}
