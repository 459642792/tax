package com.blueteam.base.constant;

/**
 * Created by  NastyNas on 2017/11/2.
 */
public interface FieldValidateConstants {
    //商品新增
    String GOODS_ADD_DTO = "GOODS_ADD_DTO";
    //下架商品编辑
    String GOODS_EDIT_DTO = "GOODS_EDIT_DTO";
    //上架商品编辑
    String GOODS_EDIT_LIMITED_DTO = "GOODS_EDIT_LIMITED_DTO";

    //属性新增
    String ATTR_ADD_DTO = "ATTR_ADD_DTO";
    //属性编辑
    String ATTR_EDIT_DTO = "ATTR_EDIT_DTO";
    //属性删除
    String ATTR_REMOVE_DTO = "ATTR_REMOVE_DTO";

    //主品牌新增
    String MAIN_BRAND_ADD_DTO = "MAIN_BRAND_ADD_DTO";
    //主品牌编辑
    String MAIN_BRAND_EDIT_DTO = "MAIN_BRAND_EDIT_DTO";
    //主品牌删除
    String MAIN_BRAND_REMOVE_DTO = "MAIN_BRAND_REMOVE_DTO";
    //品牌新增
    String BRAND_ADD_DTO = "BRAND_ADD_DTO";
    //品牌编辑
    String BRAND_EDIT_DTO = "BRAND_EDIT_DTO";
    //品牌删除
    String BRAND_REMOVE_DTO = "BRAND_REMOVE_DTO";
    //品牌启用
    String BRAND_ENABLE_DTO = "BRAND_ENABLE_DTO";
    //品牌禁用
    String BRAND_DISABLE_DTO = "BRAND_DISABLE_DTO";

    enum ValidateTypeEnum {
        /*商品*/
        GOODS_ADD_DTO("GOODS_ADD_DTO", "商品新增"),
        GOODS_EDIT_DTO("GOODS_EDIT_DTO", "下架商品编辑"),
        GOODS_EDIT_LIMITED_DTO("GOODS_EDIT_LIMITED_DTO", "上架商品编辑"),

        /*属性*/
        ATTR_ADD_DTO("ATTR_ADD_DTO", "属性新增"),
        ATTR_EDIT_DTO("ATTR_EDIT_DTO", "属性编辑"),
        ATTR_REMOVE_DTO("ATTR_REMOVE_DTO", "属性删除"),

        /*品牌*/
        MAIN_BRAND_ADD_DTO("MAIN_BRAND_ADD_DTO", "主品牌新增"),
        MAIN_BRAND_EDIT_DTO("MAIN_BRAND_EDIT_DTO", "主品牌编辑"),
        MAIN_BRAND_REMOVE_DTO("MAIN_BRAND_REMOVE_DTO", "主品牌删除"),
        BRAND_ADD_DTO("BRAND_ADD_DTO", "品牌新增"),
        BRAND_EDIT_DTO("BRAND_EDIT_DTO", "品牌编辑"),
        BRAND_REMOVE_DTO("BRAND_REMOVE_DTO", "品牌删除"),
        BRAND_ENABLE_DTO("BRAND_ENABLE_DTO", "品牌启用"),
        BRAND_DISABLE_DTO("BRAND_DISABLE_DTO", "品牌禁用");


        ValidateTypeEnum(String validateType, String validateTypeName) {
            this.validateType = validateType;
            this.validateTypeName = validateTypeName;
        }

        String validateType;
        String validateTypeName;

        public String getValidateType() {
            return validateType;
        }

        public void setValidateType(String validateType) {
            this.validateType = validateType;
        }

        public String getValidateTypeName() {
            return validateTypeName;
        }

        public void setValidateTypeName(String validateTypeName) {
            this.validateTypeName = validateTypeName;
        }

    }

}
