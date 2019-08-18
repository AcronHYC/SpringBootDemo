package com.acron.demo.core.security.handler;

import com.acron.demo.utils.ResponseUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Acron
 * @ClassName MyAuthenticationFailHandler
 * @Description 登录失败处理类：当认证用户登录系统后访问资源时因权限不足则会进入到此类并执行相关业务
 * @since 2019/08/02 22:21
 */
@Component
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"用户名或密码错误！");
        }else if(exception instanceof DisabledException){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"账号已禁用,请联系管理员！");
        }else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"登录失败！");
        }
    }
}
