package com.blueteam.entity.po;

import com.blueteam.base.constant.Enums;
import com.blueteam.entity.dto.City;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author Marx
 * <p>
 * VendorInfo.java
 * <p>
 * 2017年2月22日**@version 1.0
 */

/**
 * @author Administrator
 */
public class VendorInfo implements Serializable {
    //����ID
    private Integer Id;
    //�������
    private String Name;
    //����ƴ��
    private String CityCode;
    //���и���code
    private String Addr;
    //����code
    private String Opentime;
    //���б�־
    private String Telephone;
    //�޸�ʱ��
    private String Phone;
    //�޸���
    private Integer VisitCount;

    /*
     * 状态, 待审核 10， 审核通过 20， 审核未通过  30
     * */
    private Integer Status;

    private String Detail;

    private String RecommendProduct;

    private String AuditStatus;

    private String ContactPerson;

    private BigDecimal Rate;

    private String Image;
    /**
     * 主色调
     */
    private String imageTone;
    private Integer UserId;

    private String Label;

    private String Longitude;

    private String Latitude;

    private String PinpaiIds;

    private String CreateDate;

    private String CreateBy;

    private String UpdateDate;

    private String UpdateBy;

    private Integer ScoreCount;

    private String Lujin;

    private Integer PageSize;

    private Integer PageIndex;

    private Integer zonghe;

    private String Advantage;

    private Integer Count;

    private List<String> PpList;

    private BigDecimal outScore;

    private String quyuName;//显示详情页面需要的区域名称

    private String quyuStatus;//判断该区域有没有

    private String TradingArea;


    private String recommendStatus;//推荐标志

    public String getRecommendStatus() {
        return recommendStatus;
    }

    public void setRecommendStatus(String recommendStatus) {
        this.recommendStatus = recommendStatus;
    }

    public String getTradingArea() {
        return TradingArea;
    }

    public void setTradingArea(String tradingArea) {
        TradingArea = tradingArea;
    }

    public String getQuyuStatus() {
        return quyuStatus;
    }

    public void setQuyuStatus(String quyuStatus) {
        this.quyuStatus = quyuStatus;
    }

    public String getQuyuName() {
        return quyuName;
    }

    public void setQuyuName(String quyuName) {
        this.quyuName = quyuName;
    }

    //json格式记录某个字段的修改时间：{'字段1':'20170224120000','字段2':'20170224123040'} (yyyyMMddHHmmss)
    private String Modify;

    //新增属性：商家的全景图
    private String qjImage;

    public String getQjImage() {
        return qjImage;
    }

    public void setQjImage(String qjImage) {
        this.qjImage = qjImage;
    }

    public String getModify() {
        return Modify;
    }

    public void setModify(String modify) {
        Modify = modify;
    }

    public BigDecimal getOutScore() {
        return outScore;
    }

    public void setOutScore(BigDecimal outScore) {
        this.outScore = outScore;
    }

    public List<String> getPpList() {
        return PpList;
    }

    public void setPpList(List<String> ppList) {
        PpList = ppList;
    }

    /**
     * 法人姓名
     */
    private String legalPerson;
    /**
     * 法人身份证
     */
    private String legalPersonIdCard;

    /**
     * 法人手持身份证图片
     */
    private String idInHandImage;

    /**
     * 营业执照图片
     */
    private String businessLicenseImg;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 特许文件图片
     */
    private String licenseFileImg;

    /**
     * 认证人ID
     */
    private Integer authenticationAuditor;

    /**
     * 认证时间
     */
    private Date authenticatioDate;

    /**
     * 认证状态
     */
    private Enums.VendorStatus authenticationStatus;

    public Integer getAuthenticationStatusInt() {
        return authenticationStatusInt;
    }

    public void setAuthenticationStatusInt(Integer authenticationStatusInt) {
        this.authenticationStatusInt = authenticationStatusInt;
    }

    /**
     * 认证状态数字类型
     */
    private Integer authenticationStatusInt;

    /**
     * 审核认证理由
     */
    private String authenticationReason;


    /**
     * 交易数量
     */
    private int orderCount;
    /**
     * 交易金额
     */
    private double orderAmount;




    /**
     * 主营品牌字符串
     */
    private String pinpaiStr;

    /**
     * 运营商名称
     */
    private String carriersName;
    /**
     * 运营商ID
     */
    private Integer carriersID;


    private String provinceCode;

    private String provinceName;

    private String cityCodes;

    private String cityName;

    private String districtCode;

    private String districtName;

    private Integer isOpen;

    private Integer volume;//成交量

    private Integer pageViews;//浏览量

    private Integer salesAmount;//总销售额

    private Integer sendQrCodeStatus;//二维码贴纸状态


    public String getCarriersName() {
        return carriersName;
    }

    public void setCarriersName(String carriersName) {
        this.carriersName = carriersName;
    }

    public Integer getCarriersID() {
        return carriersID;
    }

    public void setCarriersID(Integer carriersID) {
        this.carriersID = carriersID;
    }

    public String getPinpaiStr() {
        return pinpaiStr;
    }

    public void setPinpaiStr(String pinpaiStr) {
        this.pinpaiStr = pinpaiStr;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }


    public String getAuthenticationReason() {
        return authenticationReason;
    }

