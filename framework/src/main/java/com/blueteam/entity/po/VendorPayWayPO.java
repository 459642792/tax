

package com.blueteam.entity.po;

/**
 * (TF_F_VENDOR_PAY_WAY)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class VendorPayWayPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8500275998732167611L;

    /**  */
    private Integer vendorId;

    /**  */
    private Integer payWayId;

    /**  */
    private String payWayName;

    /**  */
    private String stateTag;

    /**
     * 获取
     *
     * @return
     */
    public Integer getVendorId() {
        return this.vendorId;
    }

    /**
     * 设置
     *
     * @param vendorId
     */
    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * 获取
     *
     * @return
     */
    public Integer getPayWayId() {
        return this.payWayId;
    }

    /**
     * 设置
     *
     * @param payWayId
     */
    public void setPayWayId(Integer payWayId) {
        this.payWayId = payWayId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getPayWayName() {
        return this.payWayName;
    }

    /**
     * 设置
     *
     * @param payWayName
     */
    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getStateTag() {
        return this.stateTag;
    }

    /**
     * 设置
     *
     * @param stateTag
     */
    public void setStateTag(String stateTag) {
        this.stateTag = stateTag;
    }
}