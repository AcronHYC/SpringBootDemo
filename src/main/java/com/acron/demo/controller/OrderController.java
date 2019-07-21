package com.acron.demo.controller;

import com.acron.demo.dto.OrderDto;
import com.acron.demo.entity.database.Order;
import com.acron.demo.enums.OrderStatus;
import com.acron.demo.service.IOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName OrderController
 * @Description TODO
 * @since 2019/07/10 22:31
 */
@Api(value = "Order-Controller",description = "订单接口测试")
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private Environment env;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private IOrderService orderService;

    @ApiOperation(value = "用户下单",notes = "用户下单")
    @PostMapping(value = "/",consumes = "application/json;charset=UTF-8")
    public void createOrder(@ApiParam("订单DTO，未付款") @RequestBody OrderDto orderDto){
        Order order=new Order();
        try {
            BeanUtils.copyProperties(orderDto,order);
            order.setStatus(OrderStatus.UNPAID).setAmount(100);
            orderService.save(order);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            QueryWrapper<Order> wrapper=new QueryWrapper<>();
            wrapper.eq("userID",orderDto.getUserID()).eq("productID", orderDto.getProductID());
            Order insertOrder=orderService.getOne(wrapper);
            String id=insertOrder.getId();
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange(env.getProperty("order.dead.produce.exchange.name"));
            rabbitTemplate.setRoutingKey(env.getProperty("order.dead.produce.routing.key.name"));
            rabbitTemplate.convertAndSend((Object)id, new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    MessageProperties properties=message.getMessageProperties();
                    properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    properties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Integer.class);
                    properties.setCorrelationId(orderDto.getUserID());
                    return message;
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "用户支付",notes = "用户支付")
    @PutMapping(value = "/")
    public void payForOrder(){
        Object message=rabbitTemplate.receiveAndConvert(env.getProperty("order.dead.queue.name"));
        System.out.println(message);
    }
}
