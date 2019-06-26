package com.acron.demo;

import com.acron.demo.dao.UserMapper;
import com.acron.demo.entity.User;
import com.acron.demo.enums.Sex;
import com.acron.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

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
        log.info("User:{}",userMapper.selectById("6731612bad5ce8f1a3240dea88409012").getSex().getDesc());
    }

}
