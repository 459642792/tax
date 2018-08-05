package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by  NastyNas on 2017/10/28.
 */
public class AttrDTO {
    //属性编码
    @FieldValidate(notNull = true)
    String attrCode;
    //属性值编码， 基础且select-非空  input-可为空
    String attrValueCode;
    //属性类别：0-select  0-input
    @FieldValidate(notNull = true)
    Integer attrType;
    //属性值名： select-可为空   基础且input-非空
    String attrValueName;
    //父属性编码： 基础属性非空
    String parentAttrCode;

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrValueCode() {
        return attrValueCode;
    }

    public void setAttrValueCode(String attrValueCode) {
        this.attrValueCode = attrValueCode;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public String getAttrValueName() {
        return attrValueName;
    }

    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    public String getParentAttrCode() {
        return parentAttrCode;
    }

    public void setParentAttrCode(String parentAttrCode) {
        this.parentAttrCode = parentAttrCode;
    }
}
