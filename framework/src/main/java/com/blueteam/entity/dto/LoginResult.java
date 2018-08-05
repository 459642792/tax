package com.blueteam.entity.dto;


import com.blueteam.entity.po.UserInfo;

/**
 * 登录结果
 *
 * @author libra
 */
public class LoginResult {
    /**
     * 用户ID
     */
    private int userId;

    /**
     * 账号
     */
    private String account;

    /**
     * 登录成功的Token
     */
    private String token;

    /**
     * 当前登录用户
     */
    private UserInfo user;

    /**
     * 商家id
     */
    private Integer vendorInfoId;

    /**
     * 是否存在商家信息
     */
    private boolean hasVendorInfo;


    private Integer authenticationStatus;

    public boolean isHasVendorInfo() {
        return hasVendorInfo;
    }

    public void setHasVendorInfo(boolean hasVendorInfo) {
        this.hasVendorInfo = hasVendorInfo;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getVendorInfoId() {
        return vendorInfoId;
    }

    public void setVendorInfoId(Integer vendorInfoId) {
        this.vendorInfoId = vendorInfoId;
    }

    public Integer getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(Integer authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
