package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.entity.database.Role;
import com.acron.demo.service.IRoleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName RoleController
 * @Description TODO
 * @since 2019/07/30 20:30
 */
@Api(value = "Role-Controller",description = "角色接口测试")
@RestController
@Slf4j
@RequestMapping("/role")
public class RoleController extends BaseController<IRoleService, Role> {
    @Resource
    private IRoleService roleService;

    @Override
    public void initController(){
        setService(roleService);
    }
}
