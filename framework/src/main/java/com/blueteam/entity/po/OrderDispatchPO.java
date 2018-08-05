package com.blueteam.entity.po;

import java.util.Date;

/**
 * 订单配送信息表(TF_B_ORDER_DISPATCH)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class OrderDispatchPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 7183190882187827940L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 配送名称
     */
    private String dispatchName;

    /**
     * 配送类型：0-商家配型 1-平台配送 2-第三方配送
     */
    private Integer dispatchType;

    /**
     * 配送提供方
     */
    private String dispatchProvider;

    /**
     * 配送费用(单位-分)
     */
    private Long dispatchFee;

    /**
     * 配送详情
     */
    private String dispatchDetail;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 地址名称
     */
    private String addressName;

    /**
     * 地址详情
     */
    private String addressDesc;

    /**
     * 用户输入地址
     */
    private String inputAddress;

    /**
     * 结构化地址
     */
    private String structuredAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 行政区编码
     */
    private String adCode;

    /**
     * 街区编码
     */
    private String townCode;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新员工id
     */
    private Integer updateStaffId;

    /**
     * 获取订单id
     *
     * @return 订单id
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取配送名称
     *
     * @return 配送名称
     */
    public String getDispatchName() {
        return this.dispatchName;
    }

    /**
     * 设置配送名称
     *
     * @param dispatchName 配送名称
     */
    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    /**
     * 获取配送类型：0-商家配型 1-平台配送 2-第三方配送
     *
     * @return 配送类型
     */
    public Integer getDispatchType() {
        return this.dispatchType;
    }

    /**
     * 设置配送类型：0-商家配型 1-平台配送 2-第三方配送
     *
     * @param dispatchType 配送类型：0-商家配型 1-平台配送 2-第三方配送
     */
    public void setDispatchType(Integer dispatchType) {
        this.dispatchType = dispatchType;
    }

    /**
     * 获取配送提供方
     *
     * @return 配送提供方
     */
    public String getDispatchProvider() {
        return this.dispatchProvider;
    }

    /**
     * 设置配送提供方
     *
     * @param dispatchProvider 配送提供方
     */
    public void setDispatchProvider(String dispatchProvider) {
        this.dispatchProvider = dispatchProvider;
    }

    /**
     * 获取配送费用(单位-分)
     *
     * @return 配送费用(单位-分)
     */
    public Long getDispatchFee() {
        return this.dispatchFee;
    }

    /**
     * 设置配送费用(单位-分)
     *
     * @param dispatchFee 配送费用(单位-分)
     */
    public void setDispatchFee(Long dispatchFee) {
        this.dispatchFee = dispatchFee;
    }

    /**
     * 获取配送详情
     *
     * @return 配送详情
     */
    public String getDispatchDetail() {
        return this.dispatchDetail;
    }

    /**
     * 设置配送详情
     *
     * @param dispatchDetail 配送详情
     */
    public void setDispatchDetail(String dispatchDetail) {
        this.dispatchDetail = dispatchDetail;
    }

    /**
     * 获取联系人姓名
     *
     * @return 联系人姓名
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置联系人姓名
     *
     * @param contactName 联系人姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取联系人手机
     *
     * @return 联系人手机
     */
    public String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 设置联系人手机
     *
     * @param contactPhone 联系人手机
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取地址名称
     *
     * @return 地址名称
     */
    public String getAddressName() {
        return this.addressName;
    }

    /**
     * 设置地址名称
     *
     * @param addressName 地址名称
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * 获取地址详情
     *
     * @return 地址详情
     */
    public String getAddressDesc() {
        return this.addressDesc;
    }

    /**
     * 设置地址详情
     *
     * @param addressDesc 地址详情
     */
    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    /**
     * 获取用户输入地址
     *
     * @return 用户输入地址
     */
    public String getInputAddress() {
        return this.inputAddress;
    }

    /**
     * 设置用户输入地址
     *
     * @param inputAddress 用户输入地址
     */
    public void setInputAddress(String inputAddress) {
        this.inputAddress = inputAddress;
    }

    /**
     * 获取结构化地址
     *
     * @return 结构化地址
     */
    public String getStructuredAddress() {
        return this.structuredAddress;
    }

    /**
     * 设置结构化地址
     *
     * @param structuredAddress 结构化地址
     */
    public void setStructuredAddress(String structuredAddress) {
        this.structuredAddress = structuredAddress;
    }

    /**
     * 获取经度
     *
     * @return 经度
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return 纬度
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取城市编码
     *
     * @return 城市编码
     */
    public String getCityCode() {
        return this.cityCode;
    }

    /**
     * 设置城市编码
     *
     * @param cityCode 城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取行政区编码
     *
     * @return 行政区编码
     */
    public String getadCode() {
        return this.adCode;
    }

    /**
     * 设置行政区编码
     *
     * @param adCode 行政区编码
     */
    public void setadCode(String adCode) {
        this.adCode = adCode;
    }

    /**
     * 获取街区编码
     *
     * @return 街区编码
     */
    public String getTownCode() {
        return this.townCode;
    }

    /**
     * 设置街区编码
     *
     * @param townCode 街区编码
     */
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取更新员工id
     *
     * @return 更新员工id
     */
    public Integer getUpdateStaffId() {
        return this.updateStaffId;
    }

    /**
     * 设置更新员工id
     *
     * @param updateStaffId 更新员工id
     */
    public void setUpdateStaffId(Integer updateStaffId) {
        this.updateStaffId = updateStaffId;
    }
}