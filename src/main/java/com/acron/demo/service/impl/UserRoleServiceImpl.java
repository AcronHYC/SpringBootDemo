package com.acron.demo.service.impl;

import com.acron.demo.dao.UserRoleMapper;
import com.acron.demo.entity.database.UserRole;
import com.acron.demo.service.IUserRoleService;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName UserRoleServiceImpl
 * @Description TODO
 * @since 2019/08/01 20:19
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRole getById(Serializable id){
        return userRoleMapper.selectById(id);
    }

    @Override
    public List<UserRole> list(){
        return userRoleMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public boolean save(UserRole entity) {
        return this.retBool(userRoleMapper.insert(entity));
    }
}
