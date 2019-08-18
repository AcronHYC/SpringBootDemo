package com.acron.demo.service.impl;

import com.acron.demo.dao.RoleResourceMapper;
import com.acron.demo.entity.database.RoleResource;
import com.acron.demo.service.IRoleResourceService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName RoleResourceServiceImpl
 * @Description TODO
 * @since 2019/08/12 22:36
 */
@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource> implements IRoleResourceService {
    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Override
    public boolean save(RoleResource entity) {
        return this.retBool(roleResourceMapper.insert(entity));
    }

    @Override
    public RoleResource getById(Serializable id) {
        return roleResourceMapper.selectById(id);
    }

    @Override
    public List<RoleResource> list(Wrapper<RoleResource> queryWrapper) {
        return roleResourceMapper.selectList(queryWrapper);
    }
}
