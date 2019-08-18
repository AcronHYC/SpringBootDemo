package com.acron.demo.core.security.config;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.security.handler.MyAuthenticationFailHandler;
import com.acron.demo.core.security.handler.MyAuthenticationSuccessHandler;
import com.acron.demo.core.security.utils.JwtTokenUtil;
import com.acron.demo.entity.database.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author Acron
 * @ClassName JWTLoginFilter
 * @Description TODO
 * @since 2019/08/06 21:46
 */

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    private AuthenticationManager authenticationManager;

    public JWTLoginFilter(AuthenticationManager authenticationManager){
        this.setFilterProcessesUrl("/LogAndReg/login");
        this.setAuthenticationFailureHandler(new MyAuthenticationFailHandler());
        this.setAuthenticationSuccessHandler(new MyAuthenticationSuccessHandler(new JwtTokenUtil()));
        this.authenticationManager=authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            InputStream is = request.getInputStream();
            user = objectMapper.readValue(is, User.class);
        } catch (IOException e) {
            throw new RuntimeException("IO异常");
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword(),new ArrayList<>()));
    }
}
