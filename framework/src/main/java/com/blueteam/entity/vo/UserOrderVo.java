package com.blueteam.entity.vo;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.entity.bo.OrderStateBO;
import com.blueteam.entity.po.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单用户显示类
 * @author xiaojiang
 * @create 2018-01-26  11:04
 */
public class UserOrderVo extends OrderPO {
    /** 商家名称*/
    private String vendorName;
    /** 商家电话*/
    private String vendorPhone;
    /** 商家地址*/
    private String vendorAddress;
    /** 有效状态 是否可以删除 2代表可以取消订单 0代表删除订单 */
    private Integer showValidity;
    /** 订单状态 0  去支付 1 去退款 2 去评价 3 取消申请退款 */
    private Integer showState;
    /**
     * 是否有投诉信息
     */
    private Integer isHaveComplain;
    /** 用户地址*/
    private OrderDispatchVO orderDispatch;
    /** 订单评价*/
    private OrderComment orderComment;
    /** 申请退款信息*/
    private ApplyRefundRecordPO applyRefundRecordPO;
    /** 退款结果信息*/
    private RefundResultPO refundResultPO;
    /** 商品信息*/
    private List<OrderGoodsItemVO> orderGoodsItem;

    public UserOrderVo() {
    }

    public UserOrderVo(String vendorName, String vendorPhone, String vendorAddress, Integer showValidity, Integer showState, Integer isHaveComplain, OrderDispatchVO orderDispatch, OrderComment orderComment, ApplyRefundRecordPO applyRefundRecordPO, RefundResultPO refundResultPO, List<OrderGoodsItemVO> orderGoodsItem) {
        this.vendorName = vendorName;
        this.vendorPhone = vendorPhone;
        this.vendorAddress = vendorAddress;
        this.showValidity = showValidity;
        this.showState = showState;
        this.isHaveComplain = isHaveComplain;
        this.orderDispatch = orderDispatch;
        this.orderComment = orderComment;
        this.applyRefundRecordPO = applyRefundRecordPO;
        this.refundResultPO = refundResultPO;
        this.orderGoodsItem = orderGoodsItem;
    }

    public Integer getIsHaveComplain() {
        return isHaveComplain;
    }

