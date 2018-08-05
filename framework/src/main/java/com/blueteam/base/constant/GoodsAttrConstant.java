package com.blueteam.base.constant;

import com.blueteam.base.lang.RList;

import java.util.List;

/**
 * Created by  NastyNas on 2017/10/30.
 */
public final class GoodsAttrConstant {

    //酒精度父类属性值
    public static final String ALCOHOL_ATTR_CODE = "100";
    //包装父类属性值
    public static final String PACKAGE_ATTR_CODE = "110";
    //规格父类属性值
    public static final String CONTENT_ATTR_CODE = "120";
    //瓶数父类属性值
    public static final String BOTTLE_ATTR_CODE = "130";

    //白酒窖藏年份
    public static final String SPIRIT_YEAR_ATTR_CODE = "207";
    //葡萄酒采摘年份
    public static final String GRAPE_YEAR_ATTR_CODE = "301";
    //葡萄酒采摘年份
    public static final String GRAPE_LEVEL_ATTR_CODE = "310";
    //啤酒麦芽度
    public static final String BEER_MALT_ATTR_CODE = "507";

    //SELECT类型属性，前台未选择时编码值
    public static final String UNSELECT_ATTR_VALUE_CODE = "-1";


    //商品列表搜索项父类属性列表
    public static final List<String> SEARCH_ATTR_LIST = RList.asList(PACKAGE_ATTR_CODE, ALCOHOL_ATTR_CODE);
    //商品列表页展示属性的父属性列表
    public static final List<String> SHOW_ATTR_LIST = RList.asList(PACKAGE_ATTR_CODE, ALCOHOL_ATTR_CODE, CONTENT_ATTR_CODE);


    //基础属性,必选
    public static final int BASE_LIST_TYPE = 1;
    //特有属性，非必选
    public static final int SPECIAL_LIST_TYPE = 0;


    public enum BaseAttrEnum {
        ALCOHOL_ATTR_CODE("100"),
        PACKAGE_ATTR_CODE("110"),
        CONTENT_ATTR_CODE("120"),
        BOTTLE_ATTR_CODE("130");
        String attrCode;

        BaseAttrEnum(String attrCode) {
            this.attrCode = attrCode;
        }

        public String getAttrCode() {
            return attrCode;
        }

        public void setAttrCode(String attrCode) {
            this.attrCode = attrCode;
        }

        boolean contains(String attrCode) {
            boolean flag = false;
            for (BaseAttrEnum baseAttrEnum : BaseAttrEnum.values()) {
                if (baseAttrEnum.getAttrCode().equals(attrCode)) {
                    flag = true;
                    break;
                }
            }
            return flag;
        }
    }
}
