

package com.blueteam.entity.po;

/**
 * 配送信息表(TF_M_DISPATCH)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class DispatchPO implements java.io.Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -6661513911775102542L;

    /**
     * 配送id
     */
    private Integer dispatchId;

    /**
     * 配送名称
     */
    private String dispatchName;

    /**
     * 配送类型 0-商家配送 1-平台配送 2-第三方配送
     */
    private Integer dispatchType;

    /**
     * 配送提供方
     */
    private String dispatchProvider;

    /**
     * 配送费用(单位-分)
     */
    private Long dispatchFee;

    /**
     * 配送详情
     */
    private String dispatchDetail;

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
     * 获取配送名称
     *
     * @return 配送名称
     */
    public String getDispatchName() {
        return this.dispatchName;
    }

    /**
     * 设置配送名称
     *
     * @param dispatchName 配送名称
     */
    public void setDispatchName(String dispatchName) {
        this.dispatchName = dispatchName;
    }

    /**
     * 获取配送类型 0-商家配送 1-平台配送 2-第三方配送
     *
     * @return 配送类型 0-商家配送 1-平台配送 2-第三方配送
     */
    public Integer getDispatchType() {
        return this.dispatchType;
    }

    /**
     * 设置配送类型 0-商家配送 1-平台配送 2-第三方配送
     *
     * @param dispatchType 配送类型 0-商家配送 1-平台配送 2-第三方配送
     */
    public void setDispatchType(Integer dispatchType) {
        this.dispatchType = dispatchType;
    }

    /**
     * 获取配送提供方
     *
     * @return 配送提供方
     */
    public String getDispatchProvider() {
        return this.dispatchProvider;
    }

    /**
     * 设置配送提供方
     *
     * @param dispatchProvider 配送提供方
     */
    public void setDispatchProvider(String dispatchProvider) {
        this.dispatchProvider = dispatchProvider;
    }

    /**
     * 获取配送费用(单位-分)
     *
     * @return 配送费用(单位-分)
     */
    public Long getDispatchFee() {
        return this.dispatchFee;
    }

    /**
     * 设置配送费用(单位-分)
     *
     * @param dispatchFee 配送费用(单位-分)
     */
    public void setDispatchFee(Long dispatchFee) {
        this.dispatchFee = dispatchFee;
    }

    /**
     * 获取配送详情
     *
     * @return 配送详情
     */
    public String getDispatchDetail() {
        return this.dispatchDetail;
    }

    /**
     * 设置配送详情
     *
     * @param dispatchDetail 配送详情
     */
    public void setDispatchDetail(String dispatchDetail) {
        this.dispatchDetail = dispatchDetail;
    }
}