package com.blueteam.entity.dto;

import com.blueteam.entity.po.CouponInfo;

import java.util.List;

/**
 * Created by huangqijun on 18/1/9.
 */
public class VendorBrandSearchDTO {
    private Integer vendorId;//商户ID
    private String vendorName;//商户名称
    private String vendorLogo;//店铺logo
    private String auditStatus;//审核状态 Y/N
    private String distance;//店铺距离
    private String vendorService;//店铺服务
    private String recommendStatus;//是否推荐 Y/N
    private List<CouponInfo> couponList;//优惠劵列表
    private List<String> ppList;//主营品牌列表

    //查询参数，不用做传输
    private String longitude;//经度
    private String latitude;//纬度

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorLogo() {
        return vendorLogo;
    }

    public void setVendorLogo(String vendorLogo) {
        this.vendorLogo = vendorLogo;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getVendorService() {
        return vendorService;
    }

    public void setVendorService(String vendorService) {
        this.vendorService = vendorService;
    }

    public String getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(String recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public List<CouponInfo> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponInfo> couponList) {
        this.couponList = couponList;
    }

    public List<String> getPpList() {
        return ppList;
    }

    public void setPpList(List<String> ppList) {
        this.ppList = ppList;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
