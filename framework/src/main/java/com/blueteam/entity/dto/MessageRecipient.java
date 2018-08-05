package com.blueteam.entity.dto;

/**
 * Created by libra on 2017/5/22.
 * <p>
 * 消息接收者实体
 */
public class MessageRecipient {
    /**
     * 接收用户ID
     */
    private int userId;
    /**
     * 接收商家ID
     */
    private int vendorId;
    /**
     * 接收运营商ID
     */
    private int carriersId;

    /**
     * 用户类型
     */
    private int userType;

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getCarriersId() {
        return carriersId;
    }

    public void setCarriersId(int carriersId) {
        this.carriersId = carriersId;
    }
}
