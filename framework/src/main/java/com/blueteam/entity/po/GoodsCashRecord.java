package com.blueteam.entity.po;
/******************************************************************
 ** 类    名：Goods_cash_recordEntity
 ** 描    述：
 ** 创 建 者：xiaojiang
 ** 创建时间：2017-04-19 15:02:50
 ******************************************************************/

import java.util.Date;

/**
 * (goods_cash_record)
 * 商家兑换记录表
 *
 * @author xiaojiang
 * @version 1.0.0 2017-04-19
 */
public class GoodsCashRecord implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -8967172787553619181L;
    /**
     * 未发货
     */
    public static final int STATUS_NOT_YET_SHIPMENTS = 0;
    /**
     * 已发货
     */
    public static final int STATUS_SHIPMENTS = 1;
    /**
     * 已退货
     */
    public static final int STATUS_RECEIPT = 8;
    /**
     * 已收货
     */
    public static final int STATUS_ERROR = 9;
    /**  */
    private Integer id;

    /**
     * 商品表id'
     */
    private Integer tradedGoodsId;

    /**
     * 订单号
     */
    private String orderNumber;

    /**
     * 商品名称(冗余)
     */
    private String goodsName;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 兑换价格
     */
    private Integer cashPrice;

    /**
     * 状态(0 未发货，1 已发货 8 已收货 9 已退货)
     */
    private Integer status;

    /**
     * 快递公司
     */
    private String expressCompany;

    /**
     * 快递单号
     */
    private String expressNumbers;

    /**
     * 发货时间
     */
    private Date expressDate;

    /**
     * 商家id
     */
    private Integer vendorinfoId;

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
     * 扩展，用户ID
     */
    private Integer userId;

    /**
     * 扩展，当前的剩余酒币
     */
    private Integer balance;

    /**
     * 扩展，商家名称
     */
    private String vendorName;

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

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
     * 获取商品表id'
     *
     * @return 商品表id'
     */
    public Integer getTradedGoodsId() {
        return this.tradedGoodsId;
    }

    /**
     * 设置商品表id'
     *
     * @param traded_goods_id 商品表id'
     */
    public void setTradedGoodsId(Integer tradedGoodsId) {
        this.tradedGoodsId = tradedGoodsId;
    }

    /**
     * 获取订单号
     *
     * @return 订单号
     */
    public String getOrderNumber() {
        return this.orderNumber;
    }

    /**
     * 设置订单号
     *
     * @param order_number 订单号
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 获取商品名称(冗余)
     *
     * @return 商品名称(冗余)
     */
    public String getGoodsName() {
        return this.goodsName;
    }

    /**
     * 设置商品名称(冗余)
     *
     * @param goods_name 商品名称(冗余)
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取数量
     *
     * @return 数量
     */
    public Integer getAmount() {
        return this.amount;
    }

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 获取兑换价格
     *
     * @return 兑换价格
     */
    public Integer getCashPrice() {
        return this.cashPrice;
    }

    /**
     * 设置兑换价格
     *
     * @param cash_price 兑换价格
     */
    public void setCashPrice(Integer cashPrice) {
        this.cashPrice = cashPrice;
    }

    /**
     * 获取状态(0 未发货，1 已发货 8 已收货 9 已退货)
     *
     * @return 状态(0 未发货
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置状态(0 未发货，1 已发货 8 已收货 9 已退货)
     *
     * @param status 状态(0 未发货，1 已发货 8 已收货 9 已退货)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取快递公司
     *
     * @return 快递公司
     */
    public String getExpressCompany() {
        return this.expressCompany;
    }

    /**
     * 设置快递公司
     *
     * @param express_company 快递公司
     */
    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    /**
     * 获取快递单号
     *
     * @return 快递单号
     */
    public String getExpressNumbers() {
        return this.expressNumbers;
    }

    /**
     * 设置快递单号
     *
     * @param express_numbers 快递单号
     */
    public void setExpressNumbers(String expressNumbers) {
        this.expressNumbers = expressNumbers;
    }


    /**
     * 获取商家id
     *
     * @return 商家id
     */
    public Integer getVendorinfoId() {
        return this.vendorinfoId;
    }

    /**
     * 设置商家id
     *
     * @param vendorinfo_id 商家id
     */
    public void setVendorinfoId(Integer vendorinfoId) {
        this.vendorinfoId = vendorinfoId;
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

    public Date getExpressDate() {
        return expressDate;
    }

    public void setExpressDate(Date expressDate) {
        this.expressDate = expressDate;
    }

}