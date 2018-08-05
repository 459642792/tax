package com.blueteam.entity.po;

import java.util.Date;

/**
 * Created by huangqijun on 18/1/13.
 * 首页6大活动分类表
 */
public class PromotionCatagoryPO {
    private Integer promotionCatagoryId;//活动分类ID
    private String name;//活动分类名称
    private Integer model;//位置模式 0-小 1-大
    private String logo;//活动logo图
    private String banner;//首页banner图片
    private String summary;//活动简介
    private Integer updateStaffId;//操作管理员ID
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    //用于参数传递，不做保存
    private String updateStaffName;//操作管理员名称

    public Integer getPromotionCatagoryId() {
        return promotionCatagoryId;
    }

    public void setPromotionCatagoryId(Integer promotionCatagoryId) {
        this.promotionCatagoryId = promotionCatagoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getUpdateStaffId() {
        return updateStaffId;
    }

    public void setUpdateStaffId(Integer updateStaffId) {
        this.updateStaffId = updateStaffId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateStaffName() {
        return updateStaffName;
    }

    public void setUpdateStaffName(String updateStaffName) {
        this.updateStaffName = updateStaffName;
    }
}
