package com.blueteam.wineshop.mapper;

import com.blueteam.entity.dto.MessageSearch;
import com.blueteam.entity.dto.VendorMessage;
import com.blueteam.entity.po.Message;
import com.blueteam.entity.dto.PageResult;

import java.util.List;

/**
 * Created by libra on 2017/5/15.
 * <p>
 * 消息Mapper
 */
public interface MessageMapper {

    /**
     * 添加消息
     *
     * @param msg
     * @return
     */
    int insertMsg(Message msg);

    /**
     * 根据用户信息和搜索的类型编码获取消息
     *
     * @param search
     * @return
     */
    PageResult<List<Message>> getMessagesByTypeCodes(MessageSearch search);

    /**
     * 根据条件获取我的未读消息数量
     *
     * @param search
     * @return
     */
    int getMeNotReadMessageCount(MessageSearch search);

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
     * @param messageSearch
     * @return
     */
    int readMessageByID(MessageSearch messageSearch);

    /**
     * 根据TypeCode阅读消息
     *
     * @param messageSearch
     * @return
     */
    int readMessageByTypeCodes(MessageSearch messageSearch);
}
