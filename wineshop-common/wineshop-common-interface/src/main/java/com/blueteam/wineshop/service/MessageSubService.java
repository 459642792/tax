package com.blueteam.wineshop.service;

import com.blueteam.entity.bo.SMSMessageBO;
import com.blueteam.entity.dto.BaseResult;
import com.blueteam.entity.po.MessageSubjectPO;
import com.blueteam.entity.po.OrderPO;

import java.util.Map;

/**
 * web消息吹里
 *
 * @author xiaojiang
 * @create 2018-01-25  14:23
 */
public interface MessageSubService {
    MessageSubjectPO sendMessageSubject(MessageSubjectPO messageSubject,Integer state);
    /**
     * 方法的功能描述: 获取总数
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/26 17:02
     *@modifier
     */
    BaseResult getUnreadMessageCount(Integer userId);
    /**
     * 方法的功能描述:获取未读消息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:18
     *@modifier
     */
    BaseResult getUnreadByCount(Integer userId,Integer userSource);
    /**
     * 方法的功能描述: 修改所有未读消息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:27
     *@modifier
     */
    BaseResult updateUnread(Integer userId,Integer userSource);
    /**
     * 方法的功能描述: 获取消息列表
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/29 14:19
     *@modifier
     */
    BaseResult listMessage(Integer userId,Integer userSource,Integer pageIndex,Integer pageSize);
    /**
     * 方法的功能描述: 发送商家消息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 14:31
     *@modifier
     */
    <T> void sendVendormessage(Integer state, T t,Map<String,Object> map);
    /**
     * 方法的功能描述: 发送用户消息
     *@methodName
      * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/2/1 16:18
     *@modifier
     */
    void  senUserMessage(Integer state,OrderPO order);
}
