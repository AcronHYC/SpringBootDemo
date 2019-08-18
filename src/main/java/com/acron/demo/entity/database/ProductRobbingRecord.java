package com.acron.demo.entity.database;

import com.acron.demo.core.base.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Acron
 * @ClassName ProductRobbingRecord
 * @Description TODO
 * @since 2019/07/07 14:29
 */
@Api("抢单记录实体对象")
@Data
@TableName("t_productrobbingrecord")
@NoArgsConstructor
@Accessors(chain = true)   //可链式调用
@RequiredArgsConstructor(staticName = "build")
@EqualsAndHashCode(callSuper = true)    //equals和hashCode方法应该包含父类
public class ProductRobbingRecord extends BaseEntity {
    @NonNull
    @TableField("mobile")
    String mobile;
    @NonNull
    @TableField("productID")
    String productID;
    @NonNull
    @TableField("robbingDate")
    Date robbingDate;
}
