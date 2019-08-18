package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.Api;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Acron
 * @ClassName Resource
 * @Description TODO
 * @since 2019/08/12 21:28
 */
@Api("资源实体对象")
@TableName("t_resource")
@Data
@Accessors(chain = true)   //可链式调用
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
@JsonIgnoreProperties({"handler"})
public class Resource extends BaseEntity {
    @TableField("resourceName")
    private String resourceName;
    
    @TableField("url")
    private String url;

    @TableField("type")
    private String type;

    @TableField("module")
    private String module;

}
