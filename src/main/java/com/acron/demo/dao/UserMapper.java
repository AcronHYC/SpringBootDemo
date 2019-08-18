package com.acron.demo.dao;

import com.acron.demo.entity.database.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @since 2019/06/23 19:04
 */
@Repository("userMapper")
public interface UserMapper extends BaseMapper<User> {
    @Select("select * from t_user where id in (select userId from t_user_role where roleId=#{id})")
    List<User> selectByRoleId(String id);

    @Override
    @ResultMap("userMap")
    @Select("select * from t_user where id=#{id}")
    User selectById(Serializable id);

    @Override
    @ResultMap("userMap")
    @Select("select * from t_user ${ew.customSqlSegment}")
    List<User> selectList(@Param("ew") Wrapper<User> queryWrapper);

    @Override
    @ResultMap("userMap")
    @Select("select * from t_user ${ew.customSqlSegment} limit 1")
    User selectOne(@Param("ew") Wrapper<User> queryWrapper);
}
