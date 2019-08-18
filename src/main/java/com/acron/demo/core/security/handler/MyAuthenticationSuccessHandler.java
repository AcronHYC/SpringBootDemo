package com.acron.demo.core.security.handler;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.security.config.SecurityUserDetails;
import com.acron.demo.core.security.utils.JwtTokenUtil;
import com.acron.demo.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Acron
 * @ClassName MyAuthenticationSuccessHandler
 * @Description  登录成功处理类
 *               用户登录系统成功后，需要做的业务操作
 *               包括：1.生成token;2.将用户信息保存到redis;3.将身份存储到SecurityContext里
 * @since 2019/08/03 13:47
 */
@Slf4j
@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public MyAuthenticationSuccessHandler(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil=jwtTokenUtil;
    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityUserDetails userDetails=(SecurityUserDetails)authentication.getPrincipal();
        //将身份 存储到SecurityContext里
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
        request.getSession().setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        StringBuffer msg = new StringBuffer("用户：");
        msg.append(userDetails.getUsername()).append(" 成功登录系统.");
        log.info(msg.toString());
        String token = jwtTokenUtil.generateAccessToken(userDetails);
        String accessToken=JwtTokenUtil.tokenPrefix+token;
        String refreshToken=JwtTokenUtil.tokenPrefix+jwtTokenUtil.refreshToken(token);
        Map<String,String> map=new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        map.put("userId",userDetails.getId());
        map.put("userName",userDetails.getUserName());
        map.put("email",userDetails.getEmail());
        map.put("telephone",userDetails.getTelephone());
        BaseResult.out(response,BaseResult.success(map));
    }
}
