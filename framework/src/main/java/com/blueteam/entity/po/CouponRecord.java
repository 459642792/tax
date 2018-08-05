package com.blueteam.entity.po;

import java.math.BigDecimal;

/**
 * @author Marx
 * <p>
 * CouponRecord.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class CouponRecord {
    //����ID
    private Integer Id;
    //�������
    private String Title;
    //����ƴ��
    private String Remark;

    private String UpdateDate;
    //���б�־
    private String CreateBy;
    //����ʱ��
    private String CreateDate;
    //������
    private String UpdateBy;
    //�޸�ʱ��
    private String UserId;
    //�޸���
    private String CouponId;

    private Integer VendorInfoCouponId;


    private BigDecimal Money;
    private BigDecimal TotalMoney;
    private String NickName;

    private String OrderNo;

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public BigDecimal getMoney() {
        return Money;
    }

    public void setMoney(BigDecimal money) {
        Money = money;
    }

    public BigDecimal getTotalMoney() {
        return TotalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        TotalMoney = totalMoney;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCouponId() {
        return CouponId;
    }

    public void setCouponId(String couponId) {
        CouponId = couponId;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public Integer getVendorInfoCouponId() {
        return VendorInfoCouponId;
    }

    public void setVendorInfoCouponId(Integer vendorInfoCouponId) {
        VendorInfoCouponId = vendorInfoCouponId;
    }


}
