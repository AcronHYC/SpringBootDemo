package com.acron.demo.core.security.config;

import com.acron.demo.core.base.entity.BaseResult;
import com.acron.demo.core.base.exception.UnauthorizedException;
import com.acron.demo.core.security.utils.JwtTokenUtil;
import com.acron.demo.dao.UserMapper;
import com.acron.demo.utils.ResponseUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Acron
 * @ClassName JWTAuthenticationFilter
 * @Description jwt认证token
 *              每次请求接口时，就会进入这里验证token是否合法token，
 *              如果用户一直在操作，则token 过期时间会叠加；如果超过设置的过期时间未操作，则token 失效，需要重新登录。
 * @since 2019/08/04 14:06
 */
@Slf4j
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
    private JwtTokenUtil jwtTokenUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager,JwtTokenUtil jwtTokenUtil,UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtTokenUtil=jwtTokenUtil;
        this.userDetailsService=userDetailsService;
    }

    @Autowired
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint,JwtTokenUtil jwtTokenUtil) {
        super(authenticationManager, authenticationEntryPoint);
        this.jwtTokenUtil=jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String accessToken=request.getHeader(JwtTokenUtil.tokenHeader);
        if (accessToken != null && accessToken.startsWith(JwtTokenUtil.tokenPrefix)) {
            final String token=accessToken.substring(JwtTokenUtil.tokenPrefix.length());
            Date createTokenDate = jwtTokenUtil.getCreatedDateFromToken(token);
            log.info("token创建时间:{}",createTokenDate);
            if (createTokenDate != null) {
                Duration between=Duration.between(Instant.now(),Instant.ofEpochMilli(createTokenDate.getTime()));
                Long differSeconds =between.toMillis();
                boolean isExpire = JwtTokenUtil.tokenExpireTime > differSeconds;
                if(isExpire){
                    // 更新redis token
                }
            }

            String userName=jwtTokenUtil.getUsernameFromToken(token);
            log.info("token验证!!!!!");
            if (userName != null) {
                Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
                if(authentication == null || authentication.getPrincipal().equals("anonymousUser")){
                    SecurityUserDetails userDetails=(SecurityUserDetails)userDetailsService.loadUserByUsername(userName);
                    if(jwtTokenUtil.validateToken(token,userDetails)){
                        log.info("token验证成功!");
                        UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
                    }
                }else {
                    log.info("当前请求用户信息："+ JSON.toJSONString(authentication.getPrincipal()));
                }
            }else{
                log.error("token无效!!!");
                response.setStatus(401);
                throw new UnauthorizedException("token无效!!!!");
            }
        }
        chain.doFilter(request,response);
        return;
    }
}
