package com.blueteam.wineshop.base.spring.aop.activemq.help;

import com.blueteam.base.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.HashMap;

/**
 * Created by  NastyNas on 2017/9/19.
 * <p>
 * 从push.xml中读取需要进行推送的Controller和Action
 */
@Component
public class MqPushConfigMgr implements InitializingBean {

    //持有所有需要装配的controller+action
    private HashMap<String, Integer> map = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(MqPushConfigMgr.class);
    //xml配置读取帮助类
    private Document document;
    private XPathFactory xPathFactory;

    /**
     * Spring bean 初始化之前执行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        //从push.xml中读取节点列表
        NodeList nodeList = readXmlNodeList("/config/push.xml");
        if (nodeList == null) return;
        //将配置以成员变量map形式保存在MqPushConfigMgr管理器中
        saveConfigAsMap(nodeList);
    }

    private void saveConfigAsMap(NodeList nodeList) {
        try {
            for (int i = 0; i < nodeList.getLength(); i++) {
                //需要推送的Controller类
                NodeList controller = evalXpath(nodeList.item(i), "./@controller");
                //需要推送的action方法
                NodeList action = evalXpath(nodeList.item(i), "./@action");
                if (controller == null || controller.getLength() <= 0 || action == null || action.getLength() <= 0)
                    continue;
                //用户类型默认值
                int userType = 0;
                NodeList nodeUserType = evalXpath(nodeList.item(i), "./@userType");
                try {
                    //不影响正常流程
                    if (nodeUserType != null && nodeUserType.getLength() > 0)
                        userType = Integer.parseInt(nodeUserType.item(0).getNodeValue());
                } catch (Exception ex) {
                    userType = 10000;
                }
                // key: controller/action  value:userType
                map.put(controller.item(0).getNodeValue() + "/" + action.item(0).getNodeValue(), userType);
            }
        } catch (Exception e) {
            logger.error("消息推送xml节点信息保存失败,失败原因:{}", ExceptionUtil.stackTraceString(e));
        }
    }

    private NodeList readXmlNodeList(String classPathResource) {
        try {
            String path = MqPushConfigMgr.class.getResource("/").getPath().replaceAll("%20", " ");
            File f = new File(path + classPathResource);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(f);
            xPathFactory = XPathFactory.newInstance();
            NodeList nodeList = evalXpath("/mapper/intercept");
            return nodeList;
        } catch (Exception e) {
            logger.error("消息推送配置管理器读取push.xml节点列表失败,失败原因:{}", ExceptionUtil.stackTraceString(e));
            return null;
        }
    }

    public Integer obtainUserType(String controllerName, String actionName) {
        String key = controllerName + "/" + actionName;
        if (!map.containsKey(key))
            return -1;
        return map.get(key);
    }

    private NodeList evalXpath(Object node, String xpath) throws Exception {
        javax.xml.xpath.XPath x = xPathFactory.newXPath();
        XPathExpression expr = x.compile(xpath);
        NodeList nodes = (NodeList) expr.evaluate(node, XPathConstants.NODESET);
        return nodes;
    }

    private NodeList evalXpath(String xpath) throws Exception {
        return evalXpath(document, xpath);
    }


}
