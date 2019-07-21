package com.acron.demo.core.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Acron
 * @ClassName WebsocketServer
 * @Description TODO
 * @since 2019/07/13 11:52
 */
@Slf4j
@ServerEndpoint(value = "/websocket/{userID}")
@Component
public class WebsocketServer {
    /* 记录当前连接数量 */
    private static LongAdder sessionAcount=new LongAdder();

    /* 存放在线客户端 */
    private static Map<String, Session> clients=new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("userID") String userID, Session session){
        clients.put(session.getId(),session);
        sessionAcount.increment();
        log.info("有新的客户端建立了连接,会话ID：{}，用户ID：{}，当前连接人数：{}",session.getId(),userID,sessionAcount);
    }

    /***
      * @Description:客户端断开连接
      * @Date: 2019/7/13
      * * @Param session: 连接session
      * @return: void
      **/
    @OnClose
    public void onClose(Session session){
        log.info("客户端断开：{}",session.getId());
        clients.remove(session.getId());
        sessionAcount.decrement();
    }

    /***
      * @Description:异常处理
      * @Date: 2019/7/13
      * * @Param throwable:
      * @return: void
      **/
    @OnError
    public void onError(Throwable throwable){
        throwable.printStackTrace();
    }

    @OnMessage
    public void sendMessage(String message){
        log.info("接受客户端消息：{}",message);
        this.sendAll(message);
    }

    /***
      * @Description:群发消息
      * @Date: 2019/7/13
      * * @Param message:
      * @return: void
      **/
    private void sendAll(String message){
        clients.entrySet().forEach(entry->entry.getValue().getAsyncRemote().sendText(message));
    }
}
