package com.blueteam.entity.po;

/**
 * @author Marx
 * <p>
 * CityInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */
public class CityInfo {
    //����ID
    private int Id;
    //�������
    private String Name;
    //����ƴ��
    private String PinYin;
    //���и���code
    private String ParentCode;
    //����code

    private String Code;
    //���б�־
    private String EnableFlag;
    //����ʱ��
    private String CreateDate;
    //������
    private String CreateBy;
    //�޸�ʱ��
    private String UpdateDate;
    //�޸���
    private String UpdateBy;

    private String IsExistVendor;

    public String getIsExistVendor() {
        return IsExistVendor;
    }

    public void setIsExistVendor(String isExistVendor) {
        IsExistVendor = isExistVendor;
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

    public String getPinYin() {
        return PinYin;
    }

    public void setPinYin(String pinYin) {
        PinYin = pinYin;
    }

    public String getParentCode() {
        return ParentCode;
    }

    public void setParentCode(String parentCode) {
        ParentCode = parentCode;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getEnableFlag() {
        return EnableFlag;
    }

    public void setEnableFlag(String enableFlag) {
        EnableFlag = enableFlag;
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
