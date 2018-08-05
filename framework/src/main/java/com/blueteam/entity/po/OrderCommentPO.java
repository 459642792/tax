package com.blueteam.entity.po;

import com.blueteam.base.constant.CommentEnum;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.StringUtil;

import java.util.Date;

public class OrderCommentPO {
    private Long id;

    private Date createdTime;

    private Date updateTime;

    private Integer userId;

    private Long orderId;

    private Integer score;

    private String commentContent;

    private Integer deletedFlag;

    private String commentPicture;

    private Integer vendorId;

    private String userName;

    private String goodsName;

    private String headImage;

    private String orderNo;

    /**
     * 是1否0能够删除
     */
    private Integer ShowDelete;

    public Integer getShowDelete() {
        return ShowDelete==null?0:ShowDelete;
    }

    public void setShowDelete(Integer showDelete) {
        ShowDelete = showDelete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public Integer getDeletedFlag() {
        return deletedFlag;
    }

    public void setDeletedFlag(Integer deletedFlag) {
        this.deletedFlag = deletedFlag;
    }

    public String getCommentPicture() {
        return commentPicture;
    }

    public void setCommentPicture(String commentPicture) {
        this.commentPicture = commentPicture == null ? null : commentPicture.trim();
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRealComment(){
        if (this.getScore()!=null&&this.getScore()>0) {
            return CommentEnum.getDesc(this.score);
        }
        return null;
    }

    public String getShowDate(){
        return DateUtil.formatDate(this.getCreatedTime());
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}