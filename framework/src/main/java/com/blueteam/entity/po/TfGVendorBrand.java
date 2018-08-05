/******************************************************************
 ** 类    名：TfGVendorBrand
 ** 描    述：商家品牌权限表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商家品牌权限表(TF_G_VENDOR_BRAND)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
@Entity
@Table(name = "TF_G_VENDOR_BRAND")
public class TfGVendorBrand implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -6257595291994837829L;

    /**
     * 商家id
     */
    @Column(name = "VENDOR_ID", nullable = true, length = 10)
    private Integer vendorId;

    /**
     * 子品牌id
     */
    @Column(name = "BRAND_ID", nullable = true, length = 10)
    private Integer brandId;

    /**
     * 权限标志：0-无效 1-有效
     */
    @Column(name = "AUTHORITY_TAG", nullable = true, length = 10)
    private Integer authorityTag;

    /**
     * 创建员工ID
     */
    @Column(name = "CREATE_STAFF_ID", nullable = true, length = 10)
    private Integer createStaffId;

    /**
     * 最近一次更新的员工ID
     */
    @Column(name = "UPDATE_STAFF_ID", nullable = true, length = 10)
    private Integer updateStaffId;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME", nullable = true)
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME", nullable = true)
    private Date updateTime;

    /**
     * 获取商家id
     *
     * @return 商家id
     */
    public Integer getVendorId() {
        return this.vendorId;
    }

    /**
     * 设置商家id
     *
     * @param vendorId 商家id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 获取子品牌id
     *
     * @return 子品牌id
     */
    public Integer getBrandId() {
        return this.brandId;
    }

    /**
     * 设置子品牌id
     *
     * @param brandId 子品牌id
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    /**
     * 获取权限标志：0-无效 1-有效
     *
     * @return 权限标志
     */
    public Integer getAuthorityTag() {
        return this.authorityTag;
    }

    /**
     * 设置权限标志：0-无效 1-有效
     *
     * @param authorityTag 权限标志：0-无效 1-有效
     */
    public void setAuthorityTag(Integer authorityTag) {
        this.authorityTag = authorityTag;
    }

    /**
     * 获取创建员工ID
     *
     * @return 创建员工ID
     */
    public Integer getCreateStaffId() {
        return this.createStaffId;
    }

    /**
     * 设置创建员工ID
     *
     * @param createStaffId 创建员工ID
     */
    public void setCreateStaffId(Integer createStaffId) {
        this.createStaffId = createStaffId;
    }

    /**
     * 获取最近一次更新的员工ID
     *
     * @return 最近一次更新的员工ID
     */
    public Integer getUpdateStaffId() {
        return this.updateStaffId;
    }

    /**
     * 设置最近一次更新的员工ID
     *
     * @param updateStaffId 最近一次更新的员工ID
     */
    public void setUpdateStaffId(Integer updateStaffId) {
        this.updateStaffId = updateStaffId;
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
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}