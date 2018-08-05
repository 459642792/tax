package com.blueteam.entity.po;

import java.util.Date;

public class ThirdPartyUserInfo {

    /***  绑定 */
    public static final int THIRD_PARTY_STATUS_BIND = 1;

    /***  解绑 */
    public static final int THIRD_PARTY_STATUS_UNBIND = 0;

    private Integer id;

    private String thirdPartyId;

    private String thirdPartyNickName;

    private String thirdPartyHeadImage;

    private String thirdPartyCity;

    private Integer userType;

    private Integer thirdPartyType;

    private Integer thirdPartyStatus;

    private String createBy;

    private Date createDate;

    private String updateBy;

    private Date updateDate;

    public ThirdPartyUserInfo(Integer id, String thirdPartyId, String thirdPartyNickName, String thirdPartyHeadImage, String thirdPartyCity, Integer userType, Integer thirdPartyType, Integer thirdPartyStatus, String createBy, Date createDate, String updateBy, Date updateDate) {
        this.id = id;
        this.thirdPartyId = thirdPartyId;
        this.thirdPartyNickName = thirdPartyNickName;
        this.thirdPartyHeadImage = thirdPartyHeadImage;
        this.thirdPartyCity = thirdPartyCity;
        this.userType = userType;
        this.thirdPartyType = thirdPartyType;
        this.thirdPartyStatus = thirdPartyStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
    }

    public ThirdPartyUserInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId == null ? null : thirdPartyId.trim();
    }

    public String getThirdPartyNickName() {
        return thirdPartyNickName;
    }

    public void setThirdPartyNickName(String thirdPartyNickName) {
        this.thirdPartyNickName = thirdPartyNickName == null ? null : thirdPartyNickName.trim();
    }

    public String getThirdPartyHeadImage() {
        return thirdPartyHeadImage;
    }

    public void setThirdPartyHeadImage(String thirdPartyHeadImage) {
        this.thirdPartyHeadImage = thirdPartyHeadImage == null ? null : thirdPartyHeadImage.trim();
    }

    public String getThirdPartyCity() {
        return thirdPartyCity;
    }

    public void setThirdPartyCity(String thirdPartyCity) {
        this.thirdPartyCity = thirdPartyCity == null ? null : thirdPartyCity.trim();
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getThirdPartyType() {
        return thirdPartyType;
    }

    public void setThirdPartyType(Integer thirdPartyType) {
        this.thirdPartyType = thirdPartyType;
    }

    public Integer getThirdPartyStatus() {
        return thirdPartyStatus;
    }

    public void setThirdPartyStatus(Integer thirdPartyStatus) {
        this.thirdPartyStatus = thirdPartyStatus;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}