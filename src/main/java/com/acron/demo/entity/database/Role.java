package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author Acron
 * @ClassName Role
 * @Description TODO
 * @since 2019/07/30 20:24
 */
@Api("角色实体对象")
@Data
@TableName("t_role")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@RequiredArgsConstructor(staticName = "build")
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
@JsonIgnoreProperties({"handler"})
public class Role extends BaseEntity {
    @NonNull
    @TableField("name")
    private String name;

    @NonNull
    @TableField("tag")
    private String tag;

    @TableField(exist = false)
    private List<User> users;

    @TableField(exist = false)
    private List<Resource> resources;
}
