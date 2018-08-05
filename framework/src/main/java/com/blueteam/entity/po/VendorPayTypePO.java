

package com.blueteam.entity.po;

/**
 * 商家支付类型表(TF_F_VENDOR_PAY_TYPE)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class VendorPayTypePO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 6938079686796036620L;

    /**
     * 商家id
     */
    private Integer vendorId;

    /**
     * 支付类型id
     */
    private Integer payTypeId;

    /**
     * 状态标记：0-无效 1-有效
     */
    private Integer stateTag;

    /**
     * 展示顺序(升序)
     */
    private Integer showOrder;

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
     * 获取状态标记：0-无效 1-有效
     *
     * @return 状态标记
     */
    public Integer getStateTag() {
        return this.stateTag;
    }

    /**
     * 设置状态标记：0-无效 1-有效
     *
     * @param stateTag 状态标记：0-无效 1-有效
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