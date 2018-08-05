package com.blueteam.entity.po;

import java.util.Date;

/**
 * 支付记录明细表
 *
 * @author xiaojiang 2017年3月1日
 * @version 1.0
 * @since 1.0 2017年3月1日
 */
public class PayRecord {
    public Date updateDate;
    public String userName;
    public String discountAmount;
    public String title;
    public String discountAmounts;

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUserName() {
        return userName;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public String getTitle() {
        return title;
    }

    public String getDiscountAmounts() {
        return discountAmounts;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDiscountAmounts(String discountAmounts) {
        this.discountAmounts = discountAmounts;
    }


}
