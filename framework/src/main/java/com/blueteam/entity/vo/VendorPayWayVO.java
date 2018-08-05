package com.blueteam.entity.vo;


/**
 * 显示支付方式类
 *
 * @author xiaojiang
 * @create 2018-01-08  14:43
 */
public class VendorPayWayVO{


    /**  支付方式id*/
    private Integer payWayId;

    /**  支付名称*/
    private String payWayName;

    /** 展示顺序(升序)*/
    private Integer showOrder;

    public Integer getPayWayId() {
        return payWayId;
    }

    public void setPayWayId(Integer payWayId) {
        this.payWayId = payWayId;
    }

    public String getPayWayName() {
        return payWayName;
    }


    public void setPayWayName(String payWayName) {
        this.payWayName = payWayName;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }

    @Override
    public String toString() {
        return "VendorPayWayVO{" +
                "payWayId=" + payWayId +
                ", payWayName='" + payWayName + '\'' +
                ", showOrder=" + showOrder +
                '}';
    }
}
