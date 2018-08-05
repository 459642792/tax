/******************************************************************
 ** 类    名：BrandMainInfoPO
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * (TD_G_BRAND_MAIN)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class BrandMainInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -4228345422822079690L;

    /**
     * 主品牌编码
     */
    private Integer mainBrandId;

    /**
     * 主品牌名称
     */
    private String mainBrandName;

    /**
     * 主品牌LOG图片
     */
    private String mainBrandPhoto;

    /**
     * 主品牌介绍
     */
    private String mainBrandDesc;

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
     * 获取主品牌编码
     *
     * @return 主品牌编码
     */
    public Integer getMainBrandId() {
        return this.mainBrandId;
    }

    /**
     * 设置主品牌编码
     *
     * @param mainBrandId 主品牌编码
     */
    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    /**
     * 获取主品牌名称
     *
     * @return 主品牌名称
     */
    public String getMainBrandName() {
        return this.mainBrandName;
    }

    /**
     * 设置主品牌名称
     *
     * @param mainBrandName 主品牌名称
     */
    public void setMainBrandName(String mainBrandName) {
        this.mainBrandName = mainBrandName;
    }

    /**
     * 获取主品牌LOG图片
     *
     * @return 主品牌LOG图片
     */
    public String getMainBrandPhoto() {
        return this.mainBrandPhoto;
    }

    /**
     * 设置主品牌LOG图片
     *
     * @param mainBrandPhoto 主品牌LOG图片
     */
    public void setMainBrandPhoto(String mainBrandPhoto) {
        this.mainBrandPhoto = mainBrandPhoto;
    }

    /**
     * 获取主品牌介绍
     *
     * @return 主品牌介绍
     */
    public String getMainBrandDesc() {
        return this.mainBrandDesc;
    }

    /**
     * 设置主品牌介绍
     *
     * @param mainBrandDesc 主品牌介绍
     */
    public void setMainBrandDesc(String mainBrandDesc) {
        this.mainBrandDesc = mainBrandDesc;
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