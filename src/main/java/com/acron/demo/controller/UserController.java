package com.acron.demo.controller;

import com.acron.demo.entity.User;
import com.acron.demo.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Acron
 * @since 2019/06/23 18:04
 */
@Api(value = "User-Controller",description = "接口测试")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value="根据id查看用户", notes="根据id查看用户")
    @GetMapping("/user/{id}")
    public User findUserById(@ApiParam("ID,非空") @PathVariable("id") String id){
        User user=userService.getById(id);
        return  user;
    }
}
