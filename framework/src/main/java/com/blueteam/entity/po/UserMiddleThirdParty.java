package com.blueteam.entity.po;

public class UserMiddleThirdParty {


    private Integer id;

    private Integer userInfoId;

    private Integer thirdPartyId;

    private Integer middleStatus;

    public UserMiddleThirdParty(Integer id, Integer userInfoId, Integer thirdPartyId, Integer middleStatus) {
        this.id = id;
        this.userInfoId = userInfoId;
        this.thirdPartyId = thirdPartyId;
        this.middleStatus = middleStatus;
    }

    public UserMiddleThirdParty() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Integer userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Integer getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(Integer thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public Integer getMiddleStatus() {
        return middleStatus;
    }

    public void setMiddleStatus(Integer middleStatus) {
        this.middleStatus = middleStatus;
    }
}