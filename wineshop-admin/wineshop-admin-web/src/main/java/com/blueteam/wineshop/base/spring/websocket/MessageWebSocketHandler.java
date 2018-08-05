package com.blueteam.wineshop.base.spring.websocket;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.base.util.StringUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by libra on 2017/5/27.
 * <p>
 * Message的websocket 消息处理
 */
@Component
public class MessageWebSocketHandler implements WebSocketHandler {

    private static Logger logger = LogManager.getLogger(MessageWebSocketHandler.class);

    /**
     * 获取登录凭证
     *
     * @param session
     * @return
     */
    private UserIdentify getUserIdentiify(WebSocketSession session) {
        try {
            String token = session.getHandshakeHeaders().getFirst("Sec-WebSocket-Protocol");
            if (StringUtil.IsNullOrEmpty(token))
                return null;
            if (token != null)
                token = token.trim();
            token = java.net.URLDecoder.decode(token, "UTF-8");
            UserIdentify identify = VerificationUtil.getUserIdentify(token);
            return identify;
//            String cookie = session.getHandshakeHeaders().getFirst("Cookie");
//            if(!StringUtil.IsNullOrEmpty(cookie)){
//                String [] cookieArray = cookie.split("\\;");
//                for (String c : cookieArray){
//                    if(c.trim().startsWith(Constants.ACCESS_TOKEN+"=")){
//                        String token = c.replace(Constants.ACCESS_TOKEN+"=","");
//                        if(token!=null)
//                            token = token.trim();
//                        token = java.net.URLDecoder.decode(token,"UTF-8");
//                        UserIdentify identify = VerificationUtil.getUserIdentify(token);
//                        return identify;
//                    }
//                }
//            }
            // return null;
        } catch (Exception ex) {
            logger.error("解析token发生异常");
            logger.error(ExceptionUtil.stackTraceString(ex));
            return null;
        }
    }

    private static synchronized void removeSession(String key, WebSocketSession sesion) {
        if (!userSocketSessionMap.containsKey(key))
            return;

        List<WebSocketSession> sessions = userSocketSessionMap.get(key);
        for (int i = 0; i < sessions.size(); i++) {
            if (sesion.equals(sessions.get(i))) {
                sessions.remove(i);
                if (sessions.size() == 0)
                    userSocketSessionMap.remove(key);
                return;
            }
        }
    }

    private void close(WebSocketSession session) {
        try {
            if (session.isOpen()) {
                session.close();
            }

            UserIdentify user = getUserIdentiify(session);
            if (user == null)
                return;

            String key = getKey(user);
            removeSession(key, session);
        } catch (IOException ioe) {
            logger.error("关闭连接发生错误 sessionid:" + session.getId());
            logger.error(ExceptionUtil.stackTraceString(ioe));
        } catch (Exception ex) {
            logger.error("断开websocket错误 sessionid:" + session.getId());
            logger.error(ExceptionUtil.stackTraceString(ex));
        }
    }

    private static synchronized void putSession(String key, WebSocketSession session, List<WebSocketSession> sessions) {
        if (sessions == null) {
            sessions = new ArrayList<>();
            userSocketSessionMap.put(key, sessions);
        }
        sessions.add(session);
    }


    /**
     * 存储session
     */
    private static final Map<String, List<WebSocketSession>> userSocketSessionMap;

    static {
        userSocketSessionMap = new HashMap<>();
    }

    private String getKey(UserIdentify userIdentiy) {
        if (userIdentiy == null)
            return "";

        //管理平台做特殊处理
        if (userIdentiy.getCurUType() == Enums.UserType.Admin)
            return userIdentiy.getCurUType() + "_0";
        return userIdentiy.getCurUType() + "_" + userIdentiy.getUserId();
    }

    /**
     * 建立连接后
     */
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        logger.info("用户连上websocket sessionid:" + session.getId());

        UserIdentify user = getUserIdentiify(session);
        if (user == null) {
            logger.warn("没有找到登录凭证，断开连接 sessionid:" + session.getId());
            session.close();
            return;
        }
        String key = getKey(user);
        if (userSocketSessionMap.containsKey(key)) {
            List<WebSocketSession> sessions = userSocketSessionMap.get(key);
            for (int i = 0; i < sessions.size(); i++) {
                if (session.equals(sessions.get(i))) {
                    return;
                }
            }

            putSession(key, session, sessions);
        } else
            putSession(key, session, null);
    }

    /**
     * 消息处理，在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     */
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

    }

    /**
     * 消息传输错误处理
     */
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {

        close(session);
    }

    /**
     * 关闭连接后
     */
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        close(session);
    }

    public boolean supportsPartialMessages() {
        return false;
    }


    /**
     * 给某个用户发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessageToUser(String message) {
        try {
            Message msg = JsonUtil.deserialize(message, Message.class);
            if (msg == null) {
                logger.warn("反序列化消息失败 msg:" + msg);
                return;
            }

            UserIdentify user = new UserIdentify();
            if (msg.getReceivingUserTypes() > 0)
                user.setCurUType(msg.getReceivingUserTypes());
            else if (msg.getCarriersID() > 0)
                user.setCurUType(Enums.UserType.Carriers);
            else if (msg.getVendorID() > 0)
                user.setCurUType(Enums.UserType.Vendor);

            user.setUserId(msg.getRecipients());

            String key = getKey(user);
            if (!userSocketSessionMap.containsKey(key))
                return;

            List<WebSocketSession> sessions = userSocketSessionMap.get(key);
            if (sessions == null)
                return;
            for (int i = 0; i < sessions.size(); i++) {
                if (sessions.get(i) == null || !sessions.get(i).isOpen())
                    close(sessions.get(i));
                sessions.get(i).sendMessage(new TextMessage(message));
            }
        } catch (IOException ioe) {
            logger.error("发送消息发生IO异常:  msg:" + message);
            logger.error(ExceptionUtil.stackTraceString(ioe));
        } catch (Exception ex) {
            logger.error("发送消息发生异常:   msg:" + message);
            logger.error(ExceptionUtil.stackTraceString(ex));
        }

    }
}
