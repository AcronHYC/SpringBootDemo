package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Acron
 * @ClassName RoleResource
 * @Description TODO
 * @since 2019/08/12 21:36
 */
@Api("角色-资源实体对象")
@Data
@TableName("t_role_resource")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@JsonIgnoreProperties({"handler"})
public class RoleResource extends BaseEntity {
    @TableField("roleId")
    private Role role;

    @TableField("resourceId")
    private Resource resource;
}
