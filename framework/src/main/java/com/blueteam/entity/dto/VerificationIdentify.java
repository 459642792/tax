package com.blueteam.entity.dto;

/**
 * 验证码认证
 *
 * @author libra
 */
public class VerificationIdentify {
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 验证码
     */
    private String code;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