    public void setIsHaveComplain(Integer isHaveComplain) {
        this.isHaveComplain = isHaveComplain;
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



    public void setShowState(Integer showState) {
        this.showState = showState;
    }

    public OrderDispatchVO getOrderDispatch() {
        return orderDispatch;
    }

    public void setOrderDispatch(OrderDispatchVO orderDispatch) {
        this.orderDispatch = orderDispatch;
    }

    public OrderComment getOrderComment() {
        return orderComment;
    }

    public void setOrderComment(OrderComment orderComment) {
        this.orderComment = orderComment;
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

    public List<OrderGoodsItemVO> getOrderGoodsItem() {
        return orderGoodsItem;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public void setOrderGoodsItem(List<OrderGoodsItemVO> orderGoodsItem) {
        this.orderGoodsItem = orderGoodsItem;
    }
    public Integer getShowValidity() {
        OrderStateBO orderStateBO = new OrderStateBO();
        orderStateBO.setCommentState(this.getCommentState());
        orderStateBO.setCompleteState(this.getCompleteState());
        orderStateBO.setOrderState(this.getOrderState());
        orderStateBO.setRefundState(this.getRefundState());
        return  OrderStateHelp.transOrderValidityState(orderStateBO).getState().equals(OrderConstant.OrderValidityStateEnum.OTHER.getState()) ? null : OrderStateHelp.transOrderValidityState(orderStateBO).getState();
    }

    public Integer getShowState() {
        OrderStateBO orderStateBO = new OrderStateBO();
        orderStateBO.setCommentState(this.getCommentState());
        orderStateBO.setCompleteState(this.getCompleteState());
        orderStateBO.setOrderState(this.getOrderState());
        orderStateBO.setRefundState(this.getRefundState());
        return  OrderStateHelp.transBusinessOrderState(orderStateBO).getState().equals(OrderConstant.OrderOperateStateEnum.OTHER.getState()) ? null : OrderStateHelp.transBusinessOrderState(orderStateBO).getState();
    }




    /*** 时间节点*/
    public List<OrderNodeTime> getOrderNodeTime(){
        List<OrderNodeTime> list = new ArrayList<>();
        if (RStr.isNotEmpty(super.getCreateTime())){
            OrderNodeTime orderNodeTime = new OrderNodeTime();
            orderNodeTime.setNodeName("创建时间");
            orderNodeTime.setNodeTime(super.getCreateTime());
            list.add(orderNodeTime);
        }
        if (RStr.isNotEmpty(super.getPayTime())){
            OrderNodeTime orderNodeTime = new OrderNodeTime();
            orderNodeTime.setNodeName("支付时间");
            orderNodeTime.setNodeTime(super.getPayTime());
            list.add(orderNodeTime);
        }
        if (RStr.isNotEmpty(super.getPromiseTime())){
            OrderNodeTime orderNodeTime = new OrderNodeTime();
            orderNodeTime.setNodeName("接单时间");
            orderNodeTime.setNodeTime(super.getPromiseTime());
            list.add(orderNodeTime);
        }
        if (RStr.isNotEmpty(this.applyRefundRecordPO)){
            if (RStr.isNotEmpty(this.applyRefundRecordPO.getCreateTime()) && RStr.isNotEmpty(this.applyRefundRecordPO.getProcessStatus())){
                OrderNodeTime orderNodeTime = new OrderNodeTime();
                orderNodeTime.setNodeName("申请退款时间");
                orderNodeTime.setNodeTime(this.applyRefundRecordPO.getCreateTime());
                list.add(orderNodeTime);
            }
            if (RStr.isNotEmpty(this.applyRefundRecordPO.getProcessTime()) && this.applyRefundRecordPO.getProcessStatus().equals(ApplyRefundRecordPO.PROCESS_STATUS_CANCELED)){
                OrderNodeTime orderNodeTime = new OrderNodeTime();
                orderNodeTime.setNodeName("申请取消退款时间");
                orderNodeTime.setNodeTime(this.applyRefundRecordPO.getProcessTime());
                list.add(orderNodeTime);
            }
        }
        if (RStr.isNotEmpty(this.refundResultPO)){
            if (RStr.isNotEmpty(this.refundResultPO.getCreateTime()) && RStr.isNotEmpty(this.refundResultPO.getResultState())){
                OrderNodeTime orderNodeTime = new OrderNodeTime();
                if (this.refundResultPO.getResultState().equals(RefundResultPO.RESULT_STATE_SUCCESS)){
                    orderNodeTime.setNodeName("申请退款成功时间");
                }else{
                    orderNodeTime.setNodeName("申请退款失败时间");
                }
                orderNodeTime.setNodeTime(this.refundResultPO.getCreateTime());
                list.add(orderNodeTime);

            }
        }
        if (RStr.isNotEmpty(super.getCompleteTime())){
            if (OrderConstant.CompleteStateEnum.FINISHED.getState().equals(super.getCompleteState())){
                OrderNodeTime orderNodeTime = new OrderNodeTime();
                orderNodeTime.setNodeName("完成时间");
                orderNodeTime.setNodeTime(super.getCompleteTime());
                list.add(orderNodeTime);
            }
            if (OrderConstant.CompleteStateEnum.CANCELED.getState().equals(super.getCompleteState())){
                OrderNodeTime orderNodeTime = new OrderNodeTime();
                orderNodeTime.setNodeName("取消时间");
                orderNodeTime.setNodeTime(super.getCompleteTime());
                list.add(orderNodeTime);
            }
        }
        if (RStr.isNotEmpty(super.getCommentTime())){
            OrderNodeTime orderNodeTime = new OrderNodeTime();
            orderNodeTime.setNodeName("评价时间");
            orderNodeTime.setNodeTime(super.getCommentTime());
            list.add(orderNodeTime);
        }
        /*** 选择排序*/
        for (int i = 0; i < list.size(); i++) {
            int n = i;
            for (int j=i+1;j<list.size();j++) {
                if (list.get(j).getNodeTime().getTime() < list.get(n).getNodeTime().getTime()){
                    n = j;
                }
            }
            if (n != i){
                OrderNodeTime temp = list.get(i);
                list.set(i, list.get(n));
                list.set(n,temp);
            }
        }
        return list;
    }

}
