package com.blueteam.base.conf;

import com.blueteam.base.util.DiamondUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import static com.blueteam.base.constant.DiamondConstant.MQ_DATA;


/**
 * Created by libra on 2017/5/16.
 * <p/>
 * 队列配置
 */
@Component
public class MqConfig {

    private static final Logger logger = LogManager.getLogger(MqConfig.class);

    /**
     * topicName
     */
    private String topicName = DiamondUtil.getPre(MQ_DATA, "mq.topicName");

    /**
     * URL
     */
    private String url = DiamondUtil.getPre(MQ_DATA, "mq.url");

    /**
     * queueName
     */
    private String queueName = DiamondUtil.getPre(MQ_DATA, "mq.queueName");

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
