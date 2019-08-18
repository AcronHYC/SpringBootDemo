package com.acron.demo.service.impl;

import com.acron.demo.dao.UserMapper;
import com.acron.demo.entity.database.User;
import com.acron.demo.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Acron
 * @since 2019/06/23 17:58
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getById(Serializable id) {
        return userMapper.selectById(id);
    }

    @Override
    public List<User> list(Wrapper<User> queryWrapper) {
        return userMapper.selectList(queryWrapper);
    }
}
