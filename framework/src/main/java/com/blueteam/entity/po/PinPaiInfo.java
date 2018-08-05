package com.blueteam.entity.po;

/**
 * @author Marx
 * <p>
 * PinPaiInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class PinPaiInfo {
    //����ID
    private int Id;
    //�������
    private String Name;
    //����ƴ��
    private String Desc;
    //���и���code
    private int Status;
    //����code
    private String CreateDate;
    //���б�־
    private String CreateBy;
    //�޸�ʱ��
    private String UpdateDate;
    //�޸���
    private String UpdateBy;

    private String Image;

    private int sortNumber;

    public int getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(int sortNumber) {
        this.sortNumber = sortNumber;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
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
