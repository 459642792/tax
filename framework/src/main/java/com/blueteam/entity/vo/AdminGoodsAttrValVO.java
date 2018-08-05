package com.blueteam.entity.vo;

/**
 * 后台管理-商品新增 属性值vo
 * Created by  NastyNas on 2017/10/27.
 */
public class AdminGoodsAttrValVO {
    Integer selectedFlag;
    String attrValueCode;
    String attrValueName;

    public Integer getSelectedFlag() {
        return selectedFlag;
    }

    public void setSelectedFlag(Integer selectedFlag) {
        this.selectedFlag = selectedFlag;
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
}
