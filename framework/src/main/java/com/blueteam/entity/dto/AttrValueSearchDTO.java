package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;

/**
 * Created by  NastyNas on 2017/10/17.
 */
public class AttrValueSearchDTO extends BasePageSearch {
    @FieldValidate(notNull = true)
    String attrCode;

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }
}
