package com.acron.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Acron
 * @ClassName OrderStatus
 * @Description TODO
 * @since 2019/07/10 21:54
 */
public enum OrderStatus {
    UNPAID(0,"未付款"),
    PAID(1,"已付款"),
    expire(2,"已失效");

    OrderStatus(int value,String desc){
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
