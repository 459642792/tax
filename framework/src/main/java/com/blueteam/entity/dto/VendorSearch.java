package com.blueteam.entity.dto;

/**
 * Created by clam on 2017/4/10.
 */
public class VendorSearch {
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 入驻开始时间
     */
    private String rz_begin;

    /**
     * 入驻结束时间
     */
    private String rz_end;

    /**
     * 认证状态
     */
    private int authenticationStatus;

    /**
     * 品牌
     */
    private String pinpai;

    private int pageIndex;

    private int pageSize;

    /**
     * 排序字段
     */
    private String order;

    /**
     * 排序方式
     */
    private String orderType;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRz_begin() {
        return rz_begin;
    }

    public void setRz_begin(String rz_begin) {
        this.rz_begin = rz_begin;
    }

    public String getRz_end() {
        return rz_end;
    }

    public void setRz_end(String rz_end) {
        this.rz_end = rz_end;
    }

    public int getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(int authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    public String getPinpai() {
        return pinpai;
    }

    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
