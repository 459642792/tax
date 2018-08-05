package com.blueteam.entity.vo;

import java.io.Serializable;

/**
 * 商品关联属性
 *
 * @author xiaojiang
 * @create 2017-10-23  18:48
 */
public class GoodsAttrVO implements Serializable {
    /**
     * 商品属性名称
     */
    private String attrName;

    /**
     * 商品属性值
     */
    private String valueName;

    /**
     * 商品属性code
     */
    private String valueCode;

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }
}
