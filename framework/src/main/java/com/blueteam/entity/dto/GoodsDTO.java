package com.blueteam.entity.dto;

import com.blueteam.base.util.FieldValidate;
import com.blueteam.entity.bo.BaseGoodsBO;
import com.blueteam.entity.po.GoodsVerifyInfoPO;

import java.util.List;

import static com.blueteam.base.constant.FieldValidateConstants.GOODS_ADD_DTO;
import static com.blueteam.base.constant.FieldValidateConstants.GOODS_EDIT_DTO;
import static com.blueteam.base.constant.FieldValidateConstants.GOODS_EDIT_LIMITED_DTO;

/**
 * 商品信息dto
 * Created by  NastyNas on 2017/10/28.
 */
public class GoodsDTO {
    /*页面传入数据  start*/

    //校验类别
    String validateType;
    //商品id
    @FieldValidate(notNullValidateTypes = {GOODS_EDIT_DTO, GOODS_EDIT_LIMITED_DTO})
    Long goodsId;
    //商品类别
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO})
    Integer goodsType;
    //商品名称
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO})
    String goodsName;
    //条形码
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO})
    String barCode;
    //主品牌id
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO})
    Integer mainBrandId;
    //品牌id
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO})
    Integer brandId;
    //基础属性,必选
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO})
    List<AttrDTO> baseAttrList;
    //特有属性,非必选,如果某属性未选择(-)或未填写，该属性的AttrDTO对象也得穿值，响应字段可以为空值
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO, GOODS_EDIT_LIMITED_DTO})
    List<AttrDTO> specialAttrList;
    //图片列表
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO, GOODS_EDIT_LIMITED_DTO})
    List<String> goodsPhotoList;
    //商品详情
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO, GOODS_EDIT_LIMITED_DTO})
    String goodsDesc;
    //建议零售价
    @FieldValidate(notNullValidateTypes = {GOODS_ADD_DTO, GOODS_EDIT_DTO, GOODS_EDIT_LIMITED_DTO})
    String suggestPrice;

    /*页面传入数据  end*/

    /*业务中临时数据存储 start*/

    //商品审核信息
    List<GoodsVerifyInfoPO> goodsVerifyPOList;
    //商品基础信息
    BaseGoodsBO baseGoodsBO;
    //商品状态
    Integer goodsState;

    /*业务中临时数据存储 end*/

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getMainBrandId() {
        return mainBrandId;
    }

    public void setMainBrandId(Integer mainBrandId) {
        this.mainBrandId = mainBrandId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public List<AttrDTO> getBaseAttrList() {
        return baseAttrList;
    }

    public void setBaseAttrList(List<AttrDTO> baseAttrList) {
        this.baseAttrList = baseAttrList;
    }

    public List<AttrDTO> getSpecialAttrList() {
        return specialAttrList;
    }

    public void setSpecialAttrList(List<AttrDTO> specialAttrList) {
        this.specialAttrList = specialAttrList;
    }

    public List<String> getGoodsPhotoList() {
        return goodsPhotoList;
    }

    public void setGoodsPhotoList(List<String> goodsPhotoList) {
        this.goodsPhotoList = goodsPhotoList;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public List<GoodsVerifyInfoPO> getGoodsVerifyPOList() {
        return goodsVerifyPOList;
    }

    public void setGoodsVerifyPOList(List<GoodsVerifyInfoPO> goodsVerifyPOList) {
        this.goodsVerifyPOList = goodsVerifyPOList;
    }

    public String getValidateType() {
        return validateType;
    }

    public void setValidateType(String validateType) {
        this.validateType = validateType;
    }

    public String getSuggestPrice() {
        return suggestPrice;
    }

    public void setSuggestPrice(String suggestPrice) {
        this.suggestPrice = suggestPrice;
    }

    public BaseGoodsBO getBaseGoodsBO() {
        return baseGoodsBO;
    }

    public void setBaseGoodsBO(BaseGoodsBO baseGoodsBO) {
        this.baseGoodsBO = baseGoodsBO;
    }


    public Integer getGoodsState() {
        return goodsState;
    }

    public void setGoodsState(Integer goodsState) {
        this.goodsState = goodsState;
    }

    @Override
    public String toString() {
        return "GoodsDTO{" +
                "validateType='" + validateType + '\'' +
                ", goodsId=" + goodsId +
                ", goodsType=" + goodsType +
                ", goodsName='" + goodsName + '\'' +
                ", barCode='" + barCode + '\'' +
                ", mainBrandId=" + mainBrandId +
                ", brandId=" + brandId +
                ", baseAttrList=" + baseAttrList +
                ", specialAttrList=" + specialAttrList +
                ", goodsPhotoList=" + goodsPhotoList +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", suggestPrice=" + suggestPrice +
                ", goodsVerifyPOList=" + goodsVerifyPOList +
                ", baseGoodsBO=" + baseGoodsBO +
                ", goodsState=" + goodsState +
                '}';
    }
}
