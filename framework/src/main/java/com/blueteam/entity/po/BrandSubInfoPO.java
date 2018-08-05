/******************************************************************
 ** 类    名：BrandSubInfoPO
 ** 描    述：子品牌信息表
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;

/**
 * 子品牌信息表(TD_G_BRAND_SUB)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class BrandSubInfoPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 7353205823692675952L;
    //品牌状态1-有效
    public static final Integer STATE_TAG_VALID = 1;
    //品牌状态0-无效
    public static final Integer STATE_TAG_INVALID = 0;

    /**
     * 子品牌编码
     */
    private Integer brandId;

    /**
     * 主品牌编码
     */
    private Integer mainBrandId;

    /**  */
    private String brandName;

    /**
     * 品牌状态：0-无效 1-有效
     */
    private Integer stateTag;

    /**
     * 品牌商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     */
    private Integer brandGoodsType;

    /**
     * 品牌照片
     */
    private String brandPhoto;

    /**
     * 品牌描述
     */
    private String brandDesc;

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
     * 获取子品牌编码
     *
     * @return 子品牌编码
     */
    public Integer getBrandId() {
        return this.brandId;
    }

    /**
     * 设置子品牌编码
     *
     * @param brandId 子品牌编码
     */
    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

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
     * 获取
     *
     * @return
     */
    public String getBrandName() {
        return this.brandName;
    }

    /**
     * 设置
     *
     * @param brandName
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取品牌状态：0-无效 1-有效
     *
     * @return 品牌状态
     */
    public Integer getStateTag() {
        return this.stateTag;
    }

    /**
     * 设置品牌状态：0-无效 1-有效
     *
     * @param stateTag 品牌状态：0-无效 1-有效
     */
    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    /**
     * 获取品牌商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     *
     * @return 品牌商品类型ID
     */
    public Integer getBrandGoodsType() {
        return this.brandGoodsType;
    }

    /**
     * 设置品牌商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     *
     * @param brandGoodsType 品牌商品类型ID：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     */
    public void setBrandGoodsType(Integer brandGoodsType) {
        this.brandGoodsType = brandGoodsType;
    }

    /**
     * 获取品牌照片
     *
     * @return 品牌照片
     */
    public String getBrandPhoto() {
        return this.brandPhoto;
    }

    /**
     * 设置品牌照片
     *
     * @param brandPhoto 品牌照片
     */
    public void setBrandPhoto(String brandPhoto) {
        this.brandPhoto = brandPhoto;
    }

    /**
     * 获取品牌描述
     *
     * @return 品牌描述
     */
    public String getBrandDesc() {
        return this.brandDesc;
    }

    /**
     * 设置品牌描述
     *
     * @param brandDesc 品牌描述
     */
    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
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

    @Override
    public String toString() {
        return "BrandSubInfoPO{" +
                "brandId=" + brandId +
                ", mainBrandId=" + mainBrandId +
                ", brandName='" + brandName + '\'' +
                ", stateTag=" + stateTag +
                ", brandGoodsType=" + brandGoodsType +
                ", brandPhoto='" + brandPhoto + '\'' +
                ", brandDesc='" + brandDesc + '\'' +
                ", createStaffId=" + createStaffId +
                ", updateStaffId=" + updateStaffId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}