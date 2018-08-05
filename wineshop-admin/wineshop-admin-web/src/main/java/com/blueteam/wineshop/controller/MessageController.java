package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.ApiResult;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.dto.MessageSearch;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.wineshop.service.MessageServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libra on 2017/5/23.
 * <p>
 * admin 消息控制
 * <p>
 * 需要登录
 */

@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private MessageServiceImpl service;

    @Autowired

    private static Logger logger = LogManager.getLogger(MessageController.class);

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
