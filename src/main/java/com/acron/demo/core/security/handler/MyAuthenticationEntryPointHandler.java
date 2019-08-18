package com.acron.demo.core.security.handler;

import com.acron.demo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Acron
 * @ClassName MyAuthenticationEntryPointHandler
 * @Description 匿名用户权限不足处理业务
 * @since 2019/08/02 22:41
 */
@Slf4j
@Component
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        StringBuffer msg = new StringBuffer("请求访问: ");
        msg.append(httpServletRequest.getRequestURI()).append(" 接口，因为认证失败，无法访问系统资源.");
        log.info(msg.toString());
        //httpServletResponse.setStatus(402);
        //ResponseUtil.out(httpServletResponse, ResponseUtil.resultMap(402,"权限不足，无法访问该资源！"));
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,"权限不足，无法访问该资源");
    }
}
