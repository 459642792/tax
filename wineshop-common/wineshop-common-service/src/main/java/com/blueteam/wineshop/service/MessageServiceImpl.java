package com.blueteam.wineshop.service;

import com.blueteam.base.constant.MessageTypeCodeGroups;
import com.blueteam.base.util.StringUtil;
import com.blueteam.entity.dto.MessageSearch;
import com.blueteam.entity.dto.VendorMessage;
import com.blueteam.entity.po.Message;
import com.blueteam.entity.dto.PageResult;
import com.blueteam.wineshop.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libra on 2017/5/17.
 * <p>
 * 消息服务实现类
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper dao;

    /**
     * 添加消息
     *
     * @param message 消息实体
     * @return
     */
    @Override
    public long insert(Message message) {
        int result = dao.insertMsg(message);
        if (result > 0)
            return message.getId();
        return 0;
    }


    /**
     * 获取未读的消息数量
     *
     * @param search
     * @return
     */
    @Override
    public int getNotReadingMessageCount(MessageSearch search) {
        int count = dao.getMeNotReadMessageCount(search);
        return count;
    }

    /**
     * 根据用户信息和搜索的类型编码获取消息
     *
     * @param search
     * @return
     */
    @Override
    public PageResult<List<Message>> getMessagesByTypeCodes(MessageSearch search) {
        PageResult<List<Message>> messages = dao.getMessagesByTypeCodes(search);
        if (messages == null)
            messages = new PageResult<>();

        if (messages.getList() == null)
            messages.setList(new ArrayList<Message>());

        return messages;
    }

    /**
     * 按消息类型分组，并获取每个分组的最新一条消息，已经每组消息类型的未读消息数量
     *
     * @param search
     * @return
     */
    @Override
    public List<VendorMessage> getVendorMessageStatistics(MessageSearch search) {
        List<VendorMessage> messages = dao.getVendorMessageStatistics(search);
        messages = messages == null ? new ArrayList<VendorMessage>() : messages;

        //交易信息
        VendorMessage transaction = new VendorMessage();
        transaction.setMessageGroup(MessageTypeCodeGroups.VENDOR_TRANSACTION);

        //结算信息
        VendorMessage settlement = new VendorMessage();
        settlement.setMessageGroup(MessageTypeCodeGroups.VENDOR_SETTLEMENT);
        //评价信息
        VendorMessage comment = new VendorMessage();
        comment.setMessageGroup(MessageTypeCodeGroups.VENDOR_COMMENT);

        //系统信息
        VendorMessage system = new VendorMessage();
        system.setMessageGroup(MessageTypeCodeGroups.VENDOR_SYSTEM);

        for (VendorMessage msg : messages) {
            if (MessageTypeCodeGroups.MESSAGE_GROUPS.get(MessageTypeCodeGroups.VENDOR_COMMENT).contains(msg.getTypeCode())) {
                comment.setNotReadCount(comment.getNotReadCount() + msg.getNotReadCount());
                comment.setLastMessage(msg.getLastMessage());
                comment.setSendingTime(msg.getSendingTime());
            } else if (MessageTypeCodeGroups.MESSAGE_GROUPS.get(MessageTypeCodeGroups.VENDOR_SETTLEMENT).contains(msg.getTypeCode())) {
                settlement.setNotReadCount(settlement.getNotReadCount() + msg.getNotReadCount());
                settlement.setLastMessage(msg.getLastMessage());
                settlement.setSendingTime(msg.getSendingTime());
            } else if (MessageTypeCodeGroups.MESSAGE_GROUPS.get(MessageTypeCodeGroups.VENDOR_TRANSACTION).contains(msg.getTypeCode())) {
                transaction.setNotReadCount(transaction.getNotReadCount() + msg.getNotReadCount());
                transaction.setLastMessage(msg.getLastMessage());
                transaction.setSendingTime(msg.getSendingTime());
            } else if (MessageTypeCodeGroups.MESSAGE_GROUPS.get(MessageTypeCodeGroups.VENDOR_SYSTEM).contains(msg.getTypeCode())) {
                system.setNotReadCount(system.getNotReadCount() + msg.getNotReadCount());
                system.setLastMessage(msg.getLastMessage());
                system.setSendingTime(msg.getSendingTime());
            }
        }

        List<VendorMessage> result = new ArrayList<>();
        result.add(transaction);
        result.add(settlement);
        result.add(comment);
        result.add(system);

        return result;
    }

    /**
     * 根据消息类型获取消息列表
     *
     * @return
     */
    @Override
    public PageResult<List<Message>> getVendorMessagesByTypeCodes(MessageSearch search) {
        PageResult<List<Message>> messages = dao.getVendorMessagesByTypeCodes(search);
        if (messages == null)
            messages = new PageResult<>();

        if (messages.getList() == null)
            messages.setList(new ArrayList<Message>());

        return messages;
    }

    /**
     * 获取管理平台的未读消息
     *
     * @param search
     * @return
     */
    public List<Message> getAdminMessages(MessageSearch search) {
        return dao.getAdminMessages(search);
    }


    /**
     * 根据消息ID阅读消息
     *
     * @param recipients 阅读人ID
     * @param messageId  阅读消息ID
     * @return
     */
    @Override
    public int readMessageByID(int recipients, long messageId) {
        MessageSearch messageSearch = new MessageSearch();
        messageSearch.setId(messageId);
        messageSearch.setCreateBy(recipients);
        messageSearch.setRecipients(recipients);
        return dao.readMessageByID(messageSearch);
    }

    /**
     * 根据TypeCode阅读消息
     *
     * @param vendorId   商家ID
     * @param carriersID 运营商ID
     * @param recipients 阅读人
     * @param group      阅读分组
     * @return
     */
    @Override
    public int readMessageByTypeCodes(int vendorId, int carriersID, int recipients, String group) {
        MessageSearch messageSearch = new MessageSearch();
        fillMessageTypesByGroup(messageSearch, group);
        messageSearch.setCreateBy(recipients);
        messageSearch.setRecipients(recipients);

        if (carriersID != 0)
            messageSearch.setCarriersID(carriersID);

        if (vendorId != 0)
            messageSearch.setVendorID(vendorId);

        return dao.readMessageByTypeCodes(messageSearch);
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
}
