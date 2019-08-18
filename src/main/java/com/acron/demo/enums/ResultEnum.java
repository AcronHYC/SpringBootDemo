package com.acron.demo.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author Acron
 * @ClassName ResultEnum
 * @Description TODO
 * @since 2019/08/02 22:11
 */
public enum ResultEnum {
    /**
     *请求成功
     */
    SUCCESS(200,"成功"),
    /**
     *参数异常
     */
    PARAM(400,"参数异常"),
    /**
     *未登录
     */
    UNAUTH(401,"未登录"),
    /**
     *请求失败
     */
    FORBIDDEN(403,"禁止访问"),
    /**
     *参数异常
     */
    NOTEXIST(404,"数据不存在"),
    /**
     *系统异常
     */
    EXCEPTION(500,"系统异常");

    @EnumValue
    private final Integer code;
    private final String msg;

    ResultEnum(Integer code,String msg){
        this.code=code;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
