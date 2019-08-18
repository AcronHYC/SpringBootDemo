package com.acron.demo.dao;

import com.acron.demo.entity.database.User;
import com.acron.demo.entity.database.UserRole;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName UserRoleMapper
 * @Description TODO
 * @since 2019/08/01 20:15
 */
@Repository("userRoleMapper")
public interface UserRoleMapper extends BaseMapper<UserRole> {
    @Override
    @ResultMap("userRoleMap")
    @Select("select u.id userId,u.userName,u.telephone,u.email,r.id roleId,r.name,r.tag,ur.id " +
            "from t_user_role ur left join t_user u on ur.userId=u.id " +
            "left join t_role r on ur.roleId=r.id " +
            "where ur.id=#{id}")
    UserRole selectById(Serializable id);

    @Override
    @ResultMap("userRoleMap")
    @Select("select u.id userId,u.userName,u.telephone,u.email,r.id roleId,r.name,r.tag,ur.id " +
            "from t_user_role ur left join t_user u on ur.userId=u.id " +
            "left join t_role r on ur.roleId=r.id")
    List<UserRole> selectList(@Param("ew") Wrapper<UserRole> queryWrapper);

    @Override
    @Insert("insert into t_user_role(id,userId,roleId,createDate,modifyDate,version) " +
            "values(#{id},#{user.id},#{role.id},#{createDate},#{modifyDate},#{version})")
    int insert(UserRole entity);
}
