package com.acron.demo.core.config;

import com.acron.demo.core.websocket.Handler;
import com.acron.demo.core.websocket.Handshake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName WebsocketConfig
 * @Description TODO
 * @since 2019/07/13 11:41
 */
@Slf4j
@Configuration
@EnableWebSocket
/*@EnableWebSocketMessageBroker */  //开启使用STOMP协议来传输基于代理（MessageBroker）的消息
public class WebsocketConfig implements WebSocketConfigurer {
    @Resource
    private Handshake handshake;
    @Resource
    private Handler handler;

    /***
      * @Description:注册使用了@ServerEndpoint注解声明的Websocket endpoint
      * @Date: 2019/7/13
      *
      * @return: org.springframework.web.socket.server.standard.ServerEndpointExporter
      **/
    /*@Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }*/

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler,"/websocket/{userID}").addInterceptors(handshake).setAllowedOrigins("*");
    }


}
