package com.acron.demo.rabbitMQ;

import com.acron.demo.dao.OrderMapper;
import com.acron.demo.entity.database.Order;
import com.acron.demo.enums.OrderStatus;
import com.acron.demo.core.websocket.Handler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName OrderDeadListener
 * @Description TODO
 * @since 2019/07/10 23:01
 */
@Slf4j
@Component
public class OrderDeadListener {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private Handler handler;

    @RabbitHandler
    @RabbitListener(queues = "${order.dead.real.queue.name}",containerFactory = "multiListenerContainer")
    public void consumeMessage(@Payload Message message){
        try {
            String id=new String(message.getBody(),"UTF-8").replace("\"","");
            log.info("死信队列-用户下单超时未支付监听：{}",id);
            handler.sendMessageToOne(new TextMessage("支付时间已超时，请重新下单支付！"),"1dee882b0d669adf4a663448927b9a8e");
            Order order=orderMapper.selectById(id);
            if(order!=null){
                order.setStatus(OrderStatus.expire);
                orderMapper.updateById(order);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
