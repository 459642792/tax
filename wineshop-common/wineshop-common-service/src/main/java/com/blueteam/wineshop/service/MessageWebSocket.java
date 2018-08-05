package com.blueteam.wineshop.service;

import com.blueteam.base.constant.Enums;
import com.blueteam.base.lang.RStr;
import com.blueteam.base.util.ExceptionUtil;
import com.blueteam.base.util.JsonUtil;
import com.blueteam.base.util.VerificationUtil;
import com.blueteam.entity.dto.UserIdentify;
import com.blueteam.entity.po.Message;
import com.blueteam.entity.po.MessageSubjectPO;
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
 * 功能描述: 处理消息
 *@since 1.4.0
 *@author xiaojiang
 */
@Component
public class MessageWebSocket implements WebSocketHandler {
    /**
     * 存储session
     */
    private static final Map<String, List<WebSocketSession>> userSocketSessionMap;
    static {
        userSocketSessionMap = new HashMap<>();
    }
    private static Logger logger = LogManager.getLogger(MessageWebSocket.class);
   /**
    * 方法的功能描述: 建立连接
    *@methodName
     * @param: null
    *@return
    *@since 1.4.0
    *@author xiaojiang 2018/1/26 16:38
    *@modifier
    */
    @Override
    public void afterConnectionEstablished(WebSocketSession session)
            throws Exception {
        UserIdentify user = getUserIdentiify(session);
        if (RStr.isEmpty(user)) {
            session.close();
            return;
        }
        String key = getKey(user);
        if (userSocketSessionMap.containsKey(key)) {
            List<WebSocketSession> sessions = userSocketSessionMap.get(key);
            for (WebSocketSession webSocketSession : sessions){
                if (session.equals(webSocketSession)) {
                    return;
                }
                putSession(key, session, sessions);
            }
        } else{
            putSession(key, session, null);
        }
    }
    /**
     * ，
     */
    /**
     * 方法的功能描述:消息处理
     *                  在客户端通过Websocket API发送的消息会经过这里，然后进行相应的处理
     *                  现在只做后端处理 故暂时不处理
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/22 14:30
     *@modifier
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
    }
    /**
     * 消息传输错误处理
     */
    @Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
        close(session);
    }

    /**
     * 关闭连接后
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
        close(session);
    }
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }



    /**
     * 给某个用户发送消息
     *
     * @param
     * @throws IOException
     */
    public void sendMessageToUser(MessageSubjectPO message) {
        try {
            UserIdentify user = new UserIdentify();
            user.setCurUType(message.getUserType());
            user.setUserId(message.getUserId());
            String key = getKey(user);
            if (!userSocketSessionMap.containsKey(key)) {
                return;
            }
            List<WebSocketSession> sessions = userSocketSessionMap.get(key);
            if (sessions == null){
                return;}
            for (int i = 0; i < sessions.size(); i++) {
                if (sessions.get(i) == null || !sessions.get(i).isOpen()){
                    close(sessions.get(i));
                }
                sessions.get(i).sendMessage(new TextMessage(message.toString()));
            }
        } catch (IOException ioe) {
            logger.error(ExceptionUtil.stackTraceString(ioe));
        } catch (Exception ex) {
            logger.error(ExceptionUtil.stackTraceString(ex));
        }

    }

    /**
     * 方法的功能描述: 根据session 获取用户
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/22 14:08
     *@modifier
     */
    private UserIdentify getUserIdentiify(WebSocketSession session) {
        try {
            String token = session.getHandshakeHeaders().getFirst("Sec-WebSocket-Protocol");
            System.out.println("token"+token);
            if (RStr.isEmpty(token)){
                return null;
            }else{
                token = token.trim();
            }
            token = java.net.URLDecoder.decode(token, "UTF-8");
            UserIdentify identify = VerificationUtil.getUserIdentify(token);
            return identify;
        } catch (Exception ex) {
            logger.error(ExceptionUtil.stackTraceString(ex));
            return null;
        }
    }
    /**
     * 方法的功能描述: 获取独有的标识
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/22 14:13
     *@modifier
     */
    private String getKey(UserIdentify userIdentiy) {
        if (userIdentiy == null){
            return "";}
        //管理平台做特殊处理
        if (Enums.FlagEnumHelper.HasFlag(userIdentiy.getCurUType(), Enums.UserType.Admin )){
            return userIdentiy.getCurUType() + "_0";
        }
        return userIdentiy.getCurUType() + "_" + userIdentiy.getUserId();
    }
    /**
     * 方法的功能描述: 放置连接属性
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/22 14:19
     *@modifier
     */
    private static synchronized void putSession(String key, WebSocketSession session, List<WebSocketSession> sessions) {
        if (RStr.isEmpty(sessions)) {
            sessions = new ArrayList<>();
            userSocketSessionMap.put(key, sessions);
        }
        sessions.add(session);
    }
    /**
     * 方法的功能描述:
     *              从连接池中删除 因登陆标识思考有缺陷，故如果在连接socket中改变了用户类型 会移除失败
     *              暂时不考虑此极端现象
     *@methodName
     * @param: null
     *@return
     *@since 1.4.0
     *@author xiaojiang 2018/1/22 14:45
     *@modifier
     */
    private void close(WebSocketSession session) {
        try {
            if (session.isOpen()) {
                session.close();
            }
            System.out.println("");
            UserIdentify user = getUserIdentiify(session);
            if (RStr.isEmpty(user)){
                return;}
            String key = getKey(user);
            removeSession(key, session);
        } catch (IOException ioe) {
            logger.error(ExceptionUtil.stackTraceString(ioe));
        } catch (Exception ex) {
            logger.error("断开websocket错误 sessionid:" + session.getId());
            logger.error(ExceptionUtil.stackTraceString(ex));
        }
    }
    private static synchronized void removeSession(String key, WebSocketSession sesion) {
        if (!userSocketSessionMap.containsKey(key)) {
            return;
        }
        List<WebSocketSession> sessions = userSocketSessionMap.get(key);
        for (WebSocketSession webSocketSession : sessions) {
            if (sesion.equals(webSocketSession)) {
                sessions.remove(webSocketSession);
                if (sessions.size() == 0)
                    userSocketSessionMap.remove(key);
                return;
            }
        }
    }

}
