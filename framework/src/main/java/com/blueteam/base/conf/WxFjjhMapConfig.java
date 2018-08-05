package com.blueteam.base.conf;

import com.blueteam.base.util.DiamondUtil;
import org.springframework.stereotype.Component;

import static com.blueteam.base.constant.DiamondConstant.WX_DATA;


/**
 * @author xiaojiang
 * @create 2017-08-15  18:18
 */
@Component
public class WxFjjhMapConfig {


    //    @Value("#{wxProperties.wx_fjjh_appid}")
    private String appid = DiamondUtil.getPre(WX_DATA, "wx_fjjh_appid");

    //    @Value("#{wxProperties.wx_fjjh_appsecret}")
    private String appsecret = DiamondUtil.getPre(WX_DATA, "wx_fjjh_appsecret");


    /**
     * 用于接口访问加密的key
     */
    //@Value("#{wxProperties.wx_fjjh_apiaeskey}")
    private String apiaeskey = DiamondUtil.getPre(WX_DATA, "wx_fjjh_apiaeskey");

    /**
     * 微信回调URL
     */
//    @Value("#{wxProperties.wx_fjjh_login_callback_url}")
    private String wxLoginCallbackUrl = DiamondUtil.getPre(WX_DATA, "wx_fjjh_login_callback_url");

    public String getWxLoginCallbackUrl() {
        return wxLoginCallbackUrl;
    }

    public String getApiaeskey() {
        return apiaeskey;
    }

    public String getAppid() {
        return this.appid;
    }

    public String getAppsecret() {
        return this.appsecret;
    }


}
