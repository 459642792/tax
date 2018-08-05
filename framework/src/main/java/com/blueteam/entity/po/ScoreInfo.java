package com.blueteam.entity.po;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Marx
 * <p>
 * JiFenInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class ScoreInfo {
    //标识ID
    private Integer Id;
    //商品标识
    private Integer ProductId;
    //评分分数
    private BigDecimal Score;
    //详情
    private String Detail;
    //创建时间
    private String CreateDate;
    //创建用户
    private String CreateBy;
    //更新时间
    private String UpdateDate;
    //更新用户
    private String UpdateBy;

    private Integer VendorId;

    private String Type;

    private Integer UserId;

    private String OrderNo;

    //扩展字段:用户名
    private String userName;
    //扩展字段:用户名
    private String nickName;
    //用户头像
    private String HeadImageUrl;
    private String openId;

    /**
     * 扩展字段
     * 商家名称
     */
    private String venderName;


    public String getScoreStr() {
        if (Score == null)
            return "";
        return Score.intValue() + "星";
    }

    public String getVenderName() {
        return venderName;
    }

    public void setVenderName(String venderName) {
        this.venderName = venderName;
    }

    public List<ScoreInfo> getVendorBack() {
        return vendorBack;
    }

    public void setVendorBack(List<ScoreInfo> vendorBack) {
        this.vendorBack = vendorBack;
    }

    //回复对象实体
    private List<ScoreInfo> vendorBack;

    public String getHeadImageUrl() {
        return HeadImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl) {
        HeadImageUrl = headImageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    private List<ImageInfo> lstImage;

    public List<ImageInfo> getLstImage() {
        return lstImage;
    }

    public void setLstImage(List<ImageInfo> lstImage) {
        this.lstImage = lstImage;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getId() {
        return Id;
    }

    public Integer getProductId() {
        return ProductId;
    }


    public String getDetail() {
        return Detail;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public Integer getVendorId() {
        return VendorId;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setProductId(Integer productId) {
        ProductId = productId;
    }

    public BigDecimal getScore() {
        return Score;
    }

    public void setScore(BigDecimal score) {
        Score = score;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public void setVendorId(Integer vendorId) {
        VendorId = vendorId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
