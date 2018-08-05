
package com.blueteam.entity.po;

import java.util.Date;

/**
 * 用户收货地址表(TF_F_USER_ADDRESS)
 *
 * @author
 * @version 1.0.0 2018-01-04
 */
public class UserAddressPO implements java.io.Serializable {
    /**
     * 无效的
     */
    public static final int STATE_TAG_DISABLE = 0;
    /**
     * 有效的
     */
    public static final int STATE_TAG_VALID = 1;
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8274041746509320554L;
    /**
     * 主键
     */
    private Integer userAddressId;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机号
     */
    private String contactPhone;

    /**
     * 地址名称
     */
    private String addressName;

    /**
     * 地址详情
     */
    private String addressDesc;

    /**
     * 用户输入地址
     */
    private String inputAddress;

    /**
     * 结构化地址
     */
    private String formattedAddress;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 行政区编码
     */
    private String adCode;

    /**
     * 街区编码
     */
    private String townCode;

    /**
     * 状态标记：0-无效 1-有效
     */
    private Integer stateTag;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public UserAddressPO() {
    }

    public UserAddressPO(Integer userAddressId, Integer userId, String contactName, String contactPhone, String addressName, String addressDesc, String inputAddress, String formattedAddress, String longitude, String latitude, String cityCode, String adCode, String townCode, Integer stateTag, Date createTime, Date updateTime) {
        this.userAddressId = userAddressId;
        this.userId = userId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.addressName = addressName;
        this.addressDesc = addressDesc;
        this.inputAddress = inputAddress;
        this.formattedAddress = formattedAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.cityCode = cityCode;
        this.adCode = adCode;
        this.townCode = townCode;
        this.stateTag = stateTag;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserAddressPO{" +
                "userAddressId=" + userAddressId +
                ", userId=" + userId +
                ", contactName='" + contactName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", addressName='" + addressName + '\'' +
                ", addressDesc='" + addressDesc + '\'' +
                ", inputAddress='" + inputAddress + '\'' +
                ", formattedAddress='" + formattedAddress + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", adCode='" + adCode + '\'' +
                ", townCode='" + townCode + '\'' +
                ", stateTag=" + stateTag +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    /**
     * 获取用户id
     *
     * @return 用户id
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取联系人姓名
     *
     * @return 联系人姓名
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * 设置联系人姓名
     *
     * @param contactName 联系人姓名
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * 获取联系人手机号
     *
     * @return 联系人手机号
     */
    public String getContactPhone() {
        return this.contactPhone;
    }

    /**
     * 设置联系人手机号
     *
     * @param contactPhone 联系人手机号
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 获取地址名称
     *
     * @return 地址名称
     */
    public String getAddressName() {
        return this.addressName;
    }

    /**
     * 设置地址名称
     *
     * @param addressName 地址名称
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * 获取地址详情
     *
     * @return 地址详情
     */
    public String getAddressDesc() {
        return this.addressDesc;
    }

    /**
     * 设置地址详情
     *
     * @param addressDesc 地址详情
     */
    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    /**
     * 获取用户输入地址
     *
     * @return 用户输入地址
     */
    public String getInputAddress() {
        return this.inputAddress;
    }

    /**
     * 设置用户输入地址
     *
     * @param inputAddress 用户输入地址
     */
    public void setInputAddress(String inputAddress) {
        this.inputAddress = inputAddress;
    }

    /**
     * 获取结构化地址
     *
     * @return 结构化地址
     */
    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    /**
     * 设置结构化地址
     *
     * @param formattedAddress 结构化地址
     */
    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    /**
     * 获取经度
     *
     * @return 经度
     */
    public String getLongitude() {
        return this.longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取纬度
     *
     * @return 纬度
     */
    public String getLatitude() {
        return this.latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取城市编码
     *
     * @return 城市编码
     */
    public String getCityCode() {
        return this.cityCode;
    }

    /**
     * 设置城市编码
     *
     * @param cityCode 城市编码
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /**
     * 获取行政区编码
     *
     * @return 行政区编码
     */
    public String getadCode() {
        return this.adCode;
    }

    /**
     * 设置行政区编码
     *
     * @param adCode 行政区编码
     */
    public void setadCode(String adCode) {
        this.adCode = adCode;
    }

    /**
     * 获取街区编码
     *
     * @return 街区编码
     */
    public String getTownCode() {
        return this.townCode;
    }

    /**
     * 设置街区编码
     *
     * @param townCode 街区编码
     */
    public void setTownCode(String townCode) {
        this.townCode = townCode;
    }

    /**
     * 获取状态标记：0-无效 1-有效
     *
     * @return 状态标记
     */
    public Integer getStateTag() {
        return this.stateTag;
    }

    /**
     * 设置状态标记：0-无效 1-有效
     *
     * @param stateTag 状态标记：0-无效 1-有效
     */
    public void setStateTag(Integer stateTag) {
        this.stateTag = stateTag;
    }

    /**
     * 获取创建时间
     *
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}