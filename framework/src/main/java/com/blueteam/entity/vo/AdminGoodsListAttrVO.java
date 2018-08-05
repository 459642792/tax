package com.blueteam.entity.vo;

/**
 * 后台订单管理 商品列表属性VO
 * Created by  NastyNas on 2018/1/10.
 */
public class AdminGoodsListAttrVO {
    //属性编码
    String attrCode;
    //属性展示名称
    String attrValueShowName;

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getAttrValueShowName() {
        return attrValueShowName;
    }

    public void setAttrValueShowName(String attrValueShowName) {
        this.attrValueShowName = attrValueShowName;
    }
}
