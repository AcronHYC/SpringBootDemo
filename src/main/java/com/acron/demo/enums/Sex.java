package com.acron.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.core.enums.IEnum;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;

import java.io.Serializable;

/**
 * @author Acron
 * @since 2019/06/24 20:04
 */
public enum Sex{
    MALE(1,"男"),
    FEMALE(2,"女");

    Sex(int value,String desc){
        this.value=value;
        this.desc=desc;
    }

    @EnumValue
    private final int value;
    private final String desc;

    public int getValue(){
        return value;
    }

    public String getDesc(){
        return desc;
    }
}
