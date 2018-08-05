package com.blueteam.wineshop.service;

import com.blueteam.entity.dto.MessageSearch;
import com.blueteam.entity.dto.VendorMessage;
import com.blueteam.entity.po.Message;
import com.blueteam.entity.dto.PageResult;

import java.util.List;

/**
 * Created by libra on 2017/5/17.
 * <p>
 * 消息服务
 */
public interface MessageService {

    /**
     * 添加消息
     *
     * @param message 消息实体
     * @return 添加成功的消息ID，失败则为0
     */
    long insert(Message message);


    /**
     * 获取未读的消息数量
     *
     * @param search
     * @return
     */
    int getNotReadingMessageCount(MessageSearch search);

    /**
     * 根据用户信息和搜索的类型编码获取消息
     *
     * @param search
     * @return
     */
    PageResult<List<Message>> getMessagesByTypeCodes(MessageSearch search);


    /**
     * 按消息类型分组，并获取每个分组的最新一条消息，已经每组消息类型的未读消息数量
     *
     * @param search
     * @return
     */
    List<VendorMessage> getVendorMessageStatistics(MessageSearch search);

    /**
     * 根据消息类型获取消息列表
     *
     * @return
     */
    PageResult<List<Message>> getVendorMessagesByTypeCodes(MessageSearch search);

    /**
     * 获取管理平台的未读消息
     *
     * @param search
     * @return
     */
    List<Message> getAdminMessages(MessageSearch search);

    /**
     * 根据消息ID阅读消息
     *
     * @param recipients 阅读人ID
     * @param messageId  阅读消息ID
     * @return
     */
    int readMessageByID(int recipients, long messageId);

    /**
     * 根据TypeCode阅读消息
     *
     * @param vendorId   商家ID
     * @param carriersID 运营商ID
     * @param recipients 阅读人
     * @param group      阅读分组
     * @return
     */
    int readMessageByTypeCodes(int vendorId, int carriersID, int recipients, String group);
}
