package com.blueteam.entity.vo;

/**
 * 用户地址显示
 *
 * @author xiaojiang
 * @create 2018-01-26  11:06
 */
public class OrderDispatchVO {
    /** 联系人 */
    private String contactName;
    /** 联系电话 */
    private String contactPhone;
    /** 联系地址*/
    private String addressDesc;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }
}
