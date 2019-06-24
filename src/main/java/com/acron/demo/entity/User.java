package com.acron.demo.entity;

import com.acron.demo.enums.Sex;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * @author Acron
 * @since 2019/06/23 17:44
 */
@Api("用户实体对象")
@Data
@TableName("user")
/**
 * @NotNull： 运行时检查一个属性是否为空，如果为空则不合法
 * @NonNull： 告诉编译器这个域不可能为空，当代码检查有空值时会给出一个风险警告，@RequiredArgsConstructor 用于构造带有@NonNull注解的字段
 */
@RequiredArgsConstructor(staticName = "build")
public class User {
    @TableId(type = IdType.UUID)
    private String id;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private Sex sex;
    @NonNull
    private String telephone;
    @NonNull
    private String email;
}
