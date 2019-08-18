package com.acron.demo.service.impl;

import com.acron.demo.dao.ResourceMapper;
import com.acron.demo.entity.database.Resource;
import com.acron.demo.service.IResourceService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName ResourceServiceImpl
 * @Description TODO
 * @since 2019/08/12 22:16
 */
@Service("resourceService")
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public Resource getById(Serializable id){ return resourceMapper.selectById(id); }

    @Override
    public List<Resource> list(Wrapper<Resource> queryWrapper) {
        return resourceMapper.selectList(queryWrapper);
    }
}
