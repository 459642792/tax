package com.blueteam.entity.po;

import com.blueteam.base.util.DateUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by libra on 2017/5/15.
 * <p>
 * 消息实体
 */
public class Message extends BasePojo implements Serializable {
    /**
     * 消息类型编码
     */
    private String typeCode;

    /**
     * 数据资料KEY
     */
    private String dataKey;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 收件人
     */
    private int recipients;

    /**
     * 商家ID
     */
    private int vendorID;

    /**
     * 运营商ID
     */
    private int carriersID;

    /**
     * 发送时间
     */
    private Date sendingTime;

    /**
     * 消息来源
     * <p>
     * Constants.DataSource
     */
    private String dataSource;

    /**
     * 消息软件
     * <p>
     * Constants。SoftApp
     */
    private String softApp;

    /**
     * 消息平台
     * <p>
     * Constants.Platform
     */
    private String platform;

    /**
     * 推送服务版本
     */
    private String pushServerVersion;


    /**
     * 产品图片
     */
    private String productImg;
    /**
     * 商家ICON
     */
    private String vendorImage;

    /**
     * 交易金额
     */
    private double payDiscountAmount;

    /**
     * 交易时间
     */
    private Date payTime;

    /**
     * 交易平台
     */
    private String payWay;

    /**
     * 交易用户名称
     */
    private String payNickName;

    /**
     * 接收用户组
     * 比如：发送给所有管理员，则 用户id,商家ID和运营商ID都为0
     * 这个值为 8
     */
    private int receivingUserTypes;

    /**
     * 设备号 推送的时候用
     */
    private String device_tokens;

    /**
     * 关联的订单ID
     */
    private Integer orderID;

    /**
     * 关联订单号
     */
    private String orderNo;


    /**
     * 关联的发现ID
     */
    private Integer discoverId;

    /**
     * 结算金额
     */
    private double settlementAmounts;


    public double getSettlementAmounts() {
        return settlementAmounts;
    }

    public void setSettlementAmounts(double settlementAmounts) {
        this.settlementAmounts = settlementAmounts;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public Integer getDiscoverId() {
        return discoverId;
    }

    public void setDiscoverId(Integer discoverId) {
        this.discoverId = discoverId;
    }

    public String getDevice_tokens() {
        return device_tokens;
    }

    public void setDevice_tokens(String device_tokens) {
        this.device_tokens = device_tokens;
    }

    public String getSendingTimeStr() {
        if (this.getSendingTime() != null)
            return DateUtil.format(this.getSendingTime());
        return "";
    }

    public int getReceivingUserTypes() {
        return receivingUserTypes;
    }

    public void setReceivingUserTypes(int receivingUserTypes) {
        this.receivingUserTypes = receivingUserTypes;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getVendorImage() {
        return vendorImage;
    }

    public void setVendorImage(String vendorImage) {
        this.vendorImage = vendorImage;
    }

    public double getPayDiscountAmount() {
        return payDiscountAmount;
    }

    public void setPayDiscountAmount(double payDiscountAmount) {
        this.payDiscountAmount = payDiscountAmount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getPayNickName() {
        return payNickName;
    }

    public void setPayNickName(String payNickName) {
        this.payNickName = payNickName;
    }

    public int getVendorID() {
        return vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    public int getCarriersID() {
        return carriersID;
    }

    public void setCarriersID(int carriersID) {
        this.carriersID = carriersID;
    }

    public String getDataKey() {
        return dataKey;
    }

    public void setDataKey(String dataKey) {
        this.dataKey = dataKey;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRecipients() {
        return recipients;
    }

    public void setRecipients(int recipients) {
        this.recipients = recipients;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getSoftApp() {
        return softApp;
    }

    public void setSoftApp(String softApp) {
        this.softApp = softApp;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPushServerVersion() {
        return pushServerVersion;
    }

    public void setPushServerVersion(String pushServerVersion) {
        this.pushServerVersion = pushServerVersion;
    }
}
