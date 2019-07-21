package com.acron.demo.core.base.controller;

import com.acron.demo.core.base.entity.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Acron
 * @ClassName ExceptionController
 * @Description 全局异常处理，无法获取异常类型
 * @since 2019/07/20 21:53
 */
@Slf4j
@RestController
@EnableConfigurationProperties({ServerProperties.class})
public class ExceptionController implements ErrorController {
    private ExceptionController exceptionController;

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    private final static String ERROR_PATH = "/error";

    public ExceptionController(ErrorAttributes errorAttributes) {
        this.errorAttributes = errorAttributes;
    }

    public ExceptionController() {
        if(exceptionController == null){
            exceptionController = new ExceptionController(errorAttributes);
        }
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public BaseResult error(HttpServletRequest request,WebRequest webRequest) {
        Map<String, Object> body = getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL),webRequest);
        HttpStatus status = getStatus(request);
        log.error("发生异常:{}",body);
        return BaseResult.fail(status.value(),body.get("message").toString());
    }

    protected boolean isIncludeStackTrace(HttpServletRequest request,
                                          MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.serverProperties.getError().getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return getTraceParameter(request);
        }
        return false;
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace, WebRequest webRequest) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Map<String, Object> map = this.errorAttributes.getErrorAttributes(webRequest,includeStackTrace);
        return map;
    }

    /***
      * @Description:是否包含trace
      * @Date: 2019/7/20
      * * @Param request:
      * @return: boolean
      **/
    private boolean getTraceParameter(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        if (parameter == null) {
            return false;
        }
        return !"false".equals(parameter.toLowerCase());
    }

    /***
      * @Description:获取状态码
      * @Date: 2019/7/20
      * * @Param request:
      * @return: org.springframework.http.HttpStatus
      **/
    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request
                .getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    @Override
    public String getErrorPath() {
        return "";
    }
}
