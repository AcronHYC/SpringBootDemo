package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Acron
 * @ClassName UserRole
 * @Description TODO
 * @since 2019/08/01 20:12
 */
@Api("用户角色实体对象")
@Data
@TableName("t_user_role")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@JsonIgnoreProperties({"handler"})
public class UserRole extends BaseEntity {
    @NotNull
    @TableField("userId")
    private User user;

    @NotNull
    @TableField("roleId")
    private Role role;
}
