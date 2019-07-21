package com.acron.demo.core.base.entity;

import com.acron.demo.utils.Utils;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Acron
 * @ClassName BaseResult
 * @Description TODO
 * @since 2019/07/20 21:07
 */
@Data
public class BaseResult<T> implements Serializable {
    //时间
    private String timestamp;

    //状态码
    private Integer status=200;

    //异常信息
    private String message="OK";

    //结果集
    private T data;

    public enum Status {
        SUCCESS(200, "OK"),
        BAD_REQUEST(400, "Bad Request"),
        NOT_FOUND(404, "Not Found"),
        INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
        NOT_VALID_PARAM(40005, "Not valid Params"),
        NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
        NOT_LOGIN(50000, "Not Login");

        @EnumValue
        private int code;
        private String desc;

        Status(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode(){
            return code;
        }

        public String getDesc(){
            return desc;
        }
    }

    public BaseResult(){ }

    public BaseResult(T data){
        this.timestamp = Utils.formatDate(new Date());
        this.data = data;
    }

    public BaseResult(Integer status, String message, T data) {
        this.timestamp = Utils.formatDate(new Date());
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static<T> BaseResult<T> success(T data){
        return new BaseResult<>(data);
    }

    public static<T> BaseResult<T> fail(int code,String message){
        return new BaseResult<>(code,message,null);
    }

}
