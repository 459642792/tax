package com.blueteam.entity.po;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by huangqijun on 18/1/13.
 * 首页6大活动信息表
 */
public class PromotionInfoPO {
    private Integer promotionInfoId;//活动信息ID
    private Integer promotionCatagoryId;//活动所属分类ID
    private Integer vendorId;//参与店铺ID
    private String vendorName;//参与店铺名称
    private Long goodsId;//参与店铺商品ID
    private String goodsName;//参与店铺商品名称
    private String cityCode;//店铺所属城市代码
    private String startTime;//活动开始时间
    private String endTime;//活动结束时间
    private Integer status;//状态 0-已过期 1-正常
    private Integer weight;//权重
    private Long visits;//活动商品访问次数
    private Long salesCount;//活动商品销售量
    private Integer updateStaffId;//操作员工ID
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    public Integer getPromotionInfoId() {
        return promotionInfoId;
    }

    public void setPromotionInfoId(Integer promotionInfoId) {
        this.promotionInfoId = promotionInfoId;
    }

    public Integer getPromotionCatagoryId() {
        return promotionCatagoryId;
    }

    public void setPromotionCatagoryId(Integer promotionCatagoryId) {
        this.promotionCatagoryId = promotionCatagoryId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    public Long getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Long salesCount) {
        this.salesCount = salesCount;
    }
}
