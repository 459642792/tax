package com.blueteam.entity.vo;

import com.blueteam.entity.po.VendorBankPO;

import java.io.Serializable;

public class VendorBankDetailVO implements Serializable{

    private String name;

    private String idCard;

    private VendorBankPO bankInfo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public VendorBankPO getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(VendorBankPO bankInfo) {
        this.bankInfo = bankInfo;
    }
}
