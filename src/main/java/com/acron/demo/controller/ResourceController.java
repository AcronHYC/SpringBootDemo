package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.entity.database.Resource;
import com.acron.demo.service.IResourceService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Acron
 * @ClassName ResourceController
 * @Description TODO
 * @since 2019/08/12 22:22
 */

@Api(value = "Resource-Controller",description = "资源接口测试")
@Slf4j
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController<IResourceService, Resource> {
    @javax.annotation.Resource
    private IResourceService iResourceService;

    @Override
    protected void initController() throws Exception {
        setService(iResourceService);
    }
}
