package com.acron.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Acron
 * @ClassName ModelMetaObjectHandler
 * @Description 字段填充配置
 * @since 2019/06/26 23:41
 */

@Component
public class ModelMetaObjectHandler implements MetaObjectHandler {
    /**
     * metaObject是页面传递过来的参数的包装对象，不是从数据库取的持久化对象，因此页面传过来哪些值，metaObject里就有哪些值。
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date now = new Date();
        Object createDate = this.getFieldValByName("createDate", metaObject);
        if(null == createDate){
            /**
             * 设置实体属性setter进去的值，优先级要高于自动填充的值。
             * 如果实体没有设置该属性，就给默认值，防止entity的setter值被覆盖。
             */
            this.setFieldValByName("createDate", now, metaObject);
        }
        Object modifyDate = this.getFieldValByName("modifyDate", metaObject);
        if(null == modifyDate){
            this.setFieldValByName("modifyDate", now, metaObject);
        }
        Object version = this.getFieldValByName("version", metaObject);
        if(null == version){
            this.setFieldValByName("version", 0L, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date now = new Date();
        Object modifyDate = this.getFieldValByName("modifyDate", metaObject);
        if(null != modifyDate){
            this.setFieldValByName("modifyDate", now, metaObject);
        }
        Object version = this.getFieldValByName("version", metaObject);
        if(null != version){
            long updateVerion=Long.parseLong(String.valueOf(version));
            this.setFieldValByName("version", ++updateVerion, metaObject);
        }
    }
}
