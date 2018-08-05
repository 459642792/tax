package  com.blueteam.entity.po;

import java.util.Date;

/**
 * 消息模板(MESSAGE_TEMPLATE)
 * 
 * @author xiaojiang
 * @version 1.0.0 2018-01-22
 */
public class MessageTemplatePO implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8031614144949167830L;
    public enum templateType{
            NOTIFY(1,"通知"),
            REMIND(2,"提醒"),
            MESSAGE(3,"消息");
            public   int type;
            public  String description;
        templateType() {
        }
        templateType(int type, String description) {
            this.type = type;
            this.description = description;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
    /** 主键id */
    private Integer messageTemplateId;
    
    /** 消息分类 */
    private String templateGenre;
    
    /** 消息类型(1 通知 2 提醒 3消息) */
    private Integer templateType;
    
    /** 消息头 如 订单通知 */
    private String templateTitle;
    
    /** 消息参数 */
    private String templateArgument;
    
    /** 消息内容 必须包含template_argument参数 */
    private String templateSubject;
    
    /** 创建时间 */
    private Date createTime;
    
    /** 创建人 */
    private String createBy;
    
    /** 修改时间 */
    private Date updateTime;
    
    /** 修改人 */
    private String updateBy;
    
    /**
     * 获取主键id
     * 
     * @return 主键id
     */
    public Integer getMessageTemplateId() {
        return this.messageTemplateId;
    }
     
    /**
     * 设置主键id
     * 
     * @param messageTemplateId
     *          主键id
     */
    public void setMessageTemplateId(Integer messageTemplateId) {
        this.messageTemplateId = messageTemplateId;
    }
    
    /**
     * 获取消息分类
     * 
     * @return 消息分类
     */
    public String getTemplateGenre() {
        return this.templateGenre;
    }
     
    /**
     * 设置消息分类
     * 
     * @param templateGenre
     *          消息分类
     */
    public void setTemplateGenre(String templateGenre) {
        this.templateGenre = templateGenre;
    }
    
    /**
     * 获取消息类型(1 通知 2 提醒 3消息)
     * 
     * @return 消息类型(1 通知 2 提醒 3消息)
     */
    public Integer getTemplateType() {
        return this.templateType;
    }
     
    /**
     * 设置消息类型(1 通知 2 提醒 3消息)
     * 
     * @param templateType
     *          消息类型(1 通知 2 提醒 3消息)
     */
    public void setTemplateType(Integer templateType) {
        this.templateType = templateType;
    }
    
    /**
     * 获取消息头 如 订单通知
     * 
     * @return 消息头 如 订单通知
     */
    public String getTemplateTitle() {
        return this.templateTitle;
    }
     
    /**
     * 设置消息头 如 订单通知
     * 
     * @param templateTitle
     *          消息头 如 订单通知
     */
    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }
    
    /**
     * 获取消息参数
     * 
     * @return 消息参数
     */
    public String getTemplateArgument() {
        return this.templateArgument;
    }
     
    /**
     * 设置消息参数
     * 
     * @param templateArgument
     *          消息参数
     */
    public void setTemplateArgument(String templateArgument) {
        this.templateArgument = templateArgument;
    }
    
    /**
     * 获取消息内容 必须包含template_argument参数
     * 
     * @return 消息内容 必须包含template_argument参数
     */
    public String getTemplateSubject() {
        return this.templateSubject;
    }
     
    /**
     * 设置消息内容 必须包含template_argument参数
     * 
     * @param templateSubject
     *          消息内容 必须包含template_argument参数
     */
    public void setTemplateSubject(String templateSubject) {
        this.templateSubject = templateSubject;
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

    public MessageTemplatePO() {
    }

    public MessageTemplatePO(Integer messageTemplateId, String templateGenre, Integer templateType, String templateTitle, String templateArgument, String templateSubject, Date createTime, String createBy, Date updateTime, String updateBy) {
        this.messageTemplateId = messageTemplateId;
        this.templateGenre = templateGenre;
        this.templateType = templateType;
        this.templateTitle = templateTitle;
        this.templateArgument = templateArgument;
        this.templateSubject = templateSubject;
        this.createTime = createTime;
        this.createBy = createBy;
        this.updateTime = updateTime;
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        return "MessageTemplatePO{" +
                "messageTemplateId=" + messageTemplateId +
                ", templateGenre='" + templateGenre + '\'' +
                ", templateType=" + templateType +
                ", templateTitle='" + templateTitle + '\'' +
                ", templateArgument='" + templateArgument + '\'' +
                ", templateSubject='" + templateSubject + '\'' +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                '}';
    }
}