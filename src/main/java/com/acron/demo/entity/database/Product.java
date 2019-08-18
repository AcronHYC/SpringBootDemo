package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author Acron
 * @ClassName product
 * @Description TODO
 * @since 2019/07/06 20:00
 */
@Api("产品实体对象")
@Data
@TableName("t_product")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@RequiredArgsConstructor(staticName = "build")
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
public class Product extends BaseEntity {
    @NonNull
    @TableField("productName")
    private String productName;

    @NonNull
    @TableField("amount")
    private Integer amount;
}
