package com.blueteam.wineshop.controller;

import com.blueteam.base.constant.ApiLogin;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.wineshop.service.MessageSubService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 获取消息相关
 *
 * @author xiaojiang
 * @create 2018-01-26  16:56
 */
@Controller
@RequestMapping("/messageSub")
public class MessageSubController extends  BaseController{

    @Resource(name="messageService")
    private MessageSubService messageSubService;
    /**
     * 方法的功能描述: 获取用户未读消息总数
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:32
     *@modifier
     */
    @ApiLogin
    @RequestMapping(value = "/getUnreadByCount", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult getUnreadByCount(@RequestParam("userSource") Integer userSource){
        return messageSubService.getUnreadByCount(this.getCurrentUserID(),userSource);
    }
    /**
     * 方法的功能描述: 获取用户未读消息总数
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:32
     *@modifier
     */
    @ApiLogin
    @RequestMapping(value = "/updateUnread", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult updateUnread(@RequestParam("userSource") Integer userSource){
        return messageSubService.updateUnread(this.getCurrentUserID(),userSource);
    }
    /**
     * 方法的功能描述: 获取用户未读消息总数
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:32
     *@modifier
     */
    @ApiLogin
    @RequestMapping(value = "/listMessage", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listMessage(@RequestParam("userSource") Integer userSource,@RequestParam("pageIndex") Integer pageIndex,
                                  @RequestParam("pageSize") Integer pageSize){
        return messageSubService.listMessage(this.getCurrentUserID(),userSource,pageIndex,pageSize);
    }
}
