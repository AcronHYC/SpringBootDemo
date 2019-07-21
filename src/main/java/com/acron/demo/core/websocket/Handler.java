package com.acron.demo.core.websocket;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;

import java.util.*;

/**
 * @author Acron
 * @ClassName Handler
 * @Description TODO
 * @since 2019/07/14 14:25
 */
@Slf4j
@Service
public class Handler implements WebSocketHandler {
    /* 储存用户登录session */
    private final static Map<String,WebSocketSession> SESSIONS=Collections.synchronizedMap(new HashMap<>());

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("客户端连接成功！");
        String userID=(String)webSocketSession.getAttributes().get("userID");
        if(StringUtils.isNotEmpty(userID)){
            SESSIONS.put(userID,webSocketSession);
            webSocketSession.sendMessage(new TextMessage("成功建立连接:"+userID));
            log.info("当前在线人数:{}",SESSIONS.size());
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("服务器收到消息：{}",webSocketMessage);

        sendMessageToAll(new TextMessage("服务器收到消息："+webSocketMessage.getPayload().toString()));
        /*JSONObject msg= JSON.parseObject(webSocketMessage.getPayload().toString());
        JSONObject obj=new JSONObject();
        if(msg.getInteger("type")==1){
            obj.put("msg",msg.getString("msg"));
            sendMessageToAll(new TextMessage(obj.toJSONString()));
        }else{
            String to=msg.getString("to");
            obj.put("msg",msg.getString("msg"));
            sendMessageToOne(new TextMessage(obj.toJSONString()),to);
        }*/
        try {

        }catch (Exception e){
            log.error("发送消息出错:{}",e.fillInStackTrace());
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        log.info("连接异常!");
        SESSIONS.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("连接断开:{}" , closeStatus.toString());
        SESSIONS.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /***
      * @Description:群发消息
      * @Date: 2019/7/14
      * * @Param message:
      * @return: void
      **/
    public void sendMessageToAll(TextMessage message){
        SESSIONS.forEach((key,value)->{
            try {
                WebSocketSession session=value;
                session.sendMessage(message);
                log.info("全部发送成功！");
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /***
      * @Description:单发消息
      * @Date: 2019/7/14
      * * @Param message:
     * @Param userID:
      * @return: void
      **/
    public boolean sendMessageToOne(TextMessage message,String userID){
        if(SESSIONS.get(userID)==null)
            return false;
        WebSocketSession session=SESSIONS.get(userID);
        if(!session.isOpen())
            return false;
        log.info("发消息给：{}",session);
        try {
            session.sendMessage(message);
        }catch (Exception e){
            log.error("发送消息异常：{}",e.fillInStackTrace());
            return false;
        }
        return true;
    }

    private String getUserID(WebSocketSession session){
        try {
            String userID=(String)session.getAttributes().get("userID");
            return userID;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
