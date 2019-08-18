package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.entity.database.RoleResource;
import com.acron.demo.service.IRoleResourceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName RoleResourceController
 * @Description TODO
 * @since 2019/08/12 22:38
 */
@Api(value = "RoleResource-Controller",description = "角色资源接口测试")
@Slf4j
@RestController
@RequestMapping("/roleResource")
public class RoleResourceController extends BaseController<IRoleResourceService, RoleResource> {
    @Resource
    private IRoleResourceService iRoleResourceService;

    @Override
    protected void initController() throws Exception {
        setService(iRoleResourceService);
    }
}
