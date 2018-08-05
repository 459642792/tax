
package com.blueteam.entity.po;

/**
 * 订单费用明细表(TF_B_ORDER_FEE)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class OrderFeePO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 397252895197945737L;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 费用类型编码
     */
    private Integer feeTypeCode;

    /**
     * 费用类型名称
     */
    private String feeTypeName;

    /**
     * 应收费用(单位-分)
     */
    private Long receivableFee;

    /**
     * 实际费用(单位-分)
     */
    private Long realFee;

    /**
     * 优惠金额(单位-分)
     */
    private Long counponAmount;

    /**
     * 费用归属
     */
    private String feeBelong;

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
     * 获取费用类型编码
     *
     * @return 费用类型编码
     */
    public Integer getFeeTypeCode() {
        return this.feeTypeCode;
    }

    /**
     * 设置费用类型编码
     *
     * @param feeTypeCode 费用类型编码
     */
    public void setFeeTypeCode(Integer feeTypeCode) {
        this.feeTypeCode = feeTypeCode;
    }

    /**
     * 获取费用类型名称
     *
     * @return 费用类型名称
     */
    public String getFeeTypeName() {
        return this.feeTypeName;
    }

    /**
     * 设置费用类型名称
     *
     * @param feeTypeName 费用类型名称
     */
    public void setFeeTypeName(String feeTypeName) {
        this.feeTypeName = feeTypeName;
    }

    /**
     * 获取应收费用(单位-分)
     *
     * @return 应收费用(单位-分)
     */
    public Long getReceivableFee() {
        return this.receivableFee;
    }

    /**
     * 设置应收费用(单位-分)
     *
     * @param receivableFee 应收费用(单位-分)
     */
    public void setReceivableFee(Long receivableFee) {
        this.receivableFee = receivableFee;
    }

    /**
     * 获取实际费用(单位-分)
     *
     * @return 实际费用(单位-分)
     */
    public Long getRealFee() {
        return this.realFee;
    }

    /**
     * 设置实际费用(单位-分)
     *
     * @param realFee 实际费用(单位-分)
     */
    public void setRealFee(Long realFee) {
        this.realFee = realFee;
    }

    /**
     * 获取优惠金额(单位-分)
     *
     * @return 优惠金额(单位-分)
     */
    public Long getCounponAmount() {
        return this.counponAmount;
    }

    /**
     * 设置优惠金额(单位-分)
     *
     * @param counponAmount 优惠金额(单位-分)
     */
    public void setCounponAmount(Long counponAmount) {
        this.counponAmount = counponAmount;
    }

    /**
     * 获取费用归属
     *
     * @return 费用归属
     */
    public String getFeeBelong() {
        return this.feeBelong;
    }

    /**
     * 设置费用归属
     *
     * @param feeBelong 费用归属
     */
    public void setFeeBelong(String feeBelong) {
        this.feeBelong = feeBelong;
    }
}