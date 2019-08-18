package com.acron.demo.core.handler;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.base.exception.*;
import com.acron.demo.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public BaseResult myException(HttpServletRequest request, BaseException e){
        e.printStackTrace();
        Integer code=e.getCode();
        String message=e.getMessage();
        if(e instanceof ParamErrorException){
            code = ResultEnum.PARAM.getCode();
        }else if(e instanceof UnauthorizedException) {
            code = ResultEnum.UNAUTH.getCode();
        }else if(e instanceof ForbiddenException){
            code = ResultEnum.FORBIDDEN.getCode();
        }else if(e instanceof NotExistedException){
            code = ResultEnum.NOTEXIST.getCode();
        }else {
            if (e.getCode()==null){
                code=ResultEnum.EXCEPTION.getCode();
            }
            if (e.getMessage()==null){
                message=ResultEnum.EXCEPTION.getMsg();
            }
        }
        return BaseResult.fail(code,message);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public void accessDeniedException(HttpServletRequest request, HttpServletResponse response,  AccessDeniedException e) throws IOException {
        log.error("AccessDeniedException:{}",e.getStackTrace()[0]);
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        String msg="Line:"+stackTraceElement.getLineNumber()+
                "；Exception："+e+
                "；Class:"+stackTraceElement.getClassName()+
                "；Method:"+stackTraceElement.getMethodName();
        response.sendError(HttpServletResponse.SC_FORBIDDEN,"对不起,你没有该访问权限!");
    }
}
