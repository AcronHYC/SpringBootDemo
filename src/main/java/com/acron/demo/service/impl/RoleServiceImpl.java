package com.acron.demo.service.impl;

import com.acron.demo.dao.RoleMapper;
import com.acron.demo.entity.database.Role;
import com.acron.demo.entity.database.User;
import com.acron.demo.service.IRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @since 2019/07/30 20:28
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public Role getById(Serializable id) {
        return roleMapper.selectById(id);
    }

    @Override
    public List<Role> list() {
        return roleMapper.selectList(Wrappers.emptyWrapper());
    }
}
