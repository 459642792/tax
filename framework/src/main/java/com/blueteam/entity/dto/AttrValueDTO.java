package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;

import static com.blueteam.base.constant.FieldValidateConstants.ATTR_ADD_DTO;
import static com.blueteam.base.constant.FieldValidateConstants.ATTR_EDIT_DTO;
import static com.blueteam.base.constant.FieldValidateConstants.ATTR_REMOVE_DTO;

/**
 * Created by  NastyNas on 2017/10/18.
 */
public class AttrValueDTO {
    //校验类别
    String validateType;
    @FieldValidate(notNullValidateTypes = {ATTR_ADD_DTO, ATTR_EDIT_DTO, ATTR_REMOVE_DTO})
    String attrCode;
    @FieldValidate(notNullValidateTypes = {ATTR_EDIT_DTO, ATTR_REMOVE_DTO})
    String attrValueCode;
    @FieldValidate(notNullValidateTypes = {ATTR_ADD_DTO, ATTR_EDIT_DTO})
    String attrValueName;
    @FieldValidate(notNullValidateTypes = {ATTR_EDIT_DTO, ATTR_REMOVE_DTO})
    String password;

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

    public String getAttrValueName() {
        return attrValueName;
    }

    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    @Override
    public String toString() {
        return "AttrValueDTO{" +
                "validateType='" + validateType + '\'' +
                ", attrCode='" + attrCode + '\'' +
                ", attrValueCode='" + attrValueCode + '\'' +
                ", attrValueName='" + attrValueName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
