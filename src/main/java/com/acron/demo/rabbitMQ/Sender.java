package com.acron.demo.rabbitMQ;

import com.acron.demo.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Acron
 * @ClassName Sender
 * @Description TODO
 * @since 2019/07/02 22:11
 */
@Slf4j
@Component
public class Sender {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(){
        String context="Test:"+ Utils.formatDate(new Date());
        log.info("Sender send:{}",context);
        this.rabbitTemplate.convertAndSend("local.mail.queue.exchange","local.mail.routing.key",context);
    }
}
