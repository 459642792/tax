package com.blueteam.base.constant;

import com.blueteam.base.util.DiamondUtil;

import static com.blueteam.base.constant.DiamondConstant.GLOBAL_DATA;

public class Constants {

    public static final int DEFAULT_ONE_PAGE_INDEX = 1;
    public static final int DEFAULT_PAGE_SIZE = 20;


    /**
     * 消息resultKEY
     */
    public static final String MESSAGE_RESULT_KEY = "method_result";

    /**
     * 用户ID
     */
    public static final String USER_ID = "METHOD_USERID";

    /**
     * 扩展ID
     */
    public static final String EXTEND_ID = "METHOD_EXTENDID";

    /**
     * 存放access_token 的 key
     */
    public static final String ACCESS_TOKEN = "access_token";

    public static final String WX_CONFIG = "config/wechat_config.txt"; //微信配置文件名

    public static final String VENDOR_QRCODE_ADDR = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token="; //商家二维码生成地址

    public static final String VENDOR_QRCODE_SAVE_PATH = "vendor_qrcode"; //商家二维码保存路径

    public static final String EXCEPTION_MESSAGE = "系统发生内部错误,请联系管理员"; //统一异常消息

    /**
     * 项目版本
     */
//    public static String EDITION = ReadFile.getValue("project.edition");
    public static String EDITION = DiamondUtil.getPre(GLOBAL_DATA, "project.edition");


    /**
     * API V2的站点地址
     */
//	public static final String API_V2_WEBSITE= "http://192.168.0.111:8009";
//    public static String API_V2_WEBSITE = ReadFile.getValue("apiv2.url");
    public static String API_V2_WEBSITE = DiamondUtil.getPre(GLOBAL_DATA, "apiv2.url");

    /**
     * APIS的网站接口地址
     */
//    public static String APIS_WEBSITE = ReadFile.getValue("project.apis.url");
//    public static String APIS_WEBSITE = DiamondUtil.getPre(GLOBAL_DATA, "project.apis.url");
    public static String APIS_WEBSITE = "http://192.168.0.166:8080/fjjhadmin";

    /**
     * 图片上传路径
     */
//    public static String IMAGE_UPLOAD_URL = ReadFile.getValue("project.upload.url");
    public static String IMAGE_UPLOAD_URL = DiamondUtil.getPre(GLOBAL_DATA, "project.upload.url");

    /**
     * socket路径
     */
//    public static String SOCKET_URL = ReadFile.getValue("project.socket.url");
//    public static String SOCKET_URL = DiamondUtil.getPre(GLOBAL_DATA, "project.socket.url");
    public static String SOCKET_URL = "http://192.168.0.166:8080/fjjhadmin";

    /**
     * 用户登录返回状态码
     *
     * @author libra
     */
    public class UserLoginStatusCodes {


        /**
         * 没有授权Token
         */
        public static final String NOT_AUTH_TOKEN = "1000";

        /**
         * 错误的授权Token
         */
        public static final String TOKEN_ERROR = "1001";

        /**
         * 登录过期
         */
        public static final String EXPIRE = "1002";

        /**
         * 错误的用户类型
         * 主要用于当前要求必须是运营商
         * 但是TOKEN解析出来不为运营商
         */
        public static final String ERROR_USER_TYPE = "1003";

        /**
         * 不完整的用户
         * 主要用于当前要求必须是运营商，必须具有运营商ID
         * 但是TOKEN解析出来不具有运营商ID
         */
        public static final String INCOMPLETE_USER = "1004";

        /**
         * 登录校验器校验错误
         */
        public static final String VERIFY_ERROR = "1005";


    }

    /**
     * SQL排序方式
     *
     * @author libra
     */
    public static class SQLOrder {
        /**
         * 升序
         */
        public static final String ASC = "ASC";

        /**
         * 降序
         */
        public static final String DESC = "DESC";

        /**
         * 是否包含
         *
         * @param value
         * @return
         */
        public static boolean Contain(String value) {

            if (ASC.equalsIgnoreCase(value))
                return true;

            if (DESC.equalsIgnoreCase(value))
                return true;


            return false;
        }
    }

