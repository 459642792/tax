package com.blueteam.entity.vo;


/**
 * 商家支付类型显示VO
 *
 * @author xiaojiang
 * @create 2018-01-08  14:39
 */
public class VendorPayTypeVO {
    /**
     * 支付类型id
     */
    private Integer payTypeId;

    /**
     * 支付类型名称
     */
    private String payTypeName;

    /**
     * 展示顺序(升序)
     */
    private Integer showOrder;

    public Integer getPayTypeId() {
        return payTypeId;
    }

    public void setPayTypeId(Integer payTypeId) {
        this.payTypeId = payTypeId;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    @Override
    public String toString() {
        return "VendorPayTypeVO{" +
                "payTypeId=" + payTypeId +
                ", payTypeName='" + payTypeName + '\'' +
                ", showOrder=" + showOrder +
                '}';
    }
}
