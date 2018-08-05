package com.blueteam.entity.dto;

import com.blueteam.entity.po.CarriersInfo;

/**
 * 运营商登录Result
 *
 * @author libra
 */
public class CarriersLoginResult extends LoginResult {
    /**
     * 当前登录的运营商信息
     */
    private CarriersInfo carriers;

    public CarriersInfo getCarriers() {
        return carriers;
    }

    public void setCarriers(CarriersInfo carriers) {
        this.carriers = carriers;
    }


}
