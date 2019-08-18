package com.acron.demo.core.base.exception;

import com.acron.demo.core.base.entity.BaseResult;
import lombok.Data;

/**
 * @author Acron
 * @ClassName CustomException
 * @Description TODO
 * @since 2019/07/21 13:18
 */
@Data
public class CustomException extends RuntimeException {
    private BaseResult baseResult;

    public CustomException(Integer status,String message){
        this.baseResult=BaseResult.fail(status,message);
    }
}
