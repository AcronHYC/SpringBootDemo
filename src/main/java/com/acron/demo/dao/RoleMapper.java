package com.acron.demo.dao;

import com.acron.demo.entity.database.Role;
import com.acron.demo.entity.database.User;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName RoleMapper
 * @Description TODO
 * @since 2019/07/30 20:26
 */
@Repository("roleMapper")
public interface RoleMapper extends BaseMapper<Role> {
    @Select("select * from t_role where id in (select roleId from t_user_role where userId=#{id})")
    List<Role> selectByRoleId(String id);

    @Override
    @ResultMap("roleMap")
    @Select("select * from t_role where id=#{id}")
    Role selectById(Serializable id);

    @Override
    @ResultMap("roleMap")
    @Select("select * from t_role")
    List<Role> selectList(@Param("ew") Wrapper<Role> queryWrapper);
}
