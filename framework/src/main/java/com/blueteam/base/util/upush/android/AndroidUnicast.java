package com.blueteam.base.util.upush.android;


import com.blueteam.base.util.upush.AndroidNotification;

/**
 * 方法的功能描述:TODO 单播推送类
 *
 * @param
 * @author xiaojiang 2017/5/19 16:26
 * @methodName
 * @return
 * @since 1.3.0
 */
public class AndroidUnicast extends AndroidNotification {
    public AndroidUnicast(String appkey, String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "unicast");
    }

    public void setDeviceToken(String token) throws Exception {
        setPredefinedKeyValue("device_tokens", token);
    }

}