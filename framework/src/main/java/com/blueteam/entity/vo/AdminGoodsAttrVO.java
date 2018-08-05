package com.blueteam.entity.vo;

import java.util.List;

/**
 * 后台管理-商品新增，属性vo
 * Created by  NastyNas on 2017/10/27.
 */
public class AdminGoodsAttrVO {
    String attrCode;
    String parentAttrCode;
    String attrName;
    Integer attrType;
    String necessaryTag;
    //input类型属性值
    String attrValueName;
    //select类型属性值
    List<AdminGoodsAttrValVO> attrValVOList;

    public String getAttrValueName() {
        return attrValueName;
    }

    public void setAttrValueName(String attrValueName) {
        this.attrValueName = attrValueName;
    }

    public String getAttrCode() {
        return attrCode;
    }

    public void setAttrCode(String attrCode) {
        this.attrCode = attrCode;
    }

    public String getParentAttrCode() {
        return parentAttrCode;
    }

    public void setParentAttrCode(String parentAttrCode) {
        this.parentAttrCode = parentAttrCode;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    public String getNecessaryTag() {
        return necessaryTag;
    }

    public void setNecessaryTag(String necessaryTag) {
        this.necessaryTag = necessaryTag;
    }

    public List<AdminGoodsAttrValVO> getAttrValVOList() {
        return attrValVOList;
    }

    public void setAttrValVOList(List<AdminGoodsAttrValVO> attrValVOList) {
        this.attrValVOList = attrValVOList;
    }
}
