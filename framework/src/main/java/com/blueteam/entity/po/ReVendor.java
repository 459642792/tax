package com.blueteam.entity.po;


/**
 * 推荐店铺
 *
 * @author libra
 */
public class ReVendor {
    /**
     * 推荐店铺
     */
    private int Id;

    private String AreaAddr;

    private String VendorName;

    private String CreateDate;

    private int OrderField;

    private String CreateBy;

    private int VendorId;

    public int getVendorId() {
        return VendorId;
    }

    public void setVendorId(int vendorId) {
        VendorId = vendorId;
    }

    public String getAreaAddr() {
        return AreaAddr;
    }

    public void setAreaAddr(String areaAddr) {
        AreaAddr = areaAddr;
    }

    public String getVendorName() {
        return VendorName;
    }

    public void setVendorName(String vendorName) {
        VendorName = vendorName;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public int getOrderField() {
        return OrderField;
    }

    public void setOrderField(int orderField) {
        OrderField = orderField;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public int getClickCount() {
        return ClickCount;
    }

    public void setClickCount(int clickCount) {
        ClickCount = clickCount;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public String getEabledFlag() {
        return EabledFlag;
    }

    public void setEabledFlag(String eabledFlag) {
        EabledFlag = eabledFlag;
    }

    public String getTradingArea() {
        return TradingArea;
    }

    public void setTradingArea(String tradingArea) {
        TradingArea = tradingArea;
    }

    private String UpdateDate;

    private int ClickCount;

    private String UpdateBy;

    private String EabledFlag;

    private String TradingArea;

    private String CarriersName;

    public String getCarriersName() {
        return CarriersName;
    }

    public void setCarriersName(String carriersName) {
        CarriersName = carriersName;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
