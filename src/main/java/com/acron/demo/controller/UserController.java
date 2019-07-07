package com.acron.demo.controller;

import com.acron.demo.entity.User;
import com.acron.demo.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author Acron
 * @since 2019/06/23 18:04
 */
@Api(value = "User-Controller",description = "用户接口测试")
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController{
    @Resource
    private IUserService userService;

    private User user=new User();

    @ApiOperation(value="根据ID查询用户", notes="根据ID查询用户")
    @GetMapping("/{id}")
    public User findUserById(@ApiParam("查询条件") @PathVariable String id){
        return  userService.getById(id);
    }

    @ApiOperation(value="根据参数查询用户", notes="根据参数查询用户")
    @PostMapping(value = "/all",consumes = "application/json;charset=UTF-8")
    public List<User> findUserByParams(@ApiParam("查询条件") @RequestBody Map<String,String> queryParams){
        log.info("查询参数：{}",queryParams);
        user=new User();
        log.info(user.getClass().getName());
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryParams.forEach((k,v)->{
            for(Field field:user.getClass().getDeclaredFields()){
                if(field.getName().equals(k)){
                    queryWrapper.like(k,v);
                }
            }
        });
        log.info("查询结果：{}",userService.list(queryWrapper));
        return  userService.list(queryWrapper);
    }

    @ApiOperation(value = "创建用户",notes = "创建用户")
    @PostMapping(value = "/",consumes = "application/json;charset=UTF-8")
    public boolean createUser(@ApiParam("创建对象") @RequestBody @Valid User user){
        log.info("新建用户:{}",user.toString());
        return userService.save(user);
    }

    @ApiOperation(value = "更新用户",notes = "更新用户")
    @PutMapping(value="/",consumes = "application/json;charset=UTF-8")
    public boolean updateUser(@ApiParam("查询条件") @RequestBody Map<String,String> queryParams,@ApiParam("更新值") @RequestBody Map<String,String> updateParams){
        UpdateWrapper<User> updateWrapper=new UpdateWrapper<>();
        updateWrapper.allEq(queryParams,false);
        boolean flag=userService.update(user,updateWrapper);
        if(flag){
            log.info("更新用户成功:{}",user.toString());
        }
        return flag;
    }

    @ApiOperation(value = "根据ID删除用户",notes = "根据ID删除用户")
    @DeleteMapping(value = "/",consumes = "application/json;charset=UTF-8")
    public boolean deleteUser(@RequestBody List<String> ids){
        return userService.removeByIds(ids);
    }
}
