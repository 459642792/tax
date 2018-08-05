package com.blueteam.base.util.weixin;

import com.blueteam.base.util.DiamondUtil;
import com.jfinal.weixin.sdk.api.AccessToken;
import com.jfinal.weixin.sdk.api.AccessTokenApi;
import com.jfinal.weixin.sdk.api.ApiConfig;
import com.jfinal.weixin.sdk.api.ApiConfigKit;

import static com.blueteam.base.constant.DiamondConstant.WX_DATA;

/**
 * 微信工具类
 *
 * @author Eric Lee 2017-03-06
 */
public class WechatUtils {

    /**
     * 获取Token
     *
     * @return
     */
    public static String getToken() {
        ApiConfig ac = new ApiConfig();
//        PropKit.use(Constants.WX_CONFIG);
//        ac.setToken(PropKit.get("token"));
//        ac.setAppId(PropKit.get("appId"));
//        ac.setAppSecret(PropKit.get("appSecret"));
//        ac.setToken(DiamondUtil.getPre(WX_DATA, "token"));
        ac.setToken("");
        ac.setAppId(DiamondUtil.getPre(WX_DATA, "appId"));
        ac.setAppSecret(DiamondUtil.getPre(WX_DATA, "appSecret"));

        ApiConfigKit.setThreadLocalApiConfig(ac);
        AccessToken at = AccessTokenApi.getAccessToken();
        return at.getAccessToken();
    }

    public static void main(String[] args) throws Exception {
        //System.out.println(getVendorQrcode(112));
    }
}
