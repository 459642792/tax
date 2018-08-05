package com.blueteam.entity.po;

import java.util.Date;

/**
 * 消息内容(MESSAGE_SUBJECT)
 *
 * @author xiaojiang
 * @version 1.0.0 2018-01-22
 */
public class MessageSubjectPO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -9145930592669986376L;
    /** 未读 */
    public static final  int SUBJECT_STATE_UNREAD = 0;
    /** 已读 */
    public static final  int SUBJECT_STATE_READ = 1;
    public  enum  messageSource{
        ADMIN(1,"平台"),
        OPERATOR(2,"运营商"),
        VENDOR(3,"商家"),
        USER(4,"用户"),
        OTHER(9,"其它");
        public   int genre;
        public  String description;
        private messageSource(int genre, String description) {
            this.genre = genre;
            this.description = description;
        }
        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    /** 主键 */
    private Integer messageSubjectId;

    /** 用户id 针对分组用户的提醒0 */
    private Integer userId;

    /** 消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它 */
    private Integer messageSource;

    /** 详细内容id 根据需求关联表 */
    private Long detailedId;

    /** 主题分类 */
    private String subjectGenre;

    /** 主题类型 */
    private Integer subjectType;

    /** 主题状态 0 未读 1已读 */
    private Integer subjectState;

    /** 主题标题 */
    private String subjectTitle;

    /** 主题内容 */
    private String subjectContent;

    /** 创建时间 */
    private Date createTime;

    /** 创建人 */
    private String createBy;

    /** 修改时间 */
    private Date updateTime;

    /** 修改人 */
    private String updateBy;
    private int userType;

    /**
     * 获取主键
     *
     * @return 主键
     */
    public Integer getMessageSubjectId() {
        return this.messageSubjectId;
    }

    /**
     * 设置主键
     *
     * @param messageSubjectId
     *          主键
     */
    public void setMessageSubjectId(Integer messageSubjectId) {
        this.messageSubjectId = messageSubjectId;
    }

    /**
     * 获取用户id 针对分组用户的提醒0
     *
     * @return 用户id 针对分组用户的提醒0
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置用户id 针对分组用户的提醒0
     *
     * @param userId
     *          用户id 针对分组用户的提醒0
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它
     *
     * @return 消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它
     */
    public Integer getMessageSource() {
        return this.messageSource;
    }

    /**
     * 设置消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它
     *
     * @param messageSource
     *          消息来源  1 平台 2 运营商 3 商家 4 用户  9 其它
     */
    public void setMessageSource(Integer messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 获取详细内容id 根据需求关联表
     *
     * @return 详细内容id 根据需求关联表
     */
    public Long getDetailedId() {
        return this.detailedId;
    }

    /**
     * 设置详细内容id 根据需求关联表
     *
     * @param detailedId
     *          详细内容id 根据需求关联表
     */
    public void setDetailedId(Long detailedId) {
        this.detailedId = detailedId;
    }

    /**
     * 获取主题分类
     *
     * @return 主题分类
     */
    public String getSubjectGenre() {
        return this.subjectGenre;
    }

    /**
     * 设置主题分类
     *
     * @param subjectGenre
     *          主题分类
     */
    public void setSubjectGenre(String subjectGenre) {
        this.subjectGenre = subjectGenre;
    }

    /**
     * 获取主题类型
     *
     * @return 主题类型
     */
    public Integer getSubjectType() {
        return this.subjectType;
    }

    /**
     * 设置主题类型
     *
     * @param subjectType
     *          主题类型
     */
    public void setSubjectType(Integer subjectType) {
        this.subjectType = subjectType;
    }

    /**
     * 获取主题状态 0 未读 1已读
     *
     * @return 主题状态 0 未读 1已读
     */
    public Integer getSubjectState() {
        return this.subjectState;
    }

    /**
     * 设置主题状态 0 未读 1已读
     *
     * @param subjectState
     *          主题状态 0 未读 1已读
     */
    public void setSubjectState(Integer subjectState) {
        this.subjectState = subjectState;
    }

    /**
     * 获取主题标题
     *
     * @return 主题标题
     */
    public String getSubjectTitle() {
        return this.subjectTitle;
    }

    /**
     * 设置主题标题
     *
     * @param subjectTitle
     *          主题标题
     */
    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    /**
     * 获取主题内容
     *
     * @return 主题内容
     */
    public String getSubjectContent() {
        return this.subjectContent;
    }

    /**
     * 设置主题内容
     *
     * @param subjectContent
     *          主题内容
     */
    public void setSubjectContent(String subjectContent) {
        this.subjectContent = subjectContent;
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

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime
     *          修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取修改人
     *
     * @return 修改人
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 设置修改人
     *
     * @param updateBy
     *          修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
    public MessageSubjectPO() {
    }

    public MessageSubjectPO(Integer messageSubjectId, Integer userId, Integer messageSource, Long detailedId, String subjectGenre, Integer subjectType, Integer subjectState, String subjectTitle, String subjectContent, Date createTime, String createBy, Date updateTime, String updateBy, int userType) {
        this.messageSubjectId = messageSubjectId;
        this.userId = userId;
        this.messageSource = messageSource;
        this.detailedId = detailedId;
        this.subjectGenre = subjectGenre;
        this.subjectType = subjectType;
        this.subjectState = subjectState;
        this.subjectTitle = subjectTitle;
        this.subjectContent = subjectContent;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "MessageSubjectPO{" +
                "messageSubjectId=" + messageSubjectId +
                ", userId=" + userId +
                ", messageSource=" + messageSource +
                ", detailedId=" + detailedId +
                ", subjectGenre='" + subjectGenre + '\'' +
                ", subjectType=" + subjectType +
                ", subjectState=" + subjectState +
                ", subjectTitle='" + subjectTitle + '\'' +
                ", subjectContent='" + subjectContent + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", userType=" + userType +
                '}';
    }
}