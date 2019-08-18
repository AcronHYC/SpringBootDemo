package com.acron.demo.core.base.exception;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author Acron
 * @ClassName BaseException
 * @Description TODO
 * @since 2019/08/02 22:00
 */
@Data
public class BaseException extends RuntimeException {
    private Integer code;

    private String message;

    public BaseException(){
        super();
    }

    public BaseException(String message){
        super(message);
        this.message=message;
    }

    public BaseException(Integer code,String message){
        super(message);
        this.message=message;
        this.code=code;
    }
}
