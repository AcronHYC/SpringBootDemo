package com.acron.demo.core.security.config;

import com.acron.demo.dao.UserMapper;
import com.acron.demo.entity.database.User;
import com.acron.demo.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @since 2019/08/03 16:36
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userName",username);
        User user=userMapper.selectOne(queryWrapper);
        return new SecurityUserDetails(user);
    }
}
