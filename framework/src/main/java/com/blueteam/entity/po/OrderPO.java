package com.blueteam.entity.po;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.help.order.OrderStateHelp;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.DateUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.bo.OrderStateBO;

import java.util.Date;

/**
 * 订单表(TF_B_ORDER)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class OrderPO implements java.io.Serializable {
    /**  订单状态 无效的*/
    public static final int VALIDITY_STATE_INVALID = 0;
    /**  订单状态 有效的*/
    public static final int VALIDITY_STATE_VALID = 1;
    /**  订单状态 普通订单*/
    public static final int ORDER_SOURCE_ORDINARY = 1;
    /**  订单来源 面对面付款*/
    public static final int ORDER_SOURCE_FACE = 2;
    /**  订单 自提*/
    public static final int  DELIVER_TYPE_SINCE = 1;
    /**
     * 版本号
     */
    private static final long serialVersionUID = -9139587622429258372L;

    /**
     * 订单id 唯一标识
     */
    private Long orderId;

    public String getRealOrderId(){
        if (RStr.isNotEmpty(orderId)){
            return orderId.toString();
        }
        return null;
    }

    /**
     * 订单编号，用于平台对用户展示
     */
    private String orderNo;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商家id
     */
    private Integer vendorId;

    /**
     * 订单状态，关联字典值表tf_d_state
     */
    private Integer orderState;

    /**
     * 订单完成状态 0-未完成  1-已完成  2-已取消
     */
    private Integer completeState;

    /**
     * 退款状态 关联字典值表 tf_d_state
     */
    private Integer refundState;

    /**
     * 评价状态  0-未评价  1-已评价
     */
    private Integer commentState;

    /**
     * 有效状态  0-无效  1-有效
     */
    private Integer validityState;
    /**
     * 订单来源 1代表普通订单  2代表面对面付款
     */
    private Integer orderSource;
    /**
     * 下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     */
    private Integer orderChannel;

    /**
     * 支付渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     */
    private Integer payChannel;

    /**
     * 支付类型id  关联支付类型表
     */
    private Integer payTypeId;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 支付方式id  关联支付方式表
     */
    private Integer payWayId;

    /**
     * 支付类型名称
     */
    private String payWayName;

    /**
     * 订单原价(单位-分)
     */
    private Long originalPrice;

    /**
     * 支付金额(单位-分)
     */
    private Long payPrice;

    /**
     * 优惠金额(单位-分)
     */
    private Long couponAmount;

    /**
     * 用户留言
     */
    private String remark;

    /**
     * 收货方式  1-配送  2-自提
     */
    private Integer deliveryType;

    /**
     * 配送费(单位-分)
     */
    private Long deliveryFee;

    /**
     * 订单配送时间或者自提时间(YYYYMMdd-hh:mm-hh:mm)
     */
    private String deliveryTime;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单完成时间
     */
    private Date completeTime;

    /**
     * 订单支付时间
     */
    private Date payTime;

    /**
     * 商家接单时间
     */
    private Date promiseTime;



    /**
     * 订单更新时间
     */
    private Date updateTime;

    /**
     * 更新员工id
     */
    private Integer updateStaffId;
    /**
     * 收货时间
     */
    private Date receiveTime;
    /**
     * 评价时间
     */
    private Date commentTime;
    /**
     * 版本号
     */
    private Integer version;

    private String deliveryAddress;

    /**
     * 评分
     */
    private Integer commentScore;

    public Integer getCommentScore() {
        return commentScore;
    }

    public void setCommentScore(Integer commentScore) {
        this.commentScore = commentScore;
    }

    /**
     * 获取订单id 唯一标识
     *
     * @return 订单id 唯一标识
     */
    public Long getOrderId() {
        return this.orderId;
    }

    /**
     * 设置订单id 唯一标识
     *
     * @param orderId 订单id 唯一标识
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取订单编号，用于平台对用户展示
     *
     * @return 订单编号
     */
    public String getOrderNo() {
        return this.orderNo;
    }

    /**
     * 设置订单编号，用于平台对用户展示
     *
     * @param orderNo 订单编号，用于平台对用户展示
     */
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取商家id
     *
     * @return 商家id
     */
    public Integer getVendorId() {
        return this.vendorId;
    }

    /**
     * 设置商家id
     *
     * @param vendorId 商家id
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 获取订单状态，关联字典值表tf_d_state
     *
     * @return 订单状态
     */
    public Integer getOrderState() {
        return this.orderState;
    }

    /**
     * 设置订单状态，关联字典值表tf_d_state
     *
     * @param orderState 订单状态，关联字典值表tf_d_state
     */
    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    /**
     * 获取订单完成状态 0-未完成  1-已完成  2-已取消
     *
     * @return 订单完成状态 0-未完成  1-已完成  2-已取消
     */
    public Integer getCompleteState() {
        return this.completeState;
    }

    /**
     * 设置订单完成状态 0-未完成  1-已完成  2-已取消
     *
     * @param completeState 订单完成状态 0-未完成  1-已完成  2-已取消
     */
    public void setCompleteState(Integer completeState) {
        this.completeState = completeState;
    }

    public Integer getRefundState() {
        return refundState;
    }

    public void setRefundState(Integer refundState) {
        this.refundState = refundState;
    }

    /**
     * 获取评价状态  0-未评价  1-已评价
     *
     * @return 评价状态  0-未评价  1-已评价
     */
    public Integer getCommentState() {
        return this.commentState;
    }

    /**
     * 设置评价状态  0-未评价  1-已评价
     *
     * @param commentState 评价状态  0-未评价  1-已评价
     */
    public void setCommentState(Integer commentState) {
        this.commentState = commentState;
    }

    /**
     * 获取下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     *
     * @return 下单渠道
     */
    public Integer getOrderChannel() {
        return this.orderChannel;
    }

    /**
     * 设置下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     *
     * @param orderChannel 下单渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     */
    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    /**
     * 获取支付渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     *
     * @return 支付渠道
     */
    public Integer getPayChannel() {
        return this.payChannel;
    }

    /**
     * 设置支付渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     *
     * @param payChannel 支付渠道：1-微信小程序 2-支付宝小程序 3-app 4-web
     */
    public void setPayChannel(Integer payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 获取支付类型id  关联支付类型表
     *
     * @return 支付类型id  关联支付类型表
     */
    public Integer getPayTypeId() {
        return this.payTypeId;
    }

    /**
     * 设置支付类型id  关联支付类型表
     *
     * @param payTypeId 支付类型id  关联支付类型表
     */
    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    /**
     * 获取支付类型名称
     *
     * @return 支付类型名称
     */
    public String getPayTypeName() {
        return this.payTypeName;
    }

    /**
     * 设置支付类型名称
     *
     * @param payTypeName 支付类型名称
     */
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    /**
     * 获取支付方式id  关联支付方式表
     *
     * @return 支付方式id  关联支付方式表
     */
    public Integer getPayWayId() {
        return this.payWayId;
    }

    /**
     * 设置支付方式id  关联支付方式表
     *
     * @param payWayId 支付方式id  关联支付方式表
     */
    public void setPayWayId(Integer payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * 获取支付类型名称
     *
     * @return 支付类型名称
     */
    public String getPayWayName() {
        return this.payWayName;
    }

    /**
     * 设置支付类型名称
     *
     * @param payWayName 支付类型名称
     */
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    /**
     * 获取订单原价(单位-分)
     *
     * @return 订单原价(单位-分)
     */
    public Long getOriginalPrice() {
        return this.originalPrice;
    }

    /**
     * 设置订单原价(单位-分)
     *
     * @param originalPrice 订单原价(单位-分)
     */
    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * 获取支付金额(单位-分)
     *
     * @return 支付金额(单位-分)
     */
    public Long getPayPrice() {
        return this.payPrice;
    }

    /**
     * 设置支付金额(单位-分)
     *
     * @param payPrice 支付金额(单位-分)
     */
    public void setPayPrice(Long payPrice) {
        this.payPrice = payPrice;
    }

    /**
     * 获取优惠金额(单位-分)
     *
     * @return 优惠金额(单位-分)
     */
    public Long getCouponAmount() {
        return this.couponAmount;
    }

    /**
     * 设置优惠金额(单位-分)
     *
     * @param couponAmount 优惠金额(单位-分)
     */
    public void setCouponAmount(Long couponAmount) {
        this.couponAmount = couponAmount;
    }

    /**
     * 获取收货方式  1-配送  2-自提
     *
     * @return 收货方式  1-配送  2-自提
     */
    public Integer getDeliveryType() {
        return this.deliveryType;
    }

    /**
     * 设置收货方式  1-配送  2-自提
     *
     * @param deliveryType 收货方式  1-配送  2-自提
     */
    public void setDeliveryType(Integer deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取配送费(单位-分)
     *
     * @return 配送费(单位-分)
     */
    public Long getDeliveryFee() {
        return this.deliveryFee;
    }

    /**
     * 设置配送费(单位-分)
     *
     * @param deliveryFee 配送费(单位-分)
     */
    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 获取订单创建时间
     *
     * @return 订单创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置订单创建时间
     *
     * @param createTime 订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取订单完成时间
     *
     * @return 订单完成时间
     */
    public Date getCompleteTime() {
        return this.completeTime;
    }

    /**
     * 设置订单完成时间
     *
     * @param completeTime 订单完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 获取订单支付时间
     *
     * @return 订单支付时间
     */
    public Date getPayTime() {
        return this.payTime;
    }

    /**
     * 设置订单支付时间
     *
     * @param payTime 订单支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取商家接单时间
     *
     * @return 商家接单时间
     */
    public Date getPromiseTime() {
        return this.promiseTime;
    }

    /**
     * 设置商家接单时间
     *
     * @param promiseTime 商家接单时间
     */
    public void setPromiseTime(Date promiseTime) {
        this.promiseTime = promiseTime;
    }


    /**
     * 获取订单更新时间
     *
     * @return 订单更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置订单更新时间
     *
     * @param updateTime 订单更新时间
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


    /**
     * 价格处理
     * @return
     */
    public String getRealPayPrice(){
            if (RStr.isNotEmpty(this.getPayPrice())){
                return StringUtil.changeF2Y(this.getPayPrice().toString());
            }
        return null;
    }

    public String getRealOriginalPrice(){
        return StringUtil.changeF2Y(this.getOriginalPrice().toString());
    }

    public String getRealCouponAmount(){
        return StringUtil.changeF2Y(this.getCouponAmount().toString());
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * 日期处理
     * @return
     */
    public String getRealUpdateTime(){
        if (this.getUpdateTime()!=null) {
            return DateUtil.format(this.getUpdateTime(), "yyyy-MM-dd HH:mm");
        }
        return null;
    }
    /**
     * 日期处理
     * @return
     */
    public String getRealCreateTime(){
        if (this.getCreateTime()!=null) {
            return DateUtil.format(this.getCreateTime(), "yyyy-MM-dd HH:mm");
        }
        return null;
    }

    public String getRealPayTime(){
        if (this.getPayTime()!=null) {
            return DateUtil.format(this.getPayTime(), "yyyy-MM-dd HH:mm");
        }
        return null;
    }
    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getValidityState() {
        return validityState;
    }

    public void setValidityState(Integer validityState) {
        this.validityState = validityState;
    }

    /**前端显示状态*/
    public String getShowStatus(){
        return getOrderBusinessState().getDescription();
    }
    /**前端显示状态*/
    public Integer getShowType(){
        return getOrderBusinessState().getState();
    }
    /**获取枚举 状态值*/
    public OrderConstant.OrderBusinessStateEnum getOrderBusinessState(){
        OrderStateBO bo=new OrderStateBO();
        bo.setOrderState(this.getOrderState());
        bo.setCompleteState(this.getCompleteState());
        bo.setCommentState(this.getCommentState());
        bo.setRefundState(this.getRefundState());
        return OrderStateHelp.transBusinessOrderState(bo);
    }


}