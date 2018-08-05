package com.blueteam.base.help.order;

import com.blueteam.base.constant.OrderConstant;
import com.blueteam.base.exception.BusinessException;
import com.blueteam.base.lang.RList;
import com.blueteam.entity.bo.OrderStateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 订单业务状态与数据库订单相关状态字段转化帮助类
 * Created by  NastyNas on 2018/1/9.
 */
public class OrderStateHelp {
    private static final Logger logger = LoggerFactory.getLogger(OrderStateHelp.class);

    /**
     * 是否为待付款
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isToPay(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
    }

    /**
     * 是否为待付款
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isToPay(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TO_PAY.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }

    /**
     * 是否为待接单
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isToPromise(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
    }

    /**
     * 是否为待接单
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isToPromise(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TO_PROMISE.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为待收货
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isToReceive(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_RECEIVE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
    }

    /**
     * 是否为待收货
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isToReceive(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TO_RECEIVE.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为待评价
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isToComment(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.FINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.HAS_RECEIVE.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState());
    }

    /**
     * 是否为待评价
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isToComment(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TO_COMMENT.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为已完成
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isOrderComplete(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.FINISHED.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.HAS_COMMENT.getState());
    }

    /**
     * 是否为已完成
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isOrderComplete(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.ORDER_COMPLETE.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为已取消
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isOrderCancel(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.CANCELED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
    }

    /**
     * 是否为已取消
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isOrderCancel(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.ORDER_CANCEL.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为申请退款中
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isApplyDrawbackIng(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState());
    }

    /**
     * 是否为申请退款中
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isApplyDrawbackIng(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.APPLY_DRAWBACK_ING.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为退款中
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isDrawbackIng(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_ING.getState());
    }

    /**
     * 是否为退款中
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isDrawbackIng(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.DRAWBACK_ING.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为退款失败
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isDrawbackFail(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_FAIL.getState());
    }

    /**
     * 是否为退款失败
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isDrawbackFail(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /**
     * 是否为退款成功
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isDrawbackSuccess(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_SUCCESS.getState());
    }

    /**
     * 是否为退款成功
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isDrawbackSuccess(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }

    /**
     * 是否为待确认
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isToConfirm(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_CONFIRM.getState());
    }

    /**
     * 是否为待确认
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isToConfirm(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TO_CONFIRM.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }


    /*
     * 组合订单状态 start
     */

    /**
     * 是否为交易成功
     * 交易成功:待评价+已完成
     *
     * @param orderStateBO
     * @return
     */
    public static boolean isTradeSuccess(OrderStateBO orderStateBO) {
        checkOrderStateBO(orderStateBO);
        return isOrderComplete(orderStateBO) || isToComment(orderStateBO);
    }

    /**
     * 是否为交易成功
     *
     * @param orderBusinessState
     * @return
     */
    public static boolean isTradeSuccess(Integer orderBusinessState) {
        return OrderConstant.OrderBusinessStateEnum.TRADE_SUCCESS.getState().equals(parseBusinessOrderStateEnum(orderBusinessState).getState());
    }

    /*
     * 组合订单状态 end
     */

