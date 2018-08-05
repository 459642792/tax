/******************************************************************
 ** 类    名：VendorBrandInfoPO
 ** 描    述：商家品牌权限表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 14:04:45
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;

/**
 * 商家品牌权限表(TF_G_VENDOR_BRAND)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class VendorBrandInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8105607114388803831L;
    //初次插入默认数量
    public static final int DEFAULT_AMOUNT = 1;
    //品牌失效时数量置0
    public static final int INVALID_AMOUNT = 0;
    /**
     * 无效
     */
    public static final Integer AUTHORITY_TAG_INVALID = 0;
    /**
     * 有效
     */
    public static final Integer AUTHORITY_TAG_VALID = 1;

    /**
     * 商家id
     */
    private Integer vendorId;

    /**
     * 子品牌id
     */
    private Integer brandId;


    /**
     * 品牌上架商品数
     */
    private Integer brandGoodsAmount;

    /**
     * 权限标志：0-无效 1-有效
     */
    private Integer authorityTag;

    /**
     * 创建员工ID
     */
    private Integer createStaffId;

    /**
     * 最近一次更新的员工ID
     */
    private Integer updateStaffId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 插入标示  1代表上架  0 代表下架
     */
    private Integer goodsVendorStatus;

    /**
     * 是否是主品牌
     */
    private Integer isMajor;

    public Integer getGoodsVendorStatus() {
        return goodsVendorStatus;
    }

    public void setGoodsVendorStatus(Integer goodsVendorStatus) {
        this.goodsVendorStatus = goodsVendorStatus;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getBrandGoodsAmount() {
        return brandGoodsAmount;
    }

    public void setBrandGoodsAmount(Integer brandGoodsAmount) {
        this.brandGoodsAmount = brandGoodsAmount;
    }

    public Integer getAuthorityTag() {
        return authorityTag;
    }

    public void setAuthorityTag(Integer authorityTag) {
        this.authorityTag = authorityTag;
    }

    public Integer getCreateStaffId() {
        return createStaffId;
    }

    public void setCreateStaffId(Integer createStaffId) {
        this.createStaffId = createStaffId;
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

    public Integer getIsMajor() {
        return isMajor;
    }

    public void setIsMajor(Integer isMajor) {
        this.isMajor = isMajor;
    }
}