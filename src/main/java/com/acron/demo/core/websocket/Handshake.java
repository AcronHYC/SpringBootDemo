package com.acron.demo.core.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author Acron
 * @ClassName Handshake
 * @Description TODO
 * @since 2019/07/14 14:03
 */
@Slf4j
@Service
public class Handshake implements HandshakeInterceptor {
    /***
      * @Description:握手之前的操作
      * @Date: 2019/7/14
      * * @Param serverHttpRequest:
     * @Param serverHttpResponse:
     * @Param webSocketHandler:
     * @Param map:
      * @return: boolean
      **/
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler webSocketHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest) {
            String userID=request.getURI().toString().split("userID=")[1];
            if(StringUtils.isNotEmpty(userID)){
                log.info("记录登录用户ID：{}",userID);
                attributes.put("userID",userID);
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}
