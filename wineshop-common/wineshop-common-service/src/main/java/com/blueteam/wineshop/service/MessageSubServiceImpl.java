package com.blueteam.wineshop.service;

import com.alibaba.fastjson.JSON;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.lang.RStr;

import com.blueteam.base.util.aliyun.SmsUtil;
import com.blueteam.base.util.weixin.WeiXinUtil;
import com.blueteam.entity.bo.SMSMessageBO;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.MessageSubjectPO;
import com.blueteam.entity.po.MessageTemplatePO;
import com.blueteam.entity.po.OrderPO;
import com.blueteam.entity.vo.message.MessageVo;
import com.blueteam.wineshop.mapper.MessageSubMapper;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 消息处理
 *
 * @author xiaojiang
 * @create 2018-01-25  14:24
 */
@Service(value="messageService")
public class MessageSubServiceImpl implements MessageSubService {
    @Resource(name="messageSubMapper")
    private MessageSubMapper messageSubMapper;
    @Autowired
    private MessageWebSocket messageWebSocket;

    private static Map<String,MessageTemplatePO>  mmt = new HashMap<>();
    private static Logger logger = LogManager.getLogger(MessageSubServiceImpl.class);

    /**
     * 方法的功能描述: 保存messageSub
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/25 15:04
     *@modifier
     */
    @Override
    public   MessageSubjectPO sendMessageSubject(MessageSubjectPO messageSubject, Integer state){
        if (RStr.isNotEmpty(messageSubject) && RStr.isNotEmpty(messageSubject.getSubjectGenre())){
            MessageTemplatePO messageTemplate = mmt.get(messageSubject.getSubjectGenre());
            if (RStr.isEmpty(messageTemplate)){
                listMessageTemplatePO();
                messageTemplate = mmt.get(messageSubject.getSubjectGenre());
                if (RStr.isEmpty(messageTemplate)){
                    throw  new NullPointerException("暂时没有该"+messageSubject.getSubjectGenre()+"模板分类");
                }
            }
            //主题分类
            messageSubject.setSubjectType(messageTemplate.getTemplateType());
            //主题标题
            messageSubject.setSubjectTitle(messageTemplate.getTemplateTitle());
            //消息内容
            String templateSubject = messageTemplate.getTemplateSubject();
            //消息参数
            String templateArgument = messageTemplate.getTemplateArgument();
            String oldMessage = messageSubject.getSubjectContent();
            if (RStr.isNotEmpty(templateSubject) &&
                    RStr.isNotEmpty(templateArgument) &&
                    RStr.isNotEmpty(oldMessage) &&
                    templateSubject.contains(templateArgument) ){
                //组装消息
                messageSubject.setSubjectContent(templateSubject.replaceAll(templateArgument,oldMessage));
            }else{
                throw  new NullPointerException("消息参数为空");
            }
            messageSubject.setCreateBy("system");
            messageSubject.setUpdateBy("system");
            messageSubject.setSubjectState(MessageSubjectPO.SUBJECT_STATE_UNREAD);
            messageSubMapper.saveMessageSubject(messageSubject);
            if (state.equals(1)){
                messageWebSocket.sendMessageToUser(messageSubject);
            }
            return messageSubject;
        }else{
            throw  new NullPointerException("messageSubject数据为空");
        }
    }



    /**
     * 方法的功能描述: 根据分类获取总数
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/26 17:02
     *@modifier
     */
    @Override
    public BaseResult getUnreadMessageCount(Integer userId) {
        List<Map<String,Object>> list =  messageSubMapper.listUnreadMessageCount(userId);
        int totalCount = 0;
        if (RStr.isNotEmpty(list)){
            for (Map<String, Object> map : list) {
                int total = (Integer) map.get("total");
                totalCount+=total;
            }
        }
        return ApiResult.success(list,totalCount);
    }
    /**
     * 方法的功能描述: 获取未读消息总数
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:19
     *@modifier
     */
    @Override
    public BaseResult getUnreadByCount(Integer userId, Integer userSource) {
        int counts = messageSubMapper.getUnreadByCount(userId,userSource,MessageSubjectPO.SUBJECT_STATE_UNREAD);
        return ApiResult.success(counts);
    }
    /**
     * 方法的功能描述:修改所有未读消息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:28
     *@modifier
     */
    @Override
    public BaseResult updateUnread(Integer userId, Integer userSource) {
        messageSubMapper.updateUnread(userId,userSource);
        return ApiResult.success();
    }