    /**
     * 订单表状态字段BO转换成订单业务状态
     *
     * @param orderStateBO
     * @return
     */
    public static OrderConstant.OrderBusinessStateEnum transBusinessOrderState(OrderStateBO orderStateBO) {
        //非空校验
        checkOrderStateBO(orderStateBO);
        //待付款
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState())) {
            return OrderConstant.OrderBusinessStateEnum.TO_PAY;
        }
        //待接单
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState())) {
            return OrderConstant.OrderBusinessStateEnum.TO_PROMISE;
        }
        //待收货
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_RECEIVE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState())) {
            return OrderConstant.OrderBusinessStateEnum.TO_RECEIVE;
        }
        //待评价
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.FINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.HAS_RECEIVE.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderBusinessStateEnum.TO_COMMENT;
        }
        //已完成 (订单已完成=交易完成+已评价)
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.FINISHED.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.HAS_COMMENT.getState())) {
            return OrderConstant.OrderBusinessStateEnum.ORDER_COMPLETE;
        }
        //已取消(不包括退款成功/失败的已取消)
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.CANCELED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState())) {
            return OrderConstant.OrderBusinessStateEnum.ORDER_CANCEL;
        }
        //申请退款中
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState())) {
            return OrderConstant.OrderBusinessStateEnum.APPLY_DRAWBACK_ING;
        }
        //退款中
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_ING.getState())) {
            return OrderConstant.OrderBusinessStateEnum.DRAWBACK_ING;
        }
        //退款失败
        if (orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_FAIL.getState())) {
            return OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL;
        }
        //退款成功
        if (orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.REFUND_SUCCESS.getState())) {
            return OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS;
        }
        //待确认
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_CONFIRM.getState())) {
            return OrderConstant.OrderBusinessStateEnum.TO_CONFIRM;
        }
        logger.error("订单状态转化业务状态错误,订单相关状态数据错误,OrderStateBO={}", orderStateBO);
        throw new BusinessException("订单相关状态数据错误");
    }


    /**
     * 订单业务状态转换成订单表状态字段BO
     *
     * @return
     */
    public static OrderStateBO transOrderStateBO(Integer businessState) {
        //转化订单业务状态枚举
        OrderConstant.OrderBusinessStateEnum orderBusinessStateEnum = parseBusinessOrderStateEnum(businessState);
        //全部
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.ALL.getState())) {
            return new OrderStateBO();
        }
        //待付款
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TO_PAY.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setOrderState(OrderConstant.OrderStateEnum.TO_PAY.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
            return orderStateBO;
        }
        //待接单
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TO_PROMISE.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setOrderState(OrderConstant.OrderStateEnum.TO_PROMISE.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
            return orderStateBO;
        }
        //待收货
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TO_RECEIVE.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setOrderState(OrderConstant.OrderStateEnum.TO_RECEIVE.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
            return orderStateBO;
        }
        //待评价
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TO_COMMENT.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.FINISHED.getState());
            orderStateBO.setOrderState(OrderConstant.OrderStateEnum.HAS_RECEIVE.getState());
            orderStateBO.setCommentState(OrderConstant.CommentStateEnum.TO_COMMENT.getState());
            return orderStateBO;
        }

        //已完成 (订单已完成=交易完成+已评价)
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.ORDER_COMPLETE.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.FINISHED.getState());
            orderStateBO.setCommentState(OrderConstant.CommentStateEnum.HAS_COMMENT.getState());
            return orderStateBO;
        }
        //已取消 (不包括退款成功/失败的已取消)
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.ORDER_CANCEL.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.CANCELED.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState());
            return orderStateBO;
        }

        //申请退款中
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.APPLY_DRAWBACK_ING.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState());
            return orderStateBO;
        }
        //退款中
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.DRAWBACK_ING.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.REFUND_ING.getState());
            return orderStateBO;
        }

        //退款失败
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.DRAWBACK_FAIL.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.REFUND_FAIL.getState());
            return orderStateBO;
        }

        //退款成功
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.DRAWBACK_SUCCESS.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setRefundState(OrderConstant.RefundStateEnum.REFUND_SUCCESS.getState());
            return orderStateBO;
        }

        //待确认
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TO_CONFIRM.getState())) {
            OrderStateBO orderStateBO = new OrderStateBO();
            orderStateBO.setCompleteState(OrderConstant.CompleteStateEnum.UNFINISHED.getState());
            orderStateBO.setOrderState(OrderConstant.OrderStateEnum.TO_CONFIRM.getState());
            return orderStateBO;
        }
        logger.error("业务状态转化订单状态错误,订单相关状态数据错误,orderBusinessState={}", orderBusinessStateEnum);
        throw new BusinessException("业务状态转化订单状态错误,订单相关状态数据错误");
    }

    /**
     * 复合订单业务类型转换成订单表状态字段BO
     *
     * @param businessState
     * @return
     */
    public static List<OrderStateBO> transCompositeOrderStateBO(Integer businessState) {
        //转化订单业务状态枚举
        OrderConstant.OrderBusinessStateEnum orderBusinessStateEnum = parseBusinessOrderStateEnum(businessState);
        if (orderBusinessStateEnum.getState().equals(OrderConstant.OrderBusinessStateEnum.TRADE_SUCCESS.getState())) {
            //待评价
            OrderStateBO toCommentBO = OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.TO_COMMENT.getState());
            //已完成
            OrderStateBO completeBO = OrderStateHelp.transOrderStateBO(OrderConstant.OrderBusinessStateEnum.ORDER_COMPLETE.getState());
            //交易成功=待评价+已完成
            return RList.asList(toCommentBO, completeBO);
        }
        logger.error("复合业务状态转化订单状态错误,订单相关状态数据错误,orderBusinessState={}", orderBusinessStateEnum);
        throw new BusinessException("业务状态转化订单状态错误,订单相关状态数据错误");
    }


    /**
     * 方法的功能描述: 判断订单有效状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2018/1/13 15:07
     * @modifier
     * @since 1.4.0
     */
    public static OrderConstant.OrderValidityStateEnum transOrderValidityState(OrderStateBO orderStateBO) {
        //非空校验
        if (orderStateBO == null || orderStateBO.getCompleteState() == null || orderStateBO.getOrderState() == null ||
                orderStateBO.getRefundState() == null || orderStateBO.getCommentState() == null) {
            throw new BusinessException("订单状态OrderStateBO参数非空校验失败");
        }
        //删除订单
        if (!orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState())) {
            return OrderConstant.OrderValidityStateEnum.TO_DELETE;
        }
        //取消订单
        if (orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState()) && orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState())) {
            return OrderConstant.OrderValidityStateEnum.TO_CANCEL;
        }
        return OrderConstant.OrderValidityStateEnum.OTHER;

    }

    /**
     * 方法的功能描述: 操作状态
     *
     * @return
     * @methodName
     * @param: null
     * @author xiaojiang 2018/1/13 15:25
     * @modifier
     * @since 1.4.0
     */
    public static OrderConstant.OrderOperateStateEnum transOrderOperateState(OrderStateBO orderStateBO) {
        //非空校验
        if (orderStateBO == null || orderStateBO.getCompleteState() == null || orderStateBO.getOrderState() == null ||
                orderStateBO.getRefundState() == null || orderStateBO.getCommentState() == null) {
            throw new BusinessException("订单状态OrderStateBO参数非空校验失败");
        }
        //去支付
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PAY.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderOperateStateEnum.TO_PAY;
        }
        //去评价
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.FINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.HAS_RECEIVE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderOperateStateEnum.TO_COMMENT;
        }
        //取消申请退款
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.APPLY_REFUND_ING.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderOperateStateEnum.CANCEL_REFUND;
        }

        //去退款
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_PROMISE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderOperateStateEnum.TO_REFUND;
        }
        //去退款
        if (orderStateBO.getCompleteState().equals(OrderConstant.CompleteStateEnum.UNFINISHED.getState()) &&
                orderStateBO.getOrderState().equals(OrderConstant.OrderStateEnum.TO_RECEIVE.getState()) &&
                orderStateBO.getRefundState().equals(OrderConstant.RefundStateEnum.TO_APPLY_REFUND.getState()) &&
                orderStateBO.getCommentState().equals(OrderConstant.CommentStateEnum.TO_COMMENT.getState())) {
            return OrderConstant.OrderOperateStateEnum.TO_REFUND;
        }

        return OrderConstant.OrderOperateStateEnum.OTHER;
    }

    /**
     * 校验订单状态字段非空
     *
     * @param orderStateBO
     */
    public static void checkOrderStateBO(OrderStateBO orderStateBO) {
        if (orderStateBO == null || orderStateBO.getCompleteState() == null || orderStateBO.getOrderState() == null ||
                orderStateBO.getRefundState() == null || orderStateBO.getCommentState() == null) {
            logger.error("订单状态字段转化订单业务状态错误,OrderStateBO参数非空校验失败");
            throw new BusinessException("订单状态字段转化订单业务状态错误");
        }
    }

    /**
     * 校验并转化订单业务状态枚举
     *
     * @param businessOrderState
     * @return
     */
    public static OrderConstant.OrderBusinessStateEnum parseBusinessOrderStateEnum(Integer businessOrderState) {
        if (businessOrderState == null || OrderConstant.OrderBusinessStateEnum.match(businessOrderState) == null) {
            logger.error("订单业务状态错误");
            throw new BusinessException("订单业务状态错误");
        }
        return OrderConstant.OrderBusinessStateEnum.match(businessOrderState);
    }


}
