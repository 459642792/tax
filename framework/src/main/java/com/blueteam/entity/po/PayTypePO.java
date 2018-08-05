

package com.blueteam.entity.po;

/**
 * 支付类型表(TF_M_PAY_TYPE)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class PayTypePO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -209623432841324064L;

    /**
     * 支付类型id
     */
    private Integer payTypeId;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 支付类型名
     */
    private String payTypeName;

    /**
     * 支付类型详情
     */
    private String payTypeDetail;

    /**
     * 状态标志：0-无效 1-有效
     */
    private Integer stateTag;

    /**
     * 展示顺序(升序)
     */
    private Integer showOrder;

    /**
     * 获取支付类型id
     *
     * @return 支付类型id
     */
    public Integer getPayTypeId() {
        return this.payTypeId;
    }

    /**
     * 设置支付类型id
     *
     * @param payTypeId 支付类型id
     */
    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    /**
     * 获取渠道id
     *
     * @return 渠道id
     */
    public Integer getChannelId() {
        return this.channelId;
    }

    /**
     * 设置渠道id
     *
     * @param channelId 渠道id
     */
    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    /**
     * 获取支付类型名
     *
     * @return 支付类型名
     */
    public String getPayTypeName() {
        return this.payTypeName;
    }

    /**
     * 设置支付类型名
     *
     * @param payTypeName 支付类型名
     */
    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    /**
     * 获取支付类型详情
     *
     * @return 支付类型详情
     */
    public String getPayTypeDetail() {
        return this.payTypeDetail;
    }

    /**
     * 设置支付类型详情
     *
     * @param payTypeDetail 支付类型详情
     */
    public void setPayTypeDetail(String payTypeDetail) {
        this.payTypeDetail = payTypeDetail;
    }

    /**
     * 获取状态标志：0-无效 1-有效
     *
     * @return 状态标志
     */
    public Integer getStateTag() {
        return this.stateTag;
    }

    /**
     * 设置状态标志：0-无效 1-有效
     *
     * @param stateTag 状态标志：0-无效 1-有效
     */
    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    /**
     * 获取展示顺序(升序)
     *
     * @return 展示顺序(升序)
     */
    public Integer getShowOrder() {
        return this.showOrder;
    }

    /**
     * 设置展示顺序(升序)
     *
     * @param showOrder 展示顺序(升序)
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }
}