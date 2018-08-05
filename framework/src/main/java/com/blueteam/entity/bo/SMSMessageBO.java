package com.blueteam.entity.bo;

import java.util.List;
import java.util.Map;

/**
 * 短信消息
 *
 * @author xiaojiang
 * @create 2018-02-01  14:23
 */
public class SMSMessageBO{
    /**  类型  1 电话通知  */
    public static final  int STATE_PHONE = 1;
    /**  类型 2 微信商家版通知 */
    public static final int STATE_WECHAT_VENDOR = 2;
    /** 参数 */
    private Map<String,Object> params;
    /** 模板id */
    private String templateId;
    /**  类型  1 电话通知 2 微信商家版通知 */
    private Integer state;
    /**  */
    private String openId;
    /**  */
    private String phone;

    public static int getStatePhone() {
        return STATE_PHONE;
    }

    public static int getStateWechatVendor() {
        return STATE_WECHAT_VENDOR;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SMSMessageBO{" +
                "params=" + params +
                ", templateId='" + templateId + '\'' +
                ", state=" + state +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public SMSMessageBO() {
    }

    public SMSMessageBO(Map<String, Object> params, String templateId, Integer state, String openId, String phone) {
        this.params = params;
        this.templateId = templateId;
        this.state = state;
        this.openId = openId;
        this.phone = phone;
    }
    public SMSMessageBO(Integer state,Integer source) {
        //商家端的电话通知
        if (source.equals(STATE_PHONE)){
            this.setState(STATE_PHONE);
            switch (state){
                //C端付款
                case 1:
                    this.templateId = "SMS_123669013";
                    break;
                //C端退款
                case 2:
                    this.templateId = "SMS_123669012";
                    break;
                //商品审核成功
                case 3:
                    this.templateId = "SMS_123674019";
                    break;
                //商品审核失败
                case 4:
                    this.templateId = "SMS_123738804";
                    break;
                //结算成功
                case 5:
                    this.templateId = "SMS_123669016";
                    break;
                //C端评价
                case 6:
                    this.templateId = "SMS_123796208";
                    break;
                default:
                    break;
            }
        }
        //商家版的微信服务号通知
        if (source.equals(STATE_WECHAT_VENDOR)){
            this.setState(STATE_WECHAT_VENDOR);
            switch (state){
                // 客人评价通知
                case 1:
                    this.templateId = "Giuwe8cZM5UVWYoKHGL7xrV3ctOVlQ9yZb0nbjShJrc";
                    break;
                //  退款申请通知
                case 2:
                    this.templateId = "KZova3wW4amLur5QvNqEpKrRRMGUOd_2RVo1Bf5ag8c";
                    break;
                // 商品审核失败通知
                case 3:
                    this.templateId = "co_1vr-P41qR9RrJt5GozvPaGQfDRhVsNyJQ81EmDqs";
                    break;
                // 新订单通知
                case 4:
                    this.templateId = "jyfUuAnRA9H2FyYE5_SaFoM_kF1K5YaWndkYXiXA5Yw";
                    break;
                // 商品审核成功通知
                case 5:
                    this.templateId = "k4yVUT88kHTfd4eH12XqD1uoohcSAN5HtY2ZFPiJioo";
                    break;
                // 结算成功通知
                case 6:
                    this.templateId = "uTCsML6f1b6ZnosjAKcckuArmZQNmkCLs_w4sLJi5oo";
                    break;
                default:
                    break;
            }
        }


    }
}
