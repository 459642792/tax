package com.blueteam.entity.po;


/**
 * 发现评论列表对象
 *
 * @author libra
 */
public class SmallRoutineComment {

    /**
     * C端发现评论
     */
    public static final String DISCOVER_COMMENT_TYPE = "DISCOVER_COMMENT_TYPE";


    /**
     * 主键ID
     */
    private Integer Id;

    /**
     * 点赞数量
     */
    private Integer UpVote;

    /**
     * 用户ID
     */
    private Integer UserId;

    /**
     * 用户名称
     */
    private String UserName;

    /**
     * 用户头像
     */
    private String UserImage;

    /**
     * 创建时间
     */
    private String CreateDate;

    /**
     * 创建人
     */
    private String CreateBy;

    /**
     * 修改时间
     */
    private String UpdateDate;

    /**
     * 修改人
     */
    private String UpdateBy;

    /**
     * 评论内容
     */
    private String Content;

    /**
     * 类型
     */
    private String Type;

    /**
     * 拓展ID
     */
    private Integer ForeignKey;

    /**
     * 商家分类
     */
    private String VendorType;

    /**
     * 状态
     *
     * @return
     */

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getUpVote() {
        return UpVote;
    }

    public void setUpVote(Integer upVote) {
        UpVote = upVote;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    private String Status;

    private String State;

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getVendorType() {
        return VendorType;
    }

    public void setVendorType(String vendorType) {
        VendorType = vendorType;
    }

}
