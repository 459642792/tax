package com.blueteam.entity.po;


import com.blueteam.base.util.StringUtil;

import java.math.BigDecimal;
import java.util.Date;

public class SettlementRecords {
    private Integer id;
    private Integer vendorInfoId;
    private BigDecimal amounts;
    private String createBy;
    private Date createDate;
    private Date updateDate;
    private String updateBy;
    private Date settlement_start;
    private Date settlement_end;

    /**
     * 银行卡号 计算
     */
    private String bankCard;

    /**
     * 结算类型
     * 银行卡还是支付宝
     */
    private Integer billingInfoType;

    /**
     * 获取结算类型字符串
     *
     * @return
     */
    public String getBillInfoTypeStr() {
        if (billingInfoType == null)
            return "";
        if (billingInfoType == BillingInfo.BILLING_INFO_TYPE_ALIPAY)
            return "支付宝";
        if (billingInfoType == BillingInfo.BILLING_INFO_TYPE_BANK)
            return "银行卡";
        return "";
    }

    public Integer getBillingInfoType() {
        return billingInfoType;
    }

    public void setBillingInfoType(Integer billingInfoType) {
        this.billingInfoType = billingInfoType;
    }

    public String getBankCard() {
        return bankCard;
    }

    public String getBankCardStr() {
        if (billingInfoType != null && billingInfoType == BillingInfo.BILLING_INFO_TYPE_BANK && !StringUtil.IsNullOrEmpty(bankCard))
            return "尾号" + bankCard.substring(bankCard.length() < 4 ? 0 : bankCard.length() - 4);
        return bankCard;
    }

    /**
     * 获取账号Str
     *
     * @return
     */
    public String getCardStr() {
        if (billingInfoType != null && billingInfoType == BillingInfo.BILLING_INFO_TYPE_BANK && !StringUtil.IsNullOrEmpty(bankCard)) {
            return "尾号" + bankCard.substring(bankCard.length() < 4 ? 0 : bankCard.length() - 4);
        } else if (billingInfoType != null && billingInfoType == BillingInfo.BILLING_INFO_TYPE_ALIPAY) {
            return bankCard;
        }

        return "";
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public Date getSettlement_start() {
        return settlement_start;
    }

    public void setSettlement_start(Date settlement_start) {
        this.settlement_start = settlement_start;
    }

    public Date getSettlement_end() {
        return settlement_end;
    }

    public void setSettlement_end(Date settlement_end) {
        this.settlement_end = settlement_end;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVendorInfoId() {
        return vendorInfoId;
    }

    public void setVendorInfoId(Integer vendorInfoId) {
        this.vendorInfoId = vendorInfoId;
    }

    public BigDecimal getAmounts() {
        return amounts;
    }

    public void setAmounts(BigDecimal amounts) {
        this.amounts = amounts;
    }


}