    /**
     * 方法的功能描述: 获取消息列表
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:20
     *@modifier
     */
    @Override
    public BaseResult listMessage(Integer userId, Integer userSource, Integer pageIndex, Integer pageSize) {
        List<MessageVo> list = messageSubMapper.listMessage(userId,userSource,pageIndex,pageSize);
        int counts = messageSubMapper.getUnreadByCount(userId,userSource,null);
        return ApiResult.success(list,counts);
    }
    /**
     * 方法的功能描述: 商家端发送短信消息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/2 10:47
     *@modifier
     */
    @Override
    public  <T>  void sendVendormessage(Integer state,T t, Map<String, Object> map) {
        SMSMessageBO message = new SMSMessageBO(state,SMSMessageBO.STATE_PHONE);
        switch (state){
            //C端付款
            case 1:
                //C端退款
            case 2:
                //结算成功
            case 5:
                //C端评价
            case 6:
                Map<String,Object> vendorMap =  messageSubMapper.getVendorPhoneByVendorId((Integer) t);
                if (RStr.isNotEmpty(vendorMap)){
                    map.put("shopName",vendorMap.get("vendorName"));
                    message.setPhone(RStr.isNotEmpty(vendorMap.get("telephone")) ? vendorMap.get("telephone").toString() : null);
                }
                message.setParams(map);
                if (RStr.isNotEmpty(message.getPhone())){
                    sendSMSmessage(message);
                }
                break;
            //商品审核成功
            case 3:
                //商品审核失败
            case 4:
                List<Map<String,Object>> list = messageSubMapper.listVendorIdByBarCode((String)t);
                if (state.equals(4)){
                    map.put("barCode",t);
                }
                if (RStr.isNotEmpty(list)){
                    for (Map<String, Object> listMap : list) {
                        message.setPhone(RStr.isNotEmpty(listMap.get("telephone")) ? listMap.get("telephone").toString() : null);
                        message.setParams(map);
                        //循环发送短信 因为待审核模板有可能有几条数据
                        if (RStr.isNotEmpty(message.getPhone())){
                            sendSMSmessage(message);
                        }
                    }
                }
                break;
            default:
                break;
        }

    }

    /**
     * 方法的功能描述:  发送模板消息和短信消息
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 14:32
     *@modifier
     */
    public void sendSMSmessage(SMSMessageBO messageBO) {
        //短信
        if (messageBO.getState().equals(SMSMessageBO.STATE_PHONE)){
            SmsUtil.sendSms(messageBO);
        }
        //微信模板消息
        if (messageBO.getState().equals(SMSMessageBO.STATE_WECHAT_VENDOR)){
            sendWechetVendor(messageBO);
        }
    }
    /**
     * 方法的功能描述: 发送用户消息（短信） 和 消息通知
     *@methodName
     * @param: state 1 C端退款成功通知 2 C端订单签收通知 3 C端订单通知
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 15:50
     *@modifier
     */
    @Override
    public void senUserMessage(Integer state,OrderPO order) {
        if (RStr.isNotEmpty(order)){
            SMSMessageBO message = new SMSMessageBO();
            MessageSubjectPO messageSubjectPO = new MessageSubjectPO();
            String phone = messageSubMapper.getUserPhoneByOrderNo(order.getOrderNo());
            Map<String,Object> map = new HashMap<>();
            map.put("code",order.getOrderNo());
            message.setPhone(phone);
            message.setParams(map);
            message.setState(SMSMessageBO.STATE_PHONE);
            if(RStr.isNotEmpty(phone)){
                switch (state){
                    case 1:
                        message.setTemplateId("SMS_123669017");
                        messageSubjectPO.setSubjectContent("退款成功");
                        break;
                    case 2:
                        message.setTemplateId("SMS_123798147");
                        messageSubjectPO.setSubjectContent("订单签收");
                        break;
                    case 3:
                        message.setTemplateId("SMS_123798876");
                        messageSubjectPO.setSubjectContent("商家已接单");
                        break;
                    default:
                        break;
                }
                //发送
                sendSMSmessage(message);
            }
            messageSubjectPO.setSubjectGenre("order_message");
            messageSubjectPO.setUserId(order.getUserId());
            messageSubjectPO.setDetailedId(Long.parseLong(order.getOrderNo()));
            messageSubjectPO.setUserType(Enums.UserType.Every);
            messageSubjectPO.setMessageSource(MessageSubjectPO.messageSource.USER.getGenre());
            //暂时关闭websocket
            sendMessageSubject(messageSubjectPO,2);
        }

    }


    private  void listMessageTemplatePO(){
        List<MessageTemplatePO> list = messageSubMapper.listMessageTemplatePO();
        if (RStr.isNotEmpty(list)){
            for (MessageTemplatePO messageTemplate : list) {
                mmt.put(messageTemplate.getTemplateGenre(),messageTemplate);
            }
        }
    }
    /**
     * 方法的功能描述: 微信公众号商家版发送消息 发送失败不管
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 14:49
     *@modifier
     */
    private void sendWechetVendor(SMSMessageBO message){
        Map<String ,Object> messageMap = new HashMap<>();
        messageMap.put("touser",message.getOpenId());
        messageMap.put("template_id",message.getTemplateId());
        messageMap.put("data", JSON.toJSON(message.getParams()));
        Map<String, String> sParaTemp = new HashMap<String, String>();
        //附近酒行商家版 微信公众号信息
        sParaTemp.put("appid", "wx707d6334aaaa6b6b");
        sParaTemp.put("secret", "62c011e33d6c9a51c690b408d64003cc");
        sParaTemp.put("grant_type", "client_credential");
        Map<String, String> sPara = WeiXinUtil.paraFilter(sParaTemp);
        String prestr = WeiXinUtil.createLinkString(sPara);
        String result = WeiXinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/token", "GET", prestr);
        JSONObject jasonObject = JSONObject.fromObject(result);
        Map maps = (Map) jasonObject;
        WeiXinUtil.httpRequest("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+maps.get("access_token"), "POST", JSON.toJSON(messageMap).toString());
    }



}
