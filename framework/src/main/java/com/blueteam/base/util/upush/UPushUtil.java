package com.blueteam.base.util.upush;

import com.blueteam.base.util.upush.android.AndroidBroadcast;
import com.blueteam.base.util.upush.android.AndroidUnicast;
import com.blueteam.base.util.upush.ios.IOSBroadcast;
import com.blueteam.base.util.upush.ios.IOSUnicast;

import java.util.Map;

/**
 * 方法的功能描述:TODO 友盟推送常用参数
 *
 * @param
 * @author xiaojiang 2017/5/19 10:15
 * @methodName
 * @return
 * @since 1.3.0
 */
public class UPushUtil {
    /*  **/
    private static final String ANDROID_APP_APPKEY = "59112953f29d9835a20010e0";
    /*  **/
    private static final String ANDROID_APP_MASTER_SECRET = "0h2n3ol8hv17utgqnqfm39pbwn8pqtzh";
    /*  **/
    private static final String ANDROID_APP_UMENG_MESSAGE_SECRET = "8efc8576f268cbee6fe211effb0f7c3e";
    /*  **/
    private static final String IOS_APPKEY = "59191961aed17917ea0025f7";
    /*  **/
    private static final String IOS_APP_MASTER_SECRET = "s7e2bvaca70daaim7puzjbzy7mehj7vs";
    /*  **/
    private static final String IOS_UMENG_MESSAGE_SECRET = "";

    private static final PushClient client = new PushClient();

    /**
     * 方法的功能描述:TODO 单播推送 ios
     *
     * @param device_tokens/唯一值，body/内容
     * @return Map<String,Object>  		map.put("result","true") map.put("message","成功！");
     * @methodName
     * @author xiaojiang 2017/5/19 16:06
     * @since 1.3.0
     */
    public static Map<String, Object> sendIOSUnicast(String device_tokens, String body) throws Exception {
        IOSUnicast unicast = new IOSUnicast(IOS_APPKEY, IOS_APP_MASTER_SECRET);
        unicast.setDeviceToken(device_tokens);
        unicast.setAlert(body);
        unicast.setTestMode();
        unicast.setCustomizedField("test", "附近酒行");
        return client.send(unicast);
    }

    /**
     * 方法的功能描述:TODO 广播推送 ios
     *
     * @param body/内容
     * @return Map<String,Object>  		map.put("result","true") map.put("message","成功！");
     * @methodName
     * @author xiaojiang 2017/5/19 16:06
     * @since 1.3.0
     */
    public static Map<String, Object> sendIOSBroadcast(String body) throws Exception {
        IOSBroadcast broadcast = new IOSBroadcast(IOS_APPKEY, IOS_APP_MASTER_SECRET);
        broadcast.setAlert(body);
        broadcast.setBadge(0);
        broadcast.setSound("default");
        broadcast.setTestMode();
        broadcast.setCustomizedField("test", "附近酒行");
        return client.send(broadcast);
    }

    /**
     * 方法的功能描述:TODO 单播推送 android
     *
     * @param device_tokens/唯一值，title/通知标题 body/内容
     * @return Map<String,Object>  		map.put("result","true") map.put("message","成功！");
     * @methodName
     * @author xiaojiang 2017/5/19 16:06
     * @since 1.3.0
     */
    public static Map<String, Object> sendANDROIDUnicast(String device_tokens, String title, String body) throws Exception {
        AndroidUnicast unicast = new AndroidUnicast(ANDROID_APP_APPKEY, ANDROID_APP_MASTER_SECRET);
        unicast.setTicker("附近酒行");//通知栏提示文字
        unicast.setTitle(title);//通知标题
        unicast.setText(body);//通知文字描述
        unicast.setDeviceToken(device_tokens);
        unicast.goCustomAfterOpen("msg");// 点击"通知"的后续行为，默认为打开app。
        unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        unicast.setProductionMode();
        unicast.setExtraField("test", "附近酒行");
        return client.send(unicast);
    }

    /**
     * 方法的功能描述:TODO 广播推送 android
     *
     * @param body/内容 title/通知标题
     * @return Map<String,Object>  		map.put("result","true") map.put("message","成功！");
     * @methodName
     * @author xiaojiang 2017/5/19 16:06
     * @since 1.3.0
     */
    public static Map<String, Object> sendANDROIDBroadcast(String title, String body) throws Exception {
        AndroidBroadcast broadcast = new AndroidBroadcast(ANDROID_APP_APPKEY, ANDROID_APP_MASTER_SECRET);
        broadcast.setTicker("附近酒行");//通知栏提示文字
        broadcast.setTitle(title);//通知标题
        broadcast.setText(body);//通知文字描述
        broadcast.goAppAfterOpen();
        broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
        broadcast.setProductionMode();
        broadcast.setExtraField("test", "附近酒行");
        return client.send(broadcast);
    }

    public static void main(String[] args) {
        // TODO set your appkey and master secret here
        try {
            sendANDROIDUnicast("AohJk_udD_WnVmVwzw2ZYKzxC_Meya1gc_KexvUNJNt0", "不想上班", "不想上班不想上班");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
