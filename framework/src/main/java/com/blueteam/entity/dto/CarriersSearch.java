package com.blueteam.entity.dto;

/**
 * Created by libra on 2017/4/11.
 * 运营商搜索接口
 */
public class CarriersSearch extends BasePageSearch {
    /**
     * 管理区域
     */
    private String managerArea;

    /**
     * 负责人手机号码
     */
    private String mobilePhone;

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getManagerArea() {
        return managerArea;
    }

    public void setManagerArea(String managerArea) {
        this.managerArea = managerArea;
    }
}
