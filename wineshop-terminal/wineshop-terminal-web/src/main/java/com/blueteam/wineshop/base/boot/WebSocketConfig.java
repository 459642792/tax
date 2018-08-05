package com.blueteam.wineshop.base.boot;

import com.blueteam.wineshop.base.spring.websocket.HandshakeInterceptor;
import com.blueteam.wineshop.base.spring.websocket.MessageWebSocketHandler;
import com.blueteam.wineshop.service.MessageWebSocket;
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
    MessageWebSocket handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //注册处理拦截器,拦截url为socketServer的请求
        webSocketHandlerRegistry.addHandler(handler, "/socketServer").setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor());
        //注册SockJs的处理拦截器,拦截url为/sockjs/socketServer的请求
        webSocketHandlerRegistry.addHandler(handler, "/sockjs/socketServer").setAllowedOrigins("*")
                .addInterceptors(new HandshakeInterceptor()).withSockJS();
    }
}
