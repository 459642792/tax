package com.blueteam.entity.po;

import java.util.Date;

/**
 * (traded_goods)
 * 平台 商品表
 *
 * @author xiaojiang
 * @version 1.0.0 2017-04-19
 */
public class TradedGoods implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -6318353084151842902L;
    /**
     * 上架商品
     */
    public static final int GOODSSTATUS_PUTAWAY = 1;
    /**
     * 下架商品
     */
    public static final int GOODSSTATUS_SOLD_OUT = 0;
    /**
     * 删除商品
     */
    public static final int GOODSSTATUS_DEL = 9;

    /**  */
    private Integer id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 商品价格（目前是已酒币形式，最小单位1）
     */
    private Integer goodsPrice;

    /**
     * 状态(0 下架，1上架，9删除)
     */
    private Integer goodsStatus;

    /**
     * 商品种类(1 平台，
     */
    private Integer goodsGenre;

    /**
     * 商品详情
     */
    private String goodsDetails;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改者
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateDate;

    /**
     * 获取id
     *
     * @return id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商品名称
     *
     * @return 商品名称
     */
    public String getGoodsName() {
        return this.goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goods_name 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取品牌名称
     *
     * @return 品牌名称
     */
    public String getBrandName() {
        return this.brandName;
    }

    /**
     * 设置品牌名称
     *
     * @param brand_name 品牌名称
     */
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    /**
     * 获取 商品价格（目前是已酒币形式，最小单位1）
     *
     * @return 商品价格（目前是已酒币形式
     */
    public Integer getGoodsPrice() {
        return this.goodsPrice;
    }

    /**
     * 设置 商品价格（目前是已酒币形式，最小单位1）
     *
     * @param goods_price 商品价格（目前是已酒币形式，最小单位1）
     */
    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 获取状态(0 下架，1上架，9删除)
     *
     * @return 状态(0 下架
     */
    public Integer getGoodsStatus() {
        return this.goodsStatus;
    }

    /**
     * 设置状态(0 下架，1上架，9删除)
     *
     * @param goods_status 状态(0 下架，1上架，9删除)
     */
    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    /**
     * 获取 商品种类(1 平台，
     *
     * @return 商品种类(1 平台
     */
    public Integer getGoodsGenre() {
        return this.goodsGenre;
    }

    /**
     * 设置 商品种类(1 平台，
     *
     * @param goods_genre 商品种类(1 平台，
     */
    public void setGoodsGenre(Integer goodsGenre) {
        this.goodsGenre = goodsGenre;
    }

    /**
     * 获取商品详情
     *
     * @return 商品详情
     */
    public String getGoodsDetails() {
        return this.goodsDetails;
    }

    /**
     * 设置商品详情
     *
     * @param goods_details 商品详情
     */
    public void setGoodsDetails(String goodsDetails) {
        this.goodsDetails = goodsDetails;
    }

    /**
     * 获取创建者
     *
     * @return 创建者
     */
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 设置创建者
     *
     * @param create_by 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 设置创建时间
     *
     * @param create_date 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改者
     *
     * @return 修改者
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 设置修改者
     *
     * @param update_by 修改者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取修改时间
     *
     * @return 修改时间
     */
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param update_date 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}