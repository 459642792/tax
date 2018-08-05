package com.blueteam.entity.dto;

/**
 * 品牌列表查询，数据传输对象
 * Created by  NastyNas on 2017/10/23.
 */
public class BrandSearchDTO {
    String brandName;
    Integer stateTag;
    String updateFrom;
    String updateTo;
    //时间排序标记0：降序 1：升序 默认情况降序
    Integer orderTag;

    public Integer getOrderTag() {
        return orderTag;
    }

    public void setOrderTag(Integer orderTag) {
        this.orderTag = orderTag;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getStateTag() {
        return stateTag;
    }

    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    public String getUpdateFrom() {
        return updateFrom;
    }

    public void setUpdateFrom(String updateFrom) {
        this.updateFrom = updateFrom;
    }

    public String getUpdateTo() {
        return updateTo;
    }

    public void setUpdateTo(String updateTo) {
        this.updateTo = updateTo;
    }
}
