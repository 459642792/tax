package com.blueteam.entity.po;


import com.blueteam.entity.dto.City;

/**
 * @author Marx
 * <p>
 * AdvertiseInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class AdvertiseInfo {
    //广告id
    private Integer Id;

    //广告类型
    private String TypeCode;

    //广告名称
    private String Name;

    //广告排序字段
    private Integer SortNumber;

    private String Img;

    private String Url;

    private String CreateBy;

    private String CreateDate;

    private String UpdateBy;

    private String UpdateDate;

    private String EnableFlag;

    private String ForeignKey;

    private String CityCode;

    private boolean isFirst;


    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 创建人nickName
     */
    private String creator;

    /**
     * 点击数量
     */
    private int clickCount;

    /**
     * 品牌ID
     */
    private Integer brandId;


    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTypeCode() {
        return TypeCode;
    }

    public void setTypeCode(String typeCode) {
        TypeCode = typeCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getSortNumber() {
        return SortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        SortNumber = sortNumber;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
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

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getEnableFlag() {
        return EnableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        EnableFlag = enableFlag;
    }

    public String getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(String foreignKey) {
        ForeignKey = foreignKey;
    }

    /**
     * 外键名称
     */
    private String foreignName;

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    /**
     * 显示区域
     */
    private String showArea;

    public String getShowArea() {
        return showArea;
    }

    public void setShowArea(String showArea) {
        this.showArea = showArea;
    }

    /**
     * 显示区域
     */
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
