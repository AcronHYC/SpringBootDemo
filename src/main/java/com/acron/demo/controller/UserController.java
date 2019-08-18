package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.security.config.SecurityUserDetails;
import com.acron.demo.entity.database.User;
import com.acron.demo.service.IUserService;
import com.acron.demo.utils.MapToBean;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
public class UserController extends BaseController<IUserService,User> {
    @Resource
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private User user=new User();

    @Override
    public void initController(){
        setService(userService);
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
