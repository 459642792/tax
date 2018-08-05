package com.blueteam.wineshop.base.boot;

import com.blueteam.wineshop.base.spring.websocket.HandshakeInterceptor;
import com.blueteam.wineshop.base.spring.websocket.MessageWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by libra on 2017/5/27.
 * <p/>
 * Spring websocket 配置
 */
@Component
@EnableWebSocket
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    MessageWebSocketHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/ws").addInterceptors(new HandshakeInterceptor());
        webSocketHandlerRegistry.addHandler(handler, "/ws/sockjs").addInterceptors(new HandshakeInterceptor()).withSockJS();//.addInterceptors(new HandShake()).withSockJS();
    }
}
