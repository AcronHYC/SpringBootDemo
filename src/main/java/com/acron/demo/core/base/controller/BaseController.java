package com.acron.demo.core.base.controller;

import com.acron.demo.core.base.entity.BaseEntity;
import com.acron.demo.core.base.entity.BaseResult;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Acron
 * @ClassName BaseController
 * @Description TODO
 * @since 2019/07/11 21:19
 */
@Slf4j
public abstract class BaseController<S extends IService,E extends BaseEntity>{
    private S service;

    public void setService(S service) {
        this.service = service;
    }
    /***
      * @Description:子类重写改方法，注入Service
      * @Date: 2019/7/11
      * @return: void
      **/
    abstract protected void initController() throws Exception;

    @PostConstruct
    public void init() throws Exception{
        initController();
    }

    @ApiOperation(value = "创建实体",notes = "创建实体")
    @SuppressWarnings("unchecked")
    @PostMapping(value = "/entity",consumes = "application/json;charset=UTF-8")
    public BaseResult<Boolean> save(@RequestBody E entity){
        boolean flag=service.save(entity);
        if(flag){
            log.info("创建实体成功：{}",entity);
        }
        return BaseResult.success(flag);
    }

    @ApiOperation(value = "删除实体",notes = "删除实体")
    @SuppressWarnings("unchecked")
    @DeleteMapping(value = "/{id}")
    public BaseResult<Boolean> delete(@PathVariable String id){
        boolean flag=service.removeById(id);
        if(flag){
            log.info("删除实体成功：{}",id);
        }
        return BaseResult.success(flag);
    }

    @SuppressWarnings(value = "unchecked")
    @ApiOperation(value = "查询实体集合",notes = "查询实体集合")
    @GetMapping(value = "/")
    public BaseResult<List<E>> getAll(){
        return BaseResult.success(service.list());
    }

    @SuppressWarnings(value = "unchecked")
    @ApiOperation(value = "根据ID查询实体",notes = "根据ID查询实体")
    @GetMapping(value = "/{id}")
    public BaseResult<E> getOne(@ApiParam("实体ID") @PathVariable String id){
        return BaseResult.success((E)service.getById(id));
    }
}
