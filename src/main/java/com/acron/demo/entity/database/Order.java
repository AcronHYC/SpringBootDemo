package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.acron.demo.enums.OrderStatus;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author Acron
 * @ClassName OrderMapper
 * @Description TODO
 * @since 2019/07/10 21:37
 */
@Api("订单实体对象")
@Data
@TableName("t_order")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@RequiredArgsConstructor(staticName = "build")
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
public class Order extends BaseEntity {
    @NonNull
    @TableField("userID")
    private String userID;

    @NonNull
    @TableField("productID")
    private String productID;

    @NonNull
    @TableField("amount")
    private Integer amount;

    @NonNull
    @TableField("status")
    private OrderStatus status;
}
