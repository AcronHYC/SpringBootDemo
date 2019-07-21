package com.acron.demo.controller;

import com.acron.demo.core.base.controller.BaseController;
import com.acron.demo.entity.database.Product;
import com.acron.demo.service.IProductService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName ProductController
 * @Description TODO
 * @since 2019/07/12 22:51
 */
@Api(value = "Product-Controller",description = "产品接口测试")
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController<IProductService, Product> {
    @Resource
    private IProductService productService;

    @Override
    public void initController(){
        setService(productService);
    }
}
