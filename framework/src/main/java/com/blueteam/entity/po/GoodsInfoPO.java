/******************************************************************
 ** 类    名：GoodsInfoPO
 ** 描    述：商品表'
 /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 PARTITIONS 10 *
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;

/**
 * 商品表'
 * /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 * PARTITIONS 10 *(TF_G_GOODS)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class GoodsInfoPO implements java.io.Serializable {
    //商品状态-上架
    public static final int GOODS_STATE_LISTING = 1;
    //商品状态-下架
    public static final int GOODS_STATE_DELISTING = 0;
    //商品操作标志-可编辑
    public static final int OPERATE_TAG_EDITABLE = 1;
    //商品操作标志-不可编辑
    public static final int OPERATE_TAG_UNEDITABLE = 0;


    /**
     * 版本号
     */
    private static final long serialVersionUID = -5452596763568924224L;

    /**
     * 商品id：前八位为uuid,后两位为商品类型
     */
    private Long goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标签
     */
    private String goodsLabel;

    /**
     * 商品类型：关联TD_G_GOODS_TYPE表GOODS_TYPE_ID
     */
    private Integer goodsType;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 主品牌id
     */
    private Integer mainBrandId;

    /**
     * 子品牌id
     */
    private Integer brandId;

    /**
     * 建议零售价，单位：分
     */
    private Long suggestPrice;

    /**
     * 商品状态：0-下架 1-上架
     */
    private Integer goodsState;

    /**
     * 商品操作标志：0-不可编辑 1-可编辑
     */
    private Integer operateTag;

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

    public String getGoodsLabel() {
        return goodsLabel;
    }

    public void setGoodsLabel(String goodsLabel) {
        this.goodsLabel = goodsLabel;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getMainBrandId() {
        return mainBrandId;
    }

    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Long getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(Long suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public Integer getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(Integer goodsState) {
        this.goodsState = goodsState;
    }

    public Integer getOperateTag() {
        return operateTag;
    }

    public void setOperateTag(Integer operateTag) {
        this.operateTag = operateTag;
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
}