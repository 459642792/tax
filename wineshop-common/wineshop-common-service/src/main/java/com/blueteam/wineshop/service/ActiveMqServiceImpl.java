package com.blueteam.wineshop.service;

import com.blueteam.base.conf.MqConfig;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.entity.dto.SendMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;


/**
 * Created by clam on 2017/5/18.
 */
@Service
public class ActiveMqServiceImpl implements MqService {
    private Logger logger = LogManager.getLogger(ActiveMqServiceImpl.class);
    @Autowired
    private MqConfig config;
    private MessageProducer producer;
    private Session session;
    private volatile static boolean isInit = false;

    private synchronized void init() {
        try {
            if (isInit) return;
            ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(config.getUrl());
            Connection connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(config.getQueueName());
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            isInit = true;
        } catch (Exception ex) {
            logger.error(ExceptionUtil.stackTraceString(ex));
        }
    }

    public void sendMessage(SendMessage message) {
        if (!isInit) init();
        try {
            ObjectMessage msg = session.createObjectMessage();
            msg.setObject(message);
            producer.send(msg);
        } catch (Exception ex) {
            logger.error(ExceptionUtil.stackTraceString(ex));
        }

    }
}
