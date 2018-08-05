package com.blueteam.base.util.upush.ios;


import com.blueteam.base.util.upush.IOSNotification;

/**
 * 方法的功能描述:TODO广播推送类
 *
 * @param
 * @author xiaojiang 2017/5/19 16:26
 * @methodName
 * @return
 * @since 1.3.0
 */
public class IOSBroadcast extends IOSNotification {
    public IOSBroadcast(String appkey, String appMasterSecret) throws Exception {
        setAppMasterSecret(appMasterSecret);
        setPredefinedKeyValue("appkey", appkey);
        this.setPredefinedKeyValue("type", "broadcast");

    }
}
