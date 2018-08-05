package com.blueteam.entity.vo;

import java.util.List;

/**
 * 后台订单管理 订单详情VO
 * Created by  NastyNas on 2018/1/10.
 */
public class AdminOrderDetailVO {
    //订单详情页展示人工退款
    public static final int SHOW_MANUAL_REFUND = 1;
    //订单详情页不展示人工退款
    public static final int NO_SHOW_MANUAL_REFUND = 0;

    //订单id
    String orderId;
    //订单编号
    String orderNo;
    //订单业务状态编码
    Integer orderBusinessState;
    //订单业务状态名
    String orderBusinessStateName;
    //交易地区
    String tradeArea;
    //订单来源
    String orderChannelName;
    //订单金额
    String payPrice;
    //收货方式：1-配送 2-自提
    Integer deliveryType;
    //配送/自提时间
    String deliveryTime;
    //配送/自提地址
    String deliveryAddress;
    //用户账号
    Long userId;
    //联系方式
    String userPhone;
    //微信昵称
    String userNickName;
    //酒行账号
    String shopId;
    //酒行名称
    String shopName;
    //联系方式
    String shopPhone;
    //商品信息
    List<AdminOrderDetailGoodsVO> goodsVOList;
    //商品合计费用
    String totalGoodsFee;
    //优惠券
    String couponInfo;
    //创建时间
    String createTime;
    //支付时间
    String payTime;
    //接单时间
    String promiseTime;
    //收货时间
    String receiveTime;
    //评价时间
    String commentTime;
    //取消时间
    String cancelTime;
    //申请退款时间
    String applyDrawbackTime;
    //退款理由
    String drawbackDesc;
    //退款理由备注
    String drawbackRemark;

    //退款类型 1-系统退款 2-人工退款
    Integer drawbackType;
    //退款渠道 1-微信 2-支付宝 3-银行卡转账
    Integer drawbackChannel;
    //退款接受号
    String drawbackReceiveId;
    //退款金额
    String drawbackFee;

    //退款时间
    String drawbackTime;
    //留言批注
    String remark;
    //是否展示人工退款操作 0-不展示 1-展示
    Integer showManualRefund;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public String getTradeArea() {
        return tradeArea;
    }

    public void setTradeArea(String tradeArea) {
        this.tradeArea = tradeArea;
    }

    public String getOrderChannelName() {
        return orderChannelName;
    }

    public void setOrderChannelName(String orderChannelName) {
        this.orderChannelName = orderChannelName;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public Integer getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public List<AdminOrderDetailGoodsVO> getGoodsVOList() {
        return goodsVOList;
    }

    public void setGoodsVOList(List<AdminOrderDetailGoodsVO> goodsVOList) {
        this.goodsVOList = goodsVOList;
    }

    public String getTotalGoodsFee() {
        return totalGoodsFee;
    }

    public void setTotalGoodsFee(String totalGoodsFee) {
        this.totalGoodsFee = totalGoodsFee;
    }

    public String getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(String couponInfo) {
        this.couponInfo = couponInfo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPromiseTime() {
        return promiseTime;
    }

    public void setPromiseTime(String promiseTime) {
        this.promiseTime = promiseTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getApplyDrawbackTime() {
        return applyDrawbackTime;
    }

    public void setApplyDrawbackTime(String applyDrawbackTime) {
        this.applyDrawbackTime = applyDrawbackTime;
    }

    public String getDrawbackTime() {
        return drawbackTime;
    }

    public void setDrawbackTime(String drawbackTime) {
        this.drawbackTime = drawbackTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOrderBusinessStateName() {
        return orderBusinessStateName;
    }

    public void setOrderBusinessStateName(String orderBusinessStateName) {
        this.orderBusinessStateName = orderBusinessStateName;
    }

    public String getDrawbackDesc() {
        return drawbackDesc;
    }

    public void setDrawbackDesc(String drawbackDesc) {
        this.drawbackDesc = drawbackDesc;
    }

    public String getDrawbackRemark() {
        return drawbackRemark;
    }

    public void setDrawbackRemark(String drawbackRemark) {
        this.drawbackRemark = drawbackRemark;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Integer getShowManualRefund() {
        return showManualRefund;
    }

    public void setShowManualRefund(Integer showManualRefund) {
        this.showManualRefund = showManualRefund;
    }

    public Integer getDrawbackType() {
        return drawbackType;
    }

    public void setDrawbackType(Integer drawbackType) {
        this.drawbackType = drawbackType;
    }

    public Integer getOrderBusinessState() {
        return orderBusinessState;
    }

    public void setOrderBusinessState(Integer orderBusinessState) {
        this.orderBusinessState = orderBusinessState;
    }

    public Integer getDrawbackChannel() {
        return drawbackChannel;
    }

    public void setDrawbackChannel(Integer drawbackChannel) {
        this.drawbackChannel = drawbackChannel;
    }

    public String getDrawbackReceiveId() {
        return drawbackReceiveId;
    }

    public void setDrawbackReceiveId(String drawbackReceiveId) {
        this.drawbackReceiveId = drawbackReceiveId;
    }

    public String getDrawbackFee() {
        return drawbackFee;
    }

    public void setDrawbackFee(String drawbackFee) {
        this.drawbackFee = drawbackFee;
    }
}
