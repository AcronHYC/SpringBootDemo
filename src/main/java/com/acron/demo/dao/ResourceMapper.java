package com.acron.demo.dao;

import com.acron.demo.entity.database.Resource;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author Acron
 * @ClassName ResourceMapper
 * @Description TODO
 * @since 2019/08/12 21:32
 */
@Repository("resourceMapper")
public interface ResourceMapper extends BaseMapper<Resource> {
    @Select("select * from t_resource where id in (select resourceId from t_role_resource where roleId=#{id})")
    List<Resource> selectByResourceId(String id);

    @Override
    @ResultMap("resourceMap")
    @Select("select * from t_resource where id=#{id}")
    Resource selectById(Serializable id);

    @Override
    @ResultMap("resourceMap")
    @Select("select * from t_resource ${ew.customSqlSegment}")
    List<Resource> selectList(@Param("ew") Wrapper<Resource> queryWrapper);
}
