package com.acron.demo.dao;

import com.acron.demo.entity.database.RoleResource;
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
 * @ClassName RoleResourceMapper
 * @Description TODO
 * @since 2019/08/12 21:39
 */
@Repository("roleResource")
public interface RoleResourceMapper extends BaseMapper<RoleResource> {
    @Override
    @ResultMap("roleResourceMap")
    @Select("select re.id resourceId,re.resourceName,re.url,re.type,re.module,r.id roleId,r.name,r.tag,rr.id " +
            "from t_role_resource rr left join t_resource re on rr.resourceId=re.id " +
            "left join t_role r on rr.roleId=r.id " +
            "where rr.id=#{id}")
    RoleResource selectById(Serializable id);

    @Override
    @ResultMap("roleResourceMap")
    @Select("select re.id resourceId,re.resourceName,re.url,re.type,re.module,r.id roleId,r.name,r.tag,rr.id " +
            "from t_role_resource rr left join t_resource re on rr.resourceId=re.id " +
            "left join t_role r on rr.roleId=r.id")
    List<RoleResource> selectList(@Param("ew") Wrapper<RoleResource> queryWrapper);

    @Override
    @Insert("insert into t_role_resource(id,roleId,resourceId,createDate,modifyDate,version) " +
            "values(#{id},#{role.id},#{resource.id},#{createDate},#{modifyDate},#{version})")
    int insert(RoleResource entity);
}
