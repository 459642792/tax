package com.blueteam.entity.vo;

/**
 * 商品审核VO
 * Created by  NastyNas on 2017/11/8.
 */
public class AdminVerifyVO {
    String verifyBarCode;
    Integer verifyCount;
    String latestUpdateTime;
    String verifyGoodsName;

    public String getVerifyBarCode() {
        return verifyBarCode;
    }

    public void setVerifyBarCode(String verifyBarCode) {
        this.verifyBarCode = verifyBarCode;
    }

    public Integer getVerifyCount() {
        return verifyCount;
    }

    public void setVerifyCount(Integer verifyCount) {
        this.verifyCount = verifyCount;
    }

    public String getLatestUpdateTime() {
        return latestUpdateTime;
    }

    public void setLatestUpdateTime(String latestUpdateTime) {
        this.latestUpdateTime = latestUpdateTime;
    }

    public String getVerifyGoodsName() {
        return verifyGoodsName;
    }

    public void setVerifyGoodsName(String verifyGoodsName) {
        this.verifyGoodsName = verifyGoodsName;
    }
}
