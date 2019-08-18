package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.acron.demo.enums.Sex;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @author Acron
 * @since 2019/06/23 17:44
 */
@Api("用户实体对象")
@Data
@TableName("t_user")
/**
 * @NotNull： 运行时检查一个属性是否为空，如果为空则不合法
 * @NonNull： 告诉编译器这个域不可能为空，当代码检查有空值时会给出一个风险警告，@RequiredArgsConstructor 用于构造带有@NonNull注解的字段
 */
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@RequiredArgsConstructor(staticName = "build")
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
@JsonIgnoreProperties({"handler"})
public class User extends BaseEntity {
    @NonNull
    @TableField("userName")
    private String userName;
    @NonNull
    @TableField("password")
    private String password;
    @NonNull
    @TableField("sex")
    private Sex sex;
    @NonNull
    @TableField("telephone")
    private String telephone;
    @NonNull
    @Email(message = "邮箱格式不正确!")
    @TableField("email")
    private String email;
    @TableField(exist = false)
    private List<Role> roles;
}
