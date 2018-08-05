package com.blueteam.base.constant;

/**
 * 订单相关常量
 * Created by  NastyNas on 2018/1/9.
 */
public final class OrderConstant {


    /**
     * 订单表订单状态
     */
    public enum OrderStateEnum {
        TO_PAY(0, "待付款"),
        TO_CONFIRM(1, "待确认"),
        TO_PROMISE(2, "待接单"),
        TO_RECEIVE(3, "待收货"),
        HAS_RECEIVE(4, "已收货");
        Integer state;
        String description;

        OrderStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * 订单表退款状态
     */
    public enum RefundStateEnum {
        TO_APPLY_REFUND(0, "未申请退款"),
        APPLY_REFUND_ING(1, "申请退款中"),
        REFUND_ING(2, "退款中"),
        REFUND_FAIL(3, "退款失败"),
        REFUND_SUCCESS(4, "退款成功");
        Integer state;
        String description;

        RefundStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    /**
     * 订单表完成状态
     */
    public enum CompleteStateEnum {
        UNFINISHED(0, "未完成"),
        FINISHED(1, "已完成"),
        CANCELED(2, "已取消");
        Integer state;
        String description;

        CompleteStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    /**
     * 订单表评价状态
     */
    public enum CommentStateEnum {
        TO_COMMENT(0, "未评价"),
        HAS_COMMENT(1, "已评价");
        Integer state;
        String description;

        CommentStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }


    /**
     * 订单的业务状态
     */
    public enum OrderBusinessStateEnum {
        ALL(0, "全部"),
        TO_PAY(1, "待付款"),
        TO_PROMISE(2, "待接单"),
        TO_RECEIVE(3, "待收货"),
        TO_COMMENT(4, "待评价"),
        ORDER_COMPLETE(5, "已完成"),
        ORDER_CANCEL(6, "已取消"),
        APPLY_DRAWBACK_ING(7, "申请退款中"),
        DRAWBACK_ING(8, "退款中"),
        DRAWBACK_FAIL(9, "退款失败"),
        DRAWBACK_SUCCESS(10, "退款成功"),
        TO_CONFIRM(11, "待确认"),
        /*
         *组合类型
         */
        TRADE_SUCCESS(100, "交易成功");


        Integer state;
        String description;

        OrderBusinessStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public static OrderBusinessStateEnum match(Integer state) {
            for (OrderBusinessStateEnum orderBusinessStateEnum : OrderBusinessStateEnum.values()) {
                if (orderBusinessStateEnum.getState().equals(state)) {
                    return orderBusinessStateEnum;
                }
            }
            //未匹配返空
            return null;
        }
    }


    /**
     * 订单表退款理由
     */
    public enum RefundRemarkEnum {
        GOODS_DISCREPANCY_DESCRIPTION(0, "商品与描述不符"),
        NO_BUY(1, "不想买了"),
        AN_ADDITIONAL_CHARGE(2, "店家收取配送费"),
        VENDOR_NO_DISPATCH(3, "店家不愿配送"),
        REPLACE_GOODS(4, "换成其它商品"),
        OTHER(9, "其他");
        Integer code;
        String description;

        RefundRemarkEnum(Integer code, String description) {
            this.code = code;
            this.description = description;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public static RefundRemarkEnum match(Integer code) {
            for (RefundRemarkEnum refundRemarkEnum : RefundRemarkEnum.values()) {
                if (refundRemarkEnum.getCode().equals(code)) {
                    return refundRemarkEnum;
                }
            }
            return RefundRemarkEnum.OTHER;
        }
    }

    /**
     * 订单有效状态
     */
    public enum OrderValidityStateEnum {
        TO_DELETE(0, "删除订单"),
        TO_CANCEL(2, "取消订单"),
        OTHER(9, "不显示");
        Integer state;
        String description;

        OrderValidityStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    /**
     * 订单操作状态
     */
    public enum OrderOperateStateEnum {
        TO_PAY(0, "去支付"),
        TO_REFUND(1, "去退款"),
        TO_COMMENT(2, "去评价"),
        CANCEL_REFUND(3, "取消申请退款"),
        OTHER(9, "不显示");
        Integer state;
        String description;

        OrderOperateStateEnum(Integer state, String description) {
            this.state = state;
            this.description = description;
        }

        public Integer getState() {
            return state;
        }

        public void setState(Integer state) {
            this.state = state;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

}
