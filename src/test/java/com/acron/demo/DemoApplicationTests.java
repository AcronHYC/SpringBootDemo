package com.acron.demo;

import com.acron.demo.dao.UserMapper;
import com.acron.demo.entity.Product;
import com.acron.demo.entity.User;
import com.acron.demo.enums.Sex;
import com.acron.demo.rabbitMQ.Sender;
import com.acron.demo.service.IMailService;
import com.acron.demo.service.IProductService;
import com.acron.demo.service.IUserService;
import com.acron.demo.thread.InitThread;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMailService IMailService;
    @Autowired
    private Sender sender;
    @Autowired
    private IProductService productService;
    @Autowired
    private InitThread initThread;

    @Test
    public void contextLoads() {
        /*User user=new User();
        user.setSex(Sex.FEMALE);
        user.setEmail("13");
        user.setPassword("ssss");
        user.setTelephone("!23213");
        user.setUserName("test");
        userMapper.insert(user);*/

        /*User user=User.build("test","Test",Sex.FEMALE,"Test","Test");
        userService.save(user);*/
        User user=User.build("Acron","123456",Sex.MALE,"13132222","123");
        userService.save(user);
        Map<String,Object> deleteMap=new HashMap<String, Object>();
        deleteMap.put("sex",2);
        userService.removeByMap(deleteMap);
        log.info("User:{}");
    }

    @Test
    public void testSendMail() throws Exception{
        //System.out.println(String.valueOf((int)((Math.random()*9+1)*100000)));
        //IMailService.sendSimpleMail("370575460@qq.com","验证码（HYC）"," 您的验证码："+ Utils.generateNumber());
    }

    @Test
    public void testRabbitMQ() throws Exception{
        initThread.generateMultiThread();
    }

    @Test
    public void testProduct() throws Exception{
        Product product=Product.build("product1",100);
        productService.save(product);
    }
}