    public void setAuthenticationReason(String authenticationReason) {
        this.authenticationReason = authenticationReason;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPersonIdCard() {
        return legalPersonIdCard;
    }

    public void setLegalPersonIdCard(String legalPersonIdCard) {
        this.legalPersonIdCard = legalPersonIdCard;
    }

    public String getIdInHandImage() {
        return idInHandImage;
    }

    public void setIdInHandImage(String idInHandImage) {
        this.idInHandImage = idInHandImage;
    }

    public String getBusinessLicenseImg() {
        return businessLicenseImg;
    }

    public void setBusinessLicenseImg(String businessLicenseImg) {
        this.businessLicenseImg = businessLicenseImg;
    }

    public String getLicenseFileImg() {
        return licenseFileImg;
    }

    public void setLicenseFileImg(String licenseFileImg) {
        this.licenseFileImg = licenseFileImg;
    }

    public Integer getAuthenticationAuditor() {
        return authenticationAuditor;
    }

    public void setAuthenticationAuditor(Integer authenticationAuditor) {
        this.authenticationAuditor = authenticationAuditor;
    }

    public Date getAuthenticatioDate() {
        return authenticatioDate;
    }

    public void setAuthenticatioDate(Date authenticatioDate) {
        this.authenticatioDate = authenticatioDate;
    }

    public Enums.VendorStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(Enums.VendorStatus authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }

    private List<CouponInfo> couponList;

    private List<ScoreInfo> scoreList;

    private String[] lstAdvantage;

    public String[] getLstAdvantage() {
        return lstAdvantage;
    }

    public void setLstAdvantage(String[] strings) {
        this.lstAdvantage = strings;
    }

    private Integer ForeignKey;

    public Integer getForeignKey() {
        return ForeignKey;
    }

    public void setForeignKey(Integer foreignKey) {
        ForeignKey = foreignKey;
    }

    public List<ScoreInfo> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<ScoreInfo> scoreList) {
        this.scoreList = scoreList;
    }

    public List<CouponInfo> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<CouponInfo> couponList) {
        this.couponList = couponList;
    }

    public Integer getCount() {
        return Count;
    }

    public void setCount(Integer count) {
        Count = count;
    }

    public String getAdvantage() {
        return Advantage;
    }

    public void setAdvantage(String advantage) {
        Advantage = advantage;
    }

    public Integer getZonghe() {
        return zonghe;
    }

    public void setZonghe(Integer zonghe) {
        this.zonghe = zonghe;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public Integer getPageIndex() {
        return PageIndex;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }

    public void setPageIndex(Integer pageIndex) {
        PageIndex = pageIndex;
    }


    public String getLujin() {
        return Lujin;
    }


    public Integer getScoreCount() {
        return ScoreCount;
    }

    public void setScoreCount(Integer scoreCount) {
        ScoreCount = scoreCount;
    }

    public void setLujin(String lujin) {
        Lujin = lujin;
    }

    public Integer getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getAddr() {
        return Addr;
    }

    public String getOpentime() {
        return Opentime;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getPhone() {
        return Phone;
    }

    public Integer getVisitCount() {
        return VisitCount;
    }

    /*
     * 状态, 待审核 10， 审核通过 20， 审核未通过  30
     * */
    public Integer getStatus() {
        return Status;
    }

    public String getDetail() {
        return Detail;
    }

    public String getRecommendProduct() {
        return RecommendProduct;
    }

    public String getAuditStatus() {
        return AuditStatus;
    }

    public String getContactPerson() {
        return ContactPerson;
    }

    public BigDecimal getRate() {
        return Rate;
    }

    public String getImage() {
        return Image;
    }

    public Integer getUserId() {
        return UserId;
    }

    public String getLabel() {
        return Label;
    }

    public String getLongitude() {
        return Longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getPinpaiIds() {
        return PinpaiIds;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setAddr(String addr) {
        Addr = addr;
    }

    public void setOpentime(String opentime) {
        Opentime = opentime;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public void setVisitCount(Integer visitCount) {
        VisitCount = visitCount;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public void setRecommendProduct(String recommendProduct) {
        RecommendProduct = recommendProduct;
    }

    public void setAuditStatus(String auditStatus) {
        AuditStatus = auditStatus;
    }

    public void setContactPerson(String contactPerson) {
        ContactPerson = contactPerson;
    }

    public void setRate(BigDecimal rate) {
        Rate = rate;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public void setPinpaiIds(String pinpaiIds) {
        PinpaiIds = pinpaiIds;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }


    //扩展字段
    /**
     * 城市
     */
    private City city;

    /**
     * 评分
     */
    private Double score;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 品牌文字
     */
    private String pinpais;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 短信验证码
     */
    private String code;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPinpais() {
        return pinpais;
    }

    public void setPinpais(String pinpais) {
        this.pinpais = pinpais;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getImageTone() {
        return imageTone;
    }

    public void setImageTone(String imageTone) {
        this.imageTone = imageTone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityCodes() {
        return cityCodes;
    }

    public void setCityCodes(String cityCodes) {
        this.cityCodes = cityCodes;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getPageViews() {
        return pageViews;
    }

    public void setPageViews(Integer pageViews) {
        this.pageViews = pageViews;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public Integer getSendQrCodeStatus() {
        return sendQrCodeStatus;
    }

    public void setSendQrCodeStatus(Integer sendQrCodeStatus) {
        this.sendQrCodeStatus = sendQrCodeStatus;
    }
}
