package com.blueteam.entity.dto;

/**
 * 商品待审核列表页dto
 * Created by  NastyNas on 2017/11/8.
 */
public class GoodsVerifySearchDTO extends BasePageSearch {
    //按时间排序：0-降序  1-升序
    Integer timeOrderTag;
    //按提交数排序:0-降序 1-升序
    Integer submitOrderTag;

    public Integer getTimeOrderTag() {
        return timeOrderTag;
    }

    public void setTimeOrderTag(Integer timeOrderTag) {
        this.timeOrderTag = timeOrderTag;
    }

    public Integer getSubmitOrderTag() {
        return submitOrderTag;
    }

    public void setSubmitOrderTag(Integer submitOrderTag) {
        this.submitOrderTag = submitOrderTag;
    }
}
