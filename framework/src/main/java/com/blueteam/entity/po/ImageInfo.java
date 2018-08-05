package com.blueteam.entity.po;

/**
 * @author Marx
 * <p>
 * ImageInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class ImageInfo {
    //����ID
    private Integer Id;
    //�������
    private String Image;
    //����ƴ��
    private String Url;
    //���и���code
    private String Title;
    //����code

    private Integer Status;
    //���б�־
    private String Detail;
    //����ʱ��
    private String ForeignKey;
    //������
    private String Type;
    //�޸�ʱ��
    private Integer SortNumber;
    //�޸���
    private String CreateDate;

    private String CreateBy;

    private String UpdateDate;

    private String UpdateBy;

    private Integer Vendor;

    private boolean isFirst;

    private Integer ExtendId;

    public Integer getExtendId() {
        return ExtendId;
    }

    public void setExtendId(Integer extendId) {
        ExtendId = extendId;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getVendor() {
        return Vendor;
    }

    public void setVendor(Integer vendor) {
        Vendor = vendor;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(String foreignKey) {
        ForeignKey = foreignKey;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public Integer getSortNumber() {
        return SortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        SortNumber = sortNumber;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
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

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }
}
