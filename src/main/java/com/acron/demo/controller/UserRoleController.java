package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.entity.database.UserRole;
import com.acron.demo.service.IUserRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName UserRoleController
 * @Description TODO
 * @since 2019/08/01 20:20
 */
@Api(value = "UserRole-Controller",description = "用户角色接口测试")
@Slf4j
@RestController
@RequestMapping("userRole")
public class UserRoleController extends BaseController<IUserRoleService, UserRole> {
    @Resource
    private IUserRoleService userRoleService;

    @Override
    public void initController(){
        setService(userRoleService);
    }
}
