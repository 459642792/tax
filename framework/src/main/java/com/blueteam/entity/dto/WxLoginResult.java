package com.blueteam.entity.dto;

/**
 * Created by clam on 2017/6/13.
 */
public class WxLoginResult {
    /**
     * 登录的token
     */
    private String token;

    /**
     * 登录的附加ID
     */
    private Integer extendId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExtendId() {
        return extendId;
    }

    public void setExtendId(Integer extendId) {
        this.extendId = extendId;
    }
}
