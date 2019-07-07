package com.acron.demo.rabbitMQ;

import com.acron.demo.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName Receiver
 * @Description TODO
 * @since 2019/07/02 22:16
 */
@Slf4j
@Component
public class Receiver {
    @Resource
    private IMailService mailService;

    @RabbitHandler
    @RabbitListener(queues = "${mail.queue.name}",containerFactory = "singleListenerContainer")
    public void consumeMailQueue(@Payload byte[] message){
        try {
            String to=new String(message,"UTF-8");
            log.info("监听消费者队列：{}",to);
            boolean flag= mailService.sendSimpleMail(to, "用户注册","恭喜你注册成功!");
            if(flag){
                log.info("邮件送成功!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
