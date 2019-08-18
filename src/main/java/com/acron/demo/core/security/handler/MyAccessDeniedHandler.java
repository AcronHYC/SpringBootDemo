package com.acron.demo.core.security.handler;

import com.acron.demo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Acron
 * @ClassName MyAccessDeniedHandler
 * @Description TODO
 * @since 2019/08/02 22:37
 */
@Slf4j
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 权限不足，无法访问该资源！");
        log.info(msg.toString());
        //ResponseUtil.out(httpServletResponse, ResponseUtil.resultMap(402,"权限不足，无法访问该资源！"));
        httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"权限不足，无法访问该资源");
    }
}
