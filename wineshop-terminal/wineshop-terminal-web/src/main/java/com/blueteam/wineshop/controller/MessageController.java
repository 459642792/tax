package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.*;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.upush.UPushUtil;
import com.blueteam.entity.dto.*;
import com.blueteam.entity.po.Message;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.wineshop.base.spring.websocket.MessageWebSocketHandler;
import com.blueteam.wineshop.service.MessageService;
import com.blueteam.wineshop.service.WeixinService;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by libra on 2017/5/23.
 * <p>
 * 消息控制器
 * <p>
 * 需要登录
 */

@RestController
@RequestMapping("/api/message")
public class MessageController extends BaseController {

    @Autowired
    private WeixinService wxMpService;

    /**
     * 消息默认分页数
     */
    private static final int MESSAGE_DEFAULT_PAGESIZE = 20;

    @Autowired
    private MessageService service;

    @Autowired
    private MessageWebSocketHandler handler;

    private static Logger logger = LogManager.getLogger(MessageController.class);


    /**
     * 发送消息
     * 这里都没有做接口校验，唉，没办法，要速度开发得嘛
     * 领导要求
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult sendMessage(String json) {
        handler.sendMessageToUser(json);
        return BaseResult.success();
    }

    /**
     * 发送微信模板消息
     * 这里都没有做接口校验，唉，没办法，要速度开发得嘛
     * 领导要求
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/sendWxMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult sendWxMessage(String json) throws Exception {

//        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
//        templateMessage.setToUser("oBX7104anplmxJ6fgNAHVrX9zJKc");
//        templateMessage.setTemplateId("GBetbcbXnJ6B3YPaLQosVHcxbVsAgv2M8fLKI-i4Y_Y");
//
//        templateMessage.getData().add(new WxMpTemplateData("first.DATA","你好"));
//        templateMessage.getData().add(new WxMpTemplateData("keyword1.DATA", "你好1"));
//        templateMessage.getData().add(new WxMpTemplateData("keyword2.DATA", "你好2"));
//        templateMessage.getData().add(new WxMpTemplateData("keyword3.DATA", "你好3"));
//        templateMessage.getData().add(new WxMpTemplateData("keyword4.DATA", "你好4"));
//        templateMessage.getData().add(new WxMpTemplateData("keyword5.DATA", "你好5"));
//        templateMessage.getData().add(new WxMpTemplateData("remark.DATA", "你好6"));
        // wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        String responseContent = wxMpService.post("https://api.weixin.qq.com/cgi-bin/message/template/send", json);
        WxMpMassSendResult result = WxMpMassSendResult.fromJson(responseContent);
        BaseResult baseResult = BaseResult.success();
        baseResult.setMessage(responseContent);
        return baseResult;
    }

    /**
     * 推送友盟
     * 这里都没有做接口校验，唉，没办法，要速度开发得嘛
     * 领导要求
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "/sendUmengMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult sendUmengMessage(String json) {
        Message message = JsonUtil.deserialize(json, Message.class);
        if (message == null)
            return BaseResult.error("序列化错误");

        if (StringUtil.IsNullOrEmpty(message.getDevice_tokens()))
            return BaseResult.error("没有设备号");

        if (!message.getDevice_tokens().startsWith(Device.ANDROID + ":") && !message.getDevice_tokens().startsWith(Device.IOS + ":"))
            return BaseResult.error("错误的设备号 " + message.getDevice_tokens());

        try {
            Map map = null;
            if (message.getDevice_tokens().startsWith(Device.ANDROID + ":"))
                map = UPushUtil.sendANDROIDUnicast(message.getDevice_tokens().replace(Device.ANDROID + ":", ""), message.getTitle(), message.getContent());
            if (message.getDevice_tokens().startsWith(Device.IOS + ":"))
                map = UPushUtil.sendIOSUnicast(message.getDevice_tokens().replace(Device.IOS + ":", ""), message.getContent());

            if (map == null)
                return BaseResult.error("发送失败");

            String result = JsonUtil.serialize(map);
            BaseResult result1 = BaseResult.success();
            result1.setMessage(result);
            return result1;
        } catch (Exception e) {
            logger.error(ExceptionUtil.stackTraceString(e));
            return BaseResult.error("发生错误");
        }
    }


    /**
     * 获取我的未读消息数量
     *
     * @param codes 消息编码，多个中间以逗号分隔
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/getNotReadingMessageCount", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getNotReadingMessageCount(String codes) {
        MessageSearch search = getUserMessageSearch();
        if (!StringUtil.IsNullOrEmpty(codes)) {
            String[] codeArray = codes.split("\\,");
            List<String> codeList = new ArrayList<>(codeArray.length);
            search.setSearchTypes(codeList);
            for (int i = 0; i < codeArray.length; i++)
                codeList.add(codeArray[i]);
        }

        int count = service.getNotReadingMessageCount(search);
        return ApiResult.success(count);
    }


    /**
     * 获取指定分组消息的未读信息
     *
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/getNotReadingMessageCountByGroups", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getNotReadingMessageCountByGroups(String groups) {
        MessageSearch search = getUserMessageSearch();
        fillMessageTypesByGroup(search, groups);

        int count = service.getNotReadingMessageCount(search);
        return ApiResult.success(count);
    }

    /**
     * 根据分组获取消息
     *
     * @param groups
     * @param pageIndex index
     * @param pageSize  size
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/getMessagesByGroup", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getMessagesByGroup(String groups, Integer pageIndex, Integer pageSize) {
        MessageSearch search = getUserMessageSearch();
        fillMessageTypesByGroup(search, groups);
        setPageer(search, pageIndex, pageSize);

        PageResult<List<Message>> list = service.getMessagesByTypeCodes(search);

        return ApiResult.success(list.getList(), list.getCount());
    }


    /**
     * 按消息类型分组，并获取每个分组的最新一条消息，已经每组消息类型的未读消息数量
     *
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/getVendorMessageStatistics", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getVendorMessageStatistics() {
        MessageSearch search = getUserMessageSearch();
        fillMessageTypesByGroup(search, MessageTypeCodeGroups.VENDOR_COMMENT + "," + MessageTypeCodeGroups.VENDOR_SETTLEMENT + "," + MessageTypeCodeGroups.VENDOR_SYSTEM + "," + MessageTypeCodeGroups.VENDOR_TRANSACTION);
        List<VendorMessage> list = service.getVendorMessageStatistics(search);
        return ApiResult.success(list);
    }

    /**
     * 根据消息类型获取消息列表
     *
     * @return
     */
    @VendorApiLogin
    @RequestMapping(value = "/getVendorMessagesByTypeCodes", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getVendorMessagesByTypeCodes(String group, Integer pageIndex, Integer pageSize) {
        MessageSearch search = getUserMessageSearch();
        fillMessageTypesByGroup(search, group);
        setPageer(search, pageIndex, pageSize);
        PageResult<List<Message>> list = service.getVendorMessagesByTypeCodes(search);
        return ApiResult.success(list.getList(), list.getCount());
    }

    /**
     * 获取管理平台的未读消息
     *
     * @return
     */
    @AdminApiLogin
    @RequestMapping(value = "/getAdminMessages", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getAdminMessages() {
        MessageSearch search = getUserMessageSearch();
        List<Message> list = service.getAdminMessages(search);
        return ApiResult.success(list, list.size());
    }


    /**
     * 根据消息ID阅读消息
     *
     * @param messageId 阅读消息ID
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/readMessageByID", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult readMessageByID(long messageId) {
        if (messageId <= 0)
            return BaseResult.error("参数错误");

        int count = service.readMessageByID(super.getCurrentUserID(), messageId);
        return BaseResult.success();
    }

    /**
     * 根据TypeCode阅读消息
     *
     * @param group 阅读分组
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/readMessageByTypeCodes", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult readMessageByTypeCodes(String group) {
        if (StringUtil.IsNullOrEmpty(group))
            return BaseResult.error("参数错误");

        UserIdentify userIdentify = super.getIdentify();
        int vendorId = 0;
        int carriresId = 0;
        if (super.isUserType(userIdentify, Enums.UserType.Vendor))
            vendorId = userIdentify.getExtendId();
        if (super.isUserType(userIdentify, Enums.UserType.Carriers))
            carriresId = userIdentify.getExtendId();

        int count = service.readMessageByTypeCodes(vendorId, carriresId, super.getCurrentUserID(), group);
        return BaseResult.success();
    }

    /**
     * 根据TypeCode阅读消息
     *
     * @return
     */
    @ApiLogin
    @RequestMapping(value = "/readMeMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult readMeMessage() {
        UserIdentify userIdentify = super.getIdentify();
        int vendorId = 0;
        int carriresId = 0;
        if (super.isUserType(userIdentify, Enums.UserType.Vendor))
            vendorId = userIdentify.getExtendId();
        if (super.isUserType(userIdentify, Enums.UserType.Carriers))
            carriresId = userIdentify.getExtendId();

        int count = service.readMessageByTypeCodes(vendorId, carriresId, super.getCurrentUserID(), null);
        return BaseResult.success();
    }

    private void setPageer(MessageSearch search, Integer pageIndex, Integer pageSize) {
        pageIndex = pageIndex == null || pageIndex <= 0 ? 1 : pageIndex;
        pageSize = pageSize == null || pageSize <= 0 ? MESSAGE_DEFAULT_PAGESIZE : pageSize;
        search.setPageIndex(pageIndex);
        search.setPageSize(pageSize);
    }


    private void fillMessageTypesByGroup(MessageSearch search, String groups) {
        if (!StringUtil.IsNullOrEmpty(groups)) {
            String[] codeArray = groups.split("\\,");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < codeArray.length; i++) {
                if (!MessageTypeCodeGroups.MESSAGE_GROUPS.containsKey(codeArray[i]))
                    continue;
                list.addAll(MessageTypeCodeGroups.MESSAGE_GROUPS.get(codeArray[i]));
            }
            search.setSearchTypes(list);
        }
    }


    private MessageSearch getUserMessageSearch() {
        UserIdentify userIdentify = super.getIdentify();
        MessageSearch search = new MessageSearch();
        if (super.isUserType(userIdentify, Enums.UserType.Admin)) {
            search.setReceivingUserTypes(Enums.UserType.Admin);
            return search;
        } else if (super.isUserType(userIdentify, Enums.UserType.Carriers)) {//运营商
            search.setCarriersID(userIdentify.getExtendId());
        } else if (super.isUserType(userIdentify, Enums.UserType.Vendor)) {//商家
            search.setVendorID(userIdentify.getExtendId());
        } else {//普通用户
            search.setRecipients(userIdentify.getUserId());
        }


        return search;
    }
}
