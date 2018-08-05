package com.blueteam.base.util.upush.android;


import com.blueteam.base.util.upush.AndroidNotification;

/**
 * 方法的功能描述:TODO 广播推送类
 *
 * @param
 * @author xiaojiang 2017/5/19 16:26
 * @methodName
 * @return
 * @since 1.3.0
 */
public class AndroidBroadcast extends AndroidNotification {
    public AndroidBroadcast(String appkey, String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");
    }
}
