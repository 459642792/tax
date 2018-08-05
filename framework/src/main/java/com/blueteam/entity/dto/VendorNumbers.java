package com.blueteam.entity.dto;

/**
 * 商户数量
 *
 * @author libra
 */
public class VendorNumbers {

    /**
     * 入驻的商户数量
     */
    private int enter;

    /**
     * 认证的商户数量
     */
    private int authentication;

    public int getEnter() {
        return enter;
    }

    public void setEnter(int enter) {
        this.enter = enter;
    }

    public int getAuthentication() {
        return authentication;
    }

    public void setAuthentication(int authentication) {
        this.authentication = authentication;
    }


}
