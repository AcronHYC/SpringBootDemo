package com.acron.demo.core.security.handler;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Acron
 * @ClassName MyLogoutSuccessHandler
 * @Description 退出系统，清空redis中的Token，清空上下文&Session，记录退出日志等等
 * @since 2019/08/03 15:26
 */
@Slf4j
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        //清空上下文
        SecurityContextHolder.clearContext();
        // 从session中移除
        httpServletRequest.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
        //记录退出日志
       log.info("退出登录成功！");
        BaseResult.out(httpServletResponse, BaseResult.success("退出登录成功!"));
    }
}
