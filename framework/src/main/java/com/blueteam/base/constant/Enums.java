package com.blueteam.base.constant;

public class Enums {

    /**
     * 用户类型枚举
     *
     * @author libra
     */
    public class UserType {

        /**
         * 运营商
         */
        public final static int Carriers = 1;

        /**
         * 商家
         */
        public final static int Vendor = 2;

        /**
         * 普通用户
         */
        public final static int Every = 4;

        /**
         * 管理员
         */
        public final static int Admin = 8;

    }

    /**
     * 用户第三方类型
     *
     * @author libra
     */
    public class ThirdPartyUserInfo {

        /**
         * 微信
         */
        public final static int WEI_XIN = 1;
        /**
         * 支付宝
         */
        public final static int ZHI_FU_BAO = 2;

        /**
         * qq
         */
        public final static int QQ = 3;

        /**
         * 其它
         */
        public final static int OTHER = 9;

    }

    /**
     * 枚举接口
     *
     * @author libra
     */
    public interface Enum {

        /**
         * 获取枚举值
         *
         * @return
         */
        int getValue();

        /**
         * 获取枚举描述
         *
         * @return
         */
        String getDescription();

        /**
         * 是否包含 指定枚举值
         *
         * @param em
         * @return
         */
        boolean HasFlag(Enum em);
    }

    public static class FlagEnumHelper {
        public static boolean HasFlag(Enum em, Enum e) {
            return (em.getValue() & e.getValue()) != 0;
        }

        public static boolean HasFlag(int a, int b) {
            return (a & b) != 0;
        }
    }

    /**
     * 用户类型枚举
     *
     * @author libra
     */
    public enum EnumUserType implements Enum {

        Carriers(1, "运营商"), Vendor(2, "商家"), Every(4, "普通用户"), Admin(8, "管理员");


        private EnumUserType(int v, String desc) {
            this.value = v;
            this.description = desc;
        }

        /**
         * 获取枚举名称
         *
         * @return
         */

        public String getDescription() {
            return this.description;
        }

        /**
         * 获取枚举值
         *
         * @return
         */

        public int getValue() {
            return this.value;
        }

        /**
         * 是否包含
         */

        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }


        private int value;
        private String description;
    }


    public enum EnumUserType3 implements Enum {
        Sdpp(10, "十大品牌"), Ssdpp(20, "三十大品牌"), Noraml(30, "普通品牌");

        private EnumUserType3(int v, String desc) {
            this.value = v;
            this.description = desc;
        }

        /**
         * 获取枚举名称
         *
         * @return
         */

        public String getDescription() {
            return this.description;
        }

        /**
         * 获取枚举值
         *
         * @return
         */

        public int getValue() {
            return this.value;
        }

        /**
         * 是否包含
         */

        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }

        private int value;
        private String description;
    }

    /**
     * 图片枚举
     *
     * @author Eric Lee , 2017-02-20 11:52
     */
    public enum ImageInfoType implements Enum {
        Vendor_Dianzhao(1, "店招图片"), //没有用到，店招图存放在VendorInfo表中的Image字段中
        Vendor_Top(2, "头图"),
        Vendor_Facade(3, "门脸图"),
        Vendor_Shop(4, "店内环境图"),
        Vendor_Sample(5, "单品图"),
        Vendor_Quanjing(6, "全景图片");

        private int value;
        private String description;

        private ImageInfoType(int value, String description) {
            this.value = value;
            this.description = description;
        }


        public int getValue() {
            return this.value;
        }


        public String getDescription() {
            return this.description;
        }


        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }
    }

    /**
     * 商家状态
     *
     * @author Eric Lee , 2017-02-21 11:00
     */
    public enum VendorStatus implements Enum {
        Uncheck(10, "待审核"),
        CheckAccess(20, "审核通过"),
        CheckNotAccess(30, "审核未通过");

        private int value;
        private String description;

        private VendorStatus(int value, String description) {
            this.value = value;
            this.description = description;
        }


        public int getValue() {
            return this.value;
        }


        public String getDescription() {
            return this.description;
        }


        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }
    }


    /**
     * 用户是否锁定枚举
     *
     * @author libra
     */
    public enum EnumUserIsLock implements Enum {

        Yes(1, "锁定"), No(0, "未锁定");


        private EnumUserIsLock(int v, String desc) {
            this.value = v;
            this.description = desc;
        }

        /**
         * 获取枚举名称
         *
         * @return
         */

        public String getDescription() {
            return this.description;
        }

        /**
         * 获取枚举值
         *
         * @return
         */

        public int getValue() {
            return this.value;
        }

        /**
         * 是否包含
         */

        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }


        private int value;
        private String description;
    }

    /**
     * 方法的功能描述:TODO  枚举属性接口
     *
     * @param
     * @author xiaojiang 2017/7/27 18:57
     * @methodName值选项 （0 输入框 1 下拉框  2文本框  8 固定值   9 图片
     * @return
     * @since 1.4.0
     */
    public enum GoodsAttributeEnum implements Enum {
        INPUT(0, "输入框"),
        SELECT(1, "下拉框"),
        TEXTBOX(2, "文本框"),
        FIXED_VALUE(8, "固定值"),
        IMAGE(9, "图片");

        private int value;
        private String description;

        private GoodsAttributeEnum(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return this.value;
        }


        public String getDescription() {
            return this.description;
        }


        public boolean HasFlag(Enum em) {
            return FlagEnumHelper.HasFlag(this, em);
        }
    }

    /**
     * 登录失败，错误状态码以及错误信息
     */
    public enum LoginState implements Enum {

        NOT_AUTH_TOKEN(1000, "没有找到登录凭证"),
        ERROR_TOKEN(1001, "错误的登录凭证"),
        EXPIRE_TOKEN(1002, "登录已过期"),
        ERROR_USER_TYPE(1003, "错误的用户类型"),
        ERROR_USER_EXTEND(1004, "用户拓展信息错误"),
        VERIFY_ERROR(1005, "没有找到登录凭证");

        int state;
        String description;

        LoginState(int state, String description) {
            this.state = state;
            this.description = description;
        }

        @Override
        public int getValue() {
            return state;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public boolean HasFlag(Enum em) {
            return false;
        }
    }
}
