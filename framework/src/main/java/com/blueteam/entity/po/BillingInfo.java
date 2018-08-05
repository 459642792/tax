package com.blueteam.entity.po;

import java.util.Date;

public class BillingInfo {

    /**
     * 银行卡类型
     */
    public static final int BILLING_INFO_TYPE_BANK = 1;
    /**
     * 支付宝类型
     */
    public static final int BILLING_INFO_TYPE_ALIPAY = 2;

    /**
     * 版本号
     */
    private static final long serialVersionUID = -8050587238346557828L;

    /**  */
    private Integer id;

    /**
     * 结算类型 1：银行卡结算，2支付宝结算
     */
    private Integer billingInfoType;

    /**
     * 商家主键id
     */
    private Integer vendorInfoId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 省份证
     */
    private String identityCard;

    /**
     * 支付宝号或银行卡号，根据类型判断
     */
    private String alipayCardBank;

    /**
     * 银行名称（type为1是必填）
     */
    private String bankName;

    /**
     * 开户城市
     */
    private String accountOpeningCity;

    /**
     * 开户支行
     */
    private String accountOpeningBranch;


    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    /**
     * 获取Id
     *
     * @return Id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * 设置Id
     *
     * @param Id Id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取结算类型 1：银行卡结算，2支付宝结算
     *
     * @return 结算类型 1：银行卡结算
     */
    public Integer getBillingInfoType() {
        return this.billingInfoType;
    }

    /**
     * 设置结算类型 1：银行卡结算，2支付宝结算
     *
     * @param BillingInfoType 结算类型 1：银行卡结算，2支付宝结算
     */
    public void setBillingInfoType(Integer billingInfoType) {
        this.billingInfoType = billingInfoType;
    }

    /**
     * 获取商家主键id
     *
     * @return 商家主键id
     */
    public Integer getVendorInfoId() {
        return this.vendorInfoId;
    }

    /**
     * 设置商家主键id
     *
     * @param VendorInfoId 商家主键id
     */
    public void setVendorInfoId(Integer vendorInfoId) {
        this.vendorInfoId = vendorInfoId;
    }

    /**
     * 获取姓名
     *
     * @return 姓名
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置姓名
     *
     * @param Name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取省份证
     *
     * @return 省份证
     */
    public String getIdentityCard() {
        return this.identityCard;
    }

    /**
     * 设置省份证
     *
     * @param IdentityCard 省份证
     */
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    /**
     * 获取支付宝号或银行卡号，根据类型判断
     *
     * @return 支付宝号或银行卡号
     */
    public String getAlipayCardBank() {
        return this.alipayCardBank;
    }

    /**
     * 设置支付宝号或银行卡号，根据类型判断
     *
     * @param AlipayCardBank 支付宝号或银行卡号，根据类型判断
     */
    public void setAlipayCardBank(String alipayCardBank) {
        this.alipayCardBank = alipayCardBank;
    }

    /**
     * 获取银行名称（type为1是必填）
     *
     * @return 银行名称（type为1是必填）
     */
    public String getBankName() {
        return this.bankName;
    }

    /**
     * 设置银行名称（type为1是必填）
     *
     * @param BankName 银行名称（type为1是必填）
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * 获取开户城市
     *
     * @return 开户城市
     */
    public String getAccountOpeningCity() {
        return this.accountOpeningCity;
    }

    /**
     * 设置开户城市
     *
     * @param AccountOpeningCity 开户城市
     */
    public void setAccountOpeningCity(String accountOpeningCity) {
        this.accountOpeningCity = accountOpeningCity;
    }

    /**
     * 获取开户支行
     *
     * @return 开户支行
     */
    public String getAccountOpeningBranch() {
        return this.accountOpeningBranch;
    }

    /**
     * 设置开户支行
     *
     * @param AccountOpeningBranch 开户支行
     */
    public void setAccountOpeningBranch(String accountOpeningBranch) {
        this.accountOpeningBranch = accountOpeningBranch;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }


}
