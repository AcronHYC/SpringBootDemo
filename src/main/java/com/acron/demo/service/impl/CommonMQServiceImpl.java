package com.acron.demo.service.impl;

import com.acron.demo.service.ICommonMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName CommonMQServiceImpl
 * @Description 发送消息
 * @since 2019/07/07 15:04
 */
@Slf4j
@Service("commonMQService")
public class CommonMQServiceImpl implements ICommonMQService {
    @Resource
    private Environment env;
    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendRobbingMsg(String mobile){
        try {
            rabbitTemplate.setExchange(env.getProperty("user.order.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("user.order.routing.key.name"));
            Message message = MessageBuilder.withBody(mobile.getBytes("UTF-8")).setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                    .build();
            rabbitTemplate.send(message);
        }catch (Exception e){
            log.error("发送抢单消息异常：{}",mobile);
        }
    }

    @Override
    public void sendEmailMsg(String email){
        try {
            rabbitTemplate.setExchange("local.mail.exchange");
            rabbitTemplate.setRoutingKey("local.mail.routing.key");
            rabbitTemplate.convertAndSend(MessageBuilder.withBody(email.getBytes("UTF-8")).build());
        }catch (Exception e){
            log.error("发送邮件消息异常：{}",e.fillInStackTrace());
        }
    }
}
