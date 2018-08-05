package com.blueteam.entity.po;

import com.blueteam.entity.dto.City;

import java.io.Serializable;
import java.util.List;

/**
 * 发现实体数据结构
 *
 * @author Marx
 */
public class Discover implements Serializable {

    public List<Vendordiscover> getLstcoverVendor() {
        return lstcoverVendor;
    }

    public void setLstcoverVendor(List<Vendordiscover> lstcoverVendor) {
        this.lstcoverVendor = lstcoverVendor;
    }

    public static final int Audit_1 = 1;//待审核

    public static final int Audit_2 = 2;//已审核

    public static final int Audit_3 = 3;//已拒绝

    public static final String FACE_IMAGE = "FACEIMAGE";//封面图片

    public static final String DISCOVER_LINK_CITYS = "DISCOVER_LINK_CITY";//封面图片

    public static final String ADMIN_TYPE = "ADMIN_TYPE";//管理员

    public static final String CARRIERS_TYPE = "CARRIERS_TYPE";//

    private List<String> lstCitys;
    /**
     * 数据结构主键
     */
    private Integer Id;

    /**
     * 数据结构标题
     */
    private String Title;

    /**
     * 数据结构类型
     */
    private String Type;

    /**
     * 数据结构标签(标签采取";"形式分割)
     */
    private String Label;

    /**
     * 数据结构发布人信息
     */
    private String IsUser;

    /**
     * 数据结构状态信息
     */
    private Integer Status;

    /**
     * 数据结构浏览数
     */
    private Integer Visits;

    /**
     * 数据结构是否显示
     */
    private String IsShow;

    /**
     * 数据结构创建时间
     */
    private String CreateDate;

    /**
     * 数据结构创建人信息
     */
    private String CreateBy;

    /**
     * 数据结构内容
     */
    private String Detail;

    /**
     * 是否是头条
     */
    private String HandLine;

    /**
     * 数据结构修改时间
     */
    private String UpdateDate;

    /**
     * 数据结构创建人信息
     */
    private String UpdateBy;

    /**
     * 拒绝理由
     */
    private String Reason;

    /**
     * 商家分类
     */
    private String VendorType;

    /**
     * 状态判断
     *
     * @return
     */
    private String state;

    /**
     * 推荐字段(Y:代表推荐：N：代表不推荐)
     *
     * @return
     */
    private String Groom;


    /**
     * 扩展的userID
     */
    private Integer userId;

    /**
     * 运营商ID
     */
    private Integer carriersId;


    /**
     * 显示区域
     */
    private City city;

    public Integer getCarriersId() {
        return carriersId;
    }

    public void setCarriersId(Integer carriersId) {
        this.carriersId = carriersId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGroom() {
        return Groom;
    }

    public void setGroom(String groom) {
        Groom = groom;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVendorType() {
        return VendorType;
    }

    public void setVendorType(String vendorType) {
        VendorType = vendorType;
    }

    private List<Vendordiscover> lstcoverVendor;

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    /**
     * 对象数组
     */
    private List<String> lstVendor;

    /**
     * 封面图片
     */
    private List<String> lstFaceImage;


    public List<String> getLstCitys() {
        return lstCitys;
    }

    public void setLstCitys(List<String> lstCitys) {
        this.lstCitys = lstCitys;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getIsUser() {
        return IsUser;
    }

    public void setIsUser(String isUser) {
        IsUser = isUser;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer status) {
        Status = status;
    }

    public Integer getVisits() {
        return Visits;
    }

    public void setVisits(Integer visits) {
        Visits = visits;
    }

    public String getIsShow() {
        return IsShow;
    }

    public void setIsShow(String isShow) {
        IsShow = isShow;
    }

    public String getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(String createDate) {
        CreateDate = createDate;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String updateDate) {
        UpdateDate = updateDate;
    }

    public String getCreateBy() {
        return CreateBy;
    }

    public void setCreateBy(String createBy) {
        CreateBy = createBy;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public String getHandLine() {
        return HandLine;
    }

    public void setHandLine(String handLine) {
        HandLine = handLine;
    }


    public String getUpdateBy() {
        return UpdateBy;
    }

    public void setUpdateBy(String updateBy) {
        UpdateBy = updateBy;
    }

    public List<String> getLstVendor() {
        return lstVendor;
    }

    public void setLstVendor(List<String> lstVendor) {
        this.lstVendor = lstVendor;
    }

    public List<String> getLstFaceImage() {
        return lstFaceImage;
    }

    public void setLstFaceImage(List<String> lstFaceImage) {
        this.lstFaceImage = lstFaceImage;
    }
}
