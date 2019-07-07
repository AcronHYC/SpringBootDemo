package com.acron.demo.core.base.eneity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Acron
 * @since 2019/06/25 20:46
 */
@Api("共用属性基类")
@Data
@Accessors(chain = true)
public abstract class BaseEntity<T extends Model> extends Model {
    private static final long serialVersionUID = -6332231062406603722L;

    @TableId(value = "id",type = IdType.UUID)
    private String id;
    
    /**
     * 插入数据时自动填充
     */
    @TableField(value="createDate",fill = FieldFill.INSERT)
    private Date createDate;
    
    /**
     * update="NOW()"，则生成的SQL中会含“update ... set ..., modify_date = NOW()”
     **/
    @TableField(value = "modifyDate",fill = FieldFill.INSERT_UPDATE,update = "NOW()")
    private Date modifyDate;

    /**
     * @Version注解说明: 更新时，实体对象的version属性必须有值，才会生成SQL update ... WHERE ... and version=?
     */
    @Version
    @TableField(value="version", fill = FieldFill.INSERT_UPDATE, update="%s+1")
    private Long version;
}
