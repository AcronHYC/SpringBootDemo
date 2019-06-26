package com.acron.demo.controller;

import com.acron.demo.entity.User;
import com.acron.demo.enums.Sex;
import com.acron.demo.service.UserService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Acron
 * @since 2019/06/23 18:04
 */
@Api(value = "User-Controller",description = "接口测试")
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="根据id查看用户", notes="根据id查看用户")
    @GetMapping("/user/{id}")
    public String  findUserById(@ApiParam("ID,非空") @PathVariable("id") String id){
        User user=userService.getById(id);
        System.out.println(user.getSex().getDesc());
        return  user.toString();
    }

    @PostMapping("/user")
    public void createUser(){
        User user=User.build("testN","testP", Sex.FEMALE,"testT","testE");
        log.info(user.getSex().getValue()+":"+user.getSex().getDesc());
        userService.save(user);
    }
}
