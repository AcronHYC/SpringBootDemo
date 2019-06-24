package com.acron.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;

/**
 * @author Acron
 * @since 2019/06/24 20:04
 */
public enum Sex {
    MALE(1,"男"),
    FEMALE(2,"女");

    Sex(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    @EnumValue
    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
