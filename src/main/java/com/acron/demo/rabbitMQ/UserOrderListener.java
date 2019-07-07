package com.acron.demo.rabbitMQ;

import com.acron.demo.service.IProductService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName UserOrderListener
 * @Description TODO
 * @since 2019/07/07 13:05
 */
@Slf4j
@Component("userOrderListener")
public class UserOrderListener implements ChannelAwareMessageListener {
    @Resource
    private IProductService productService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception{
        long tag=message.getMessageProperties().getDeliveryTag();
        try {
            byte[] body=message.getBody();
            String mobile=new String(body,"UTF-8");
            log.info("监听到抢单手机号：{}",mobile);
            productService.manageRobbing("45b09cef93e7b917c809bbe71ae1b2d9",mobile);
            //对消息进行应答
            channel.basicAck(tag,true);
        }catch (Exception e){
            log.error("用户抢单异常:{}",e.fillInStackTrace());
            channel.basicReject(tag,false);
        }
    }
}
