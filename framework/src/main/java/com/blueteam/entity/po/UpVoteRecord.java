package com.blueteam.entity.po;


/**
 * 发现点赞记录
 *
 * @author Marx
 */
public class UpVoteRecord {

    /**
     * 主键ID
     */
    private Integer Id;

    /**
     * 点赞的用户名称
     */
    private String UserName;

    /**
     * 拓展ID(存放发现文章的ID)
     */
    private Integer ForeignKey;

    /**
     * 点赞用户的头像
     */
    private String UserImage;

    /**
     * 点赞用户的ID
     */
    private Integer UserId;

    /**
     * 创建时间
     */
    private String CreateDate;

    /**
     * 创建用户
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
     * 扩展，点赞的用户昵称
     */
    private String nickName;

    /**
     * 扩展，点赞的发现title
     */
    private String title;


    /**
     * 商家分类
     */
    private String VendorType;


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendorType() {
        return VendorType;
    }

    public void setVendorType(String vendorType) {
        VendorType = vendorType;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
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
}
