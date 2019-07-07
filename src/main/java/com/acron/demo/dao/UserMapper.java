package com.acron.demo.dao;

import com.acron.demo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author Acron
 * @since 2019/06/23 19:04
 */
@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {
}
