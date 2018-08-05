package com.blueteam.entity.vo;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.po.*;

import java.lang.reflect.Array;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

public class VendorOrderVO extends OrderPO{
    /** 商家名称*/
    private String vendorName;
    /** 商家电话*/
    private String vendorPhone;
    /** 用户地址*/
    private OrderDispatchPO orderDispatchPO;
    /** 订单评价*/
    private OrderComment orderComment;
    /** 申请退款信息*/
    private ApplyRefundRecordPO applyRefundRecordPO;
    /** 退款结果信息*/
    private RefundResultPO refundResultPO;
    /** 商品信息*/
    private List<OrderGoodsItemPO> orderGoodsItemPOS;
    /** 有效状态 是否可以删除 2代表可以取消订单 0代表删除订单 */
    private Integer showValidity;
    /** 订单状态 0  去支付 1 去退款 2 去评价 3 取消申请退款 */
    private Integer showState;
    /**
     * 是否有投诉信息
     */
    private Integer isHaveComplain;

    public Integer getIsHaveComplain() {
        return isHaveComplain==null?0:isHaveComplain;
    }

    public void setIsHaveComplain(Integer isHaveComplain) {
        this.isHaveComplain = isHaveComplain;
    }

    public OrderDispatchPO getOrderDispatchPO() {
        return orderDispatchPO;
    }

    public void setOrderDispatchPO(OrderDispatchPO orderDispatchPO) {
        this.orderDispatchPO = orderDispatchPO;
    }

    public List<OrderGoodsItemPO> getOrderGoodsItemPOS() {
        return orderGoodsItemPOS;
    }

    public void setOrderGoodsItemPOS(List<OrderGoodsItemPO> orderGoodsItemPOS) {
        this.orderGoodsItemPOS = orderGoodsItemPOS;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }


    public void setShowValidity(Integer showValidity) {
        this.showValidity = showValidity;
    }


    public OrderComment getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(OrderComment orderComment) {
        this.orderComment = orderComment;
    }

    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public ApplyRefundRecordPO getApplyRefundRecordPO() {
        return applyRefundRecordPO;
    }

    public void setApplyRefundRecordPO(ApplyRefundRecordPO applyRefundRecordPO) {
        this.applyRefundRecordPO = applyRefundRecordPO;
    }

    public RefundResultPO getRefundResultPO() {
        return refundResultPO;
    }

    public void setRefundResultPO(RefundResultPO refundResultPO) {
        this.refundResultPO = refundResultPO;
    }

}
