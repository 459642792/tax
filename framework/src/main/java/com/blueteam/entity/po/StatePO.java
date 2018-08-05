
package com.blueteam.entity.po;

/**
 * 状态字典值表(TF_D_STATE)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class StatePO implements java.io.Serializable {
    //订单状态归属：已取消
    public static final int ORDER_STATE_TO_CANCEL = 0;
    //订单状态归属：已完成
    public static final int ORDER_STATE_TO_COMPLETE = 1;

    //状态类型：订单状态
    public static final int STATE_TYPE_ORDER = 0;
    //状态类型：退款状态
    public static final int STATE_TYPE_REFUND = 1;



    /**
     * 版本号
     */
    private static final long serialVersionUID = -1944915612163787223L;

    /**
     * 状态id
     */
    private Integer stateId;
    /**
     * 状态类型 0-订单状态 1-退款状态
     */
    private Integer stateType;

    /**
     * 状态名称
     */
    private String stateName;
    /**
     * 状态业务类型，针对订单状态为订单完成类型：0-已取消 1-已完成；针对退款状态待定默认0
     */
    private Integer stateBusinessType;

    /**
     * 订单状态逻辑顺序
     */
    private Integer stateOrder;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getStateType() {
        return stateType;
    }

    public void setStateType(Integer stateType) {
        this.stateType = stateType;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getStateBusinessType() {
        return stateBusinessType;
    }

    public void setStateBusinessType(Integer stateBusinessType) {
        this.stateBusinessType = stateBusinessType;
    }

    public Integer getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(Integer stateOrder) {
        this.stateOrder = stateOrder;
    }
}