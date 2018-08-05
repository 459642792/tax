/******************************************************************
 ** 类    名：GoodsVendorInfoPO
 ** 描    述：商家商品信息表'
 /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 PARTITIONS 10 *
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-10-18 17:49:12
 ******************************************************************/

package com.blueteam.entity.po;

import java.util.Date;

/**
 * 商家商品信息表'
 * /*!50100 PARTITION BY HASH (MOD(GOODS_ID, 10))
 * PARTITIONS 10 *(TF_G_GOODS_VENDOR)
 *
 * @author xiaojiang
 * @version 1.0.0 2017-10-18
 */
public class GoodsVendorInfoPO implements java.io.Serializable {

    //商家商品状态-上架
    public static final int VENDOR_GOODS__LISTING = 1;
    //商家商品状态-下架
    public static final int VENDOR_GOODS_DELISTING = 0;
    /**
     * 版本号
     */
    private static final long serialVersionUID = 2977867485836227870L;
    /**
     * 上架
     */
    public static final int VENDOR_GOODS_STATE_UP = 1;
    /**
     * 下架
     */
    public static final int VENDOR_GOODS_STATE_DOWN = 0;
    /**
     * 删除
     */
    public static final int VENDOR_GOODS_STATE__DELETE = 2;
    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商家id
     */
    private Integer vendorId;

    /**
     * 商家销售价格
     */
    private Integer salePrice;

    /**
     * 商家商品状态：0-下架 1-下架
     */
    private Integer vendorGoodsState;

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
     * 获取商品id
     *
     * @return 商品id
     */
    public Long getGoodsId() {
        return this.goodsId;
    }

    /**
     * 设置商品id
     *
     * @param goodsId 商品id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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
     * 获取商家销售价格
     *
     * @return 商家销售价格
     */
    public Integer getSalePrice() {
        return this.salePrice;
    }

    /**
     * 设置商家销售价格
     *
     * @param salePrice 商家销售价格
     */
    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getVendorGoodsState() {
        return vendorGoodsState;
    }

    public void setVendorGoodsState(Integer vendorGoodsState) {
        this.vendorGoodsState = vendorGoodsState;
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