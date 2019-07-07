package com.acron.demo.service.impl;

import com.acron.demo.dao.UserMapper;
import com.acron.demo.entity.User;
import com.acron.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Acron
 * @since 2019/06/23 17:58
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
}
