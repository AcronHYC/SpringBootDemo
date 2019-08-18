package com.acron.demo.core.base.entity;

import com.acron.demo.utils.Utils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author Acron
 * @ClassName BaseResult
 * @Description TODO
 * @since 2019/07/20 21:07
 */
@Data
@Slf4j
public class BaseResult<T> implements Serializable {
    //时间
    private String timestamp;

    //状态码
    private Integer status=200;

    //异常信息
    private String message="OK";

    //结果集
    private T rows;

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

    public BaseResult(T rows){
        this.timestamp = Utils.formatDate(new Date());
        this.rows = rows;
    }

    public BaseResult(Integer status, String message, T rows) {
        this.timestamp = Utils.formatDate(new Date());
        this.status = status;
        this.message = message;
        this.rows = rows;
    }

    public static<T> BaseResult<T> success(T rows){
        return new BaseResult<>(rows);
    }

    public static<T> BaseResult<T> fail(int code,String message){
        return new BaseResult<>(code,message,null);
    }

    public static<T> BaseResult<T> fail(int code,String message,T rows){
        return new BaseResult<>(code,message,rows);
    }

    public static void out(ServletResponse response, BaseResult resultMap){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(resultMap));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        }finally{
            if(out!=null){
                out.flush();
                out.close();
            }
        }
    }
}
