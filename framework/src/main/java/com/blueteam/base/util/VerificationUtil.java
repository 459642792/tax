package com.blueteam.base.util;

import com.blueteam.base.constant.Constants;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.dto.ApiV2Result;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.UserInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 校验util
 *
 * @author libra
 */
public class VerificationUtil {


    /**
     * 失效时间，单位分钟
     */
    private static final int EXPIRE_TIME = 43200;
    /**
     * JSON创建者
     */
    private static JsonGenerator jsonGenerator = null;
    /**
     * JSON
     */
    private static ObjectMapper objectMapper;

    /**
     * logger
     */
    private static Logger logger = LogManager.getLogger(VerificationUtil.class);


    //    private final static String TOEKN_KEY = "TOEKN_KEY";
    public final static String TOEKN_KEY = "qwer1234qwer1234";

    private static List<String> NOT_CHECK_IP = getNotChekcIps();

    private static List<String> getNotChekcIps() {
        List<String> list = new ArrayList<String>();
        list.add("192.168.0.111");
        list.add("192.168.0.127");
        list.add("192.168.0.48"); //Eric Lee
        return list;
    }

    static {
        objectMapper = new ObjectMapper();
        try {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 校验验证码
     *
     * @param session
     * @param phone   电话号码
     * @param code    验证码
     * @return true 表示输入正确
     */
    public static boolean VerificationCode(HttpSession session, String phone, String code) {
//		VerificationIdentify identify =  (VerificationIdentify)session.getAttribute(Constants.VERIFICATION_CODE_KEY);
//		if(identify == null || !phone.equals(identify.getPhone()) || !code.equals(identify.getCode()))
//			return false;
        if (ProjectUtil.isDebug())
            return true;

        String ip = NetUtil.getLocalIP();
        for (String notIp : NOT_CHECK_IP) {
            if (ip != null && ip.contains(notIp))
                return true;
        }

        String url = Constants.API_V2_WEBSITE + "/api/common/CheckSMSCode?mobile=" + phone + "&code=" + code;
        logger.info("开始请求验证短信验证码地址为:" + url);

        String jsonText = WeiXinUtil.httpRequest(url, "GET", null);

        logger.info("短信验证码验证完成，结果为:" + jsonText);

        ApiV2Result<Boolean> result = JsonUtil.deserialize(jsonText, ApiV2Result.class);
        if (result == null)
            return false;
        return result.isIsSucceed() && result.getData();
    }


    /**
     * 获取Token
     *
     * @param user     当前登录用户
     * @param curUType 当时以什么角色登录系统
     * @return
     */
    public static String getToken(UserInfo user, int curUType) {
        return getToken(user, 0, curUType);
    }

    /**
     * 获取Token
     *
     * @param user     当前登录用户
     * @param curUType 当时以什么角色登录系统
     * @return
     */
    public static String getToken(UserInfo user, int extendId, int curUType) {
        if (user == null)
            return null;

        UserIdentify identify = new UserIdentify();

        Date loginDate = new Date();
        identify.setLoginDate(loginDate);
        identify.setUserId(user.getId());
        identify.setUserName(user.getUsername());
        identify.setUserType(user.getUsertypes());
        identify.setExtendId(extendId);
        identify.setCurUType(curUType);
        identify.setTelephone(user.getTelephone());

        try {
            String json = objectMapper.writeValueAsString(identify);
            String token = AES.encrypt(TOEKN_KEY, json);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

        return null;
    }

    /**
     * 获取用户登录标示
     *
     * @param token
     * @return
     */
    public static UserIdentify getUserIdentify(String token) {
        if (token.isEmpty() || token.equals("undefined"))
            return null;
        UserIdentify identify;

        try {
            String json = AES.decrypt(TOEKN_KEY, token);
            identify = objectMapper.readValue(json, UserIdentify.class);
            return identify;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }

        return null;
    }

    /**
     * 监测是否失效
     *
     * @param identify
     * @return
     */
    public static boolean CheckExpire(UserIdentify identify) {
        if (identify == null)
            return true;
        Calendar cal = Calendar.getInstance();
        cal.setTime(identify.getLoginDate());

//		//如果是运营商 设置为5分钟失效
//		if(FlagEnumHelper.HasFlag(identify.getUserType(),UserType.Carriers))
//			cal.add(Calendar.MINUTE,5);
//		else
        cal.add(Calendar.MINUTE, EXPIRE_TIME);

        if (cal.getTime().getTime() > new Date().getTime())
            return false;
        return true;
    }
}
