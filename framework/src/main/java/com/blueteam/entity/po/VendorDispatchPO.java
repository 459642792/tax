
package com.blueteam.entity.po;

/**
 * 商家配送信息表(TF_F_VENDOR_DISPATCH)
 *
 * @author bi
 * @version 1.0.0 2018-01-04
 */
public class VendorDispatchPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -1930292701677377106L;

    /**
     * 商家id
     */
    private Integer vendorId;

    /**
     * 配送id
     */
    private Integer dispatchId;

    /**
     * 状态标记：0-无效 1-有效
     */
    private Integer stateTag;

    /**
     * 展示顺序(降序)
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
     * 获取配送id
     *
     * @return 配送id
     */
    public Integer getDispatchId() {
        return this.dispatchId;
    }

    /**
     * 设置配送id
     *
     * @param dispatchId 配送id
     */
    public void setDispatchId(Integer dispatchId) {
        this.dispatchId = dispatchId;
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
     * 获取展示顺序(降序)
     *
     * @return 展示顺序(降序)
     */
    public Integer getShowOrder() {
        return this.showOrder;
    }

    /**
     * 设置展示顺序(降序)
     *
     * @param showOrder 展示顺序(降序)
     */
    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }
}