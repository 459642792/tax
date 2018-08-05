package com.blueteam.base.conf;

import com.blueteam.base.util.DiamondUtil;
import org.springframework.stereotype.Component;

import static com.blueteam.base.constant.DiamondConstant.WX_DATA;


/**
 * @author Binary Wang
 */
@Component
public class WxMpConfig {
    //    @Value("#{wxProperties.wx_token}")
    private String token = DiamondUtil.getPre(WX_DATA, "wx_token");

    //    @Value("#{wxProperties.wx_appid}")
    private String appid = DiamondUtil.getPre(WX_DATA, "wx_appid");

    //    @Value("#{wxProperties.wx_appsecret}")
    private String appsecret = DiamondUtil.getPre(WX_DATA, "wx_appsecret");

    //    @Value("#{wxProperties.wx_aeskey}")
    private String aesKey = DiamondUtil.getPre(WX_DATA, "wx_aeskey");

    /**
     * 用于接口访问加密的key
     */
//    @Value("#{wxProperties.wx_apiaeskey}")
    private String apiaeskey = DiamondUtil.getPre(WX_DATA, "wx_apiaeskey");

    /**
     * 微信回调URL
     */
//    @Value("#{wxProperties.wx_login_callback_url}")
    private String wxLoginCallbackUrl = DiamondUtil.getPre(WX_DATA, "wx_login_callback_url");

    public String getWxLoginCallbackUrl() {
        return wxLoginCallbackUrl;
    }

    public String getApiaeskey() {
        return apiaeskey;
    }

    public String getToken() {
        return this.token;
    }

    public String getAppid() {
        return this.appid;
    }

    public String getAppsecret() {
        return this.appsecret;
    }

    public String getAesKey() {
        return this.aesKey;
    }

}