    /**
     * 商家排序字段
     *
     * @author libra
     */
    public static class VendorsOrderField {
        /**
         * 入驻时间
         */
        public static final String ENTER_DATE = "DATE";

        /**
         * 综合评分
         */
        public static final String SCORE = "SCORE";

        /**
         * 销量
         */
        public static final String SALES = "SALES";

        /**
         * 是否包含
         *
         * @param value
         * @return
         */
        public static boolean Contain(String value) {
            if (ENTER_DATE.equalsIgnoreCase(value))
                return true;

            if (SCORE.equalsIgnoreCase(value))
                return true;

            if (SALES.equalsIgnoreCase(value))
                return true;

            return false;
        }
    }


    /**
     * 登录标示存放的KEY
     * 用于将用户登录认证标示存放在请求Attribute
     */
    public static final String LOGINIDENTIFY_KEY = "LOGINIDENTIFY_KEY";


    /**
     * 验证码缓存key
     */
    public static final String VERIFICATION_CODE_KEY = "VERIFICATION_CODE";


    /**
     * 城市根节点的key
     */
    public static final String CITY_ROOT_CODE = "root";

    /**
     * 用户DataSource
     *
     * @author libra
     */
    public class UserInfoDataSource {

        /**
         * 网站
         */
        public static final String WEBSITE = "website";

        /**
         * 手机客户端
         */
        public static final String PHONE_CLIENT = "phoneClient";

    }

    /**
     * 领取优惠券
     */
    public static final String DISCOUNT_COUPON_CODE = "DISCOUNT_COUPON_CODE";


    /**
     * 商家创建优惠券（商家发布）
     */
    public static final String CREATE_COUPON_CODE_VENDOR = "CREATE_COUPON_CODE";

    /**
     * 平台创建优惠券
     */
    public static final String CREATE_COUPON_CODE_VENDOR_ADMIN = "CREATE_COUPON_CODE_VENDOR_ADMIN";

    /**
     * 运营商发布优惠券
     */
    public static final String CREATE_COUPON_CODE_VENDOR_CARRIER = "CREATE_COUPON_CODE_VENDOR_CARRIER";

    /**
     * 图片是商家的评论图片
     */
    public static final String COMMENT_CODE_VENDOR = "COMMENT_CODE_VENDOR";

    /**
     * 用户评论商家
     */
    public static final String COMMENTVENDOR_CODE_VENDOR = "COMMENTVENDOR_CODE_VENDOR";
    /**
     * 商家评论用户
     */
    public static final String COMMENTADMIN_CODE_VENDOR = "COMMENTADMIN_CODE_VENDOR";

    /**
     *
     */
    public static final String COMMENTVENDOR_CODE_ORDER = "COMMENTVENDOR_CODE_ORDER";

    /**
     * C端商家详情页面顶部轮播图片(头图)
     */
    public static final String CREATE_VENDOR_DETAIL_TOPIMAGE = "CREATE_VENDOR_DETAIL_TOPIMAGE";

    /**
     * 十大品牌轮播图
     */
    public static final String CREATE_Sdpp_IMAGELIST = "CREATE_Sdpp_IMAGELIST";

    /**
     * C端商家详情页面顶部图片(门面)
     */
    public static final String CREATE_VENDOR_DETAIL_FACADE = "CREATE_VENDOR_DETAIL_FACADE";

    /**
     * C端商家详情页面顶部图片(环境)
     */
    public static final String CREATE_VENDOR_DETAIL_AMBIENT = "CREATE_VENDOR_DETAIL_AMBIENT";

    /**
     * C端商家详情页面顶部图片(单品)
     */
    public static final String CREATE_VENDOR_DETAIL_PRODUCT = "CREATE_VENDOR_DETAIL_PRODUCT";

    /**
     * C端商家详情页面顶部全景图片
     */
    public static final String CREATE_VENDOR_GENERALVIEW = "CREATE_VENDOR_GENERALVIEW";

    /**
     * C端首页顶部轮播
     */
    public static final String CREATE_VENDOR_LISTIMAGE = "CREATE_VENDOR_LISTIMAGE";

    /**
     * 首页 平台管理员发布的轮播广告
     */
    public static final String CREATE_MANAGER_LISTIMAGE = "CREATE_MANAGER_LISTIMAGE";

}
