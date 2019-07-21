package com.acron.demo.core.handler;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.base.entity.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author Acron
 * @ClassName ControllerExceptionAdvice
 * @Description TODO
 * @since 2019/07/21 12:33
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice {
    /***
      * @Description:全局异常
      * @Date: 2019/7/21
      * * @Param e:
      * @return: com.acron.demo.core.base.entity.BaseResult
      **/
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public BaseResult errorHandler(Exception e){
        log.error("发生Exception异常:{}",e.getStackTrace()[0]);
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String msg="Line:"+stackTraceElement.getLineNumber()+
                   "；Exception："+e+
                   "；Class:"+stackTraceElement.getClassName()+
                   "；Method:"+stackTraceElement.getMethodName();
        return BaseResult.fail(-1,msg);
    }

    @ResponseBody
    @ExceptionHandler(value = CustomException.class)
    public BaseResult customErrorHandler(CustomException c){
        log.error("发生自定义异常：{}",c.getBaseResult());
        return c.getBaseResult();
    }
}
