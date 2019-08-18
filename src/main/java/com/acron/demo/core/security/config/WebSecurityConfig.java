package com.acron.demo.core.security.config;

import com.acron.demo.core.security.handler.*;
import com.acron.demo.core.security.interceptor.MyFilterSecurityInterceptor;
import com.acron.demo.core.security.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Acron
 * @ClassName WebSecurityConfig
 * @Description TODO
 * @since 2019/07/29 21:57
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启方法级的权限注解 ,设置后控制器层的方法前的@PreAuthorize("hasRole('admin')") 注解才能起效
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private UserDetailsService userDetailsService;
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private MyAuthenticationEntryPointHandler myAuthenticationEntryPointHandler;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    /**
     * 配置Spring Security的Filter链
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/favicon.ico");
        web.ignoring().antMatchers("/error");
        super.configure(web);
    }

    /**
     *  解决 无法直接注入 AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 基于token，所以不需要session  如果基于session 则表使用这段代码
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    //对请求进行认证  url认证配置顺序为：1.先配置放行不需要认证的 permitAll() 2.然后配置 需要特定权限的 hasRole() 3.最后配置 anyRequest().authenticated()
                    .authorizeRequests()
                    // 所有 /oauth/v1/api/login/ 请求的都放行 不做认证即不需要登录即可访问
                    .antMatchers(JwtTokenUtil.antMatchers.split(",")).permitAll()
                    //其他请求都需要进行认证,认证通过够才能访问
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    //匿名用户权限认证
                    .authenticationEntryPoint(myAuthenticationEntryPointHandler)
                    //认证用户权限不足
                    .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                    .formLogin()
                    //登录URL
                    .loginProcessingUrl("/LogAndReg/login")
                    //登录失败 处理类
                    .failureHandler(myAuthenticationFailHandler)
                    //登录成功处理类
                    .successHandler(myAuthenticationSuccessHandler)
                    //登录成功跳转路径
                    .successForwardUrl("/")
                    //登录失败跳转路径
                    .failureUrl("/")
                    //登录页面路径
                    .loginPage("/").permitAll()
                .and()
                    .logout()
                    .logoutUrl("/LogAndReg/logout")
                    //退出系统后的url跳转
                    .logoutSuccessUrl("/")
                    //退出系统后的 业务处理
                    .logoutSuccessHandler(myLogoutSuccessHandler)
                    .permitAll()
                    .invalidateHttpSession(true)
                .and()
                    //登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                    // 勾选Remember me登录会在PERSISTENT_LOGINS表中，生成一条记录
                    .rememberMe()
                    //cookie的有效期（秒为单位
                    .tokenValiditySeconds(3600)
                .and()
                    //加入自定义UsernamePasswordAuthenticationFilter替代原有Filter
                    .addFilterAt(new JWTLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        //添加JWT过滤器 除/login其它请求都需经过此过滤器
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(),jwtTokenUtil,userDetailsService));
        //在 beforeFilter 之前添加 自定义 filter
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
        //添加JWT filter 验证其他请求的Token是否合法
        //http.addFilterBefore(authenticationTokenFilterBean(), FilterSecurityInterceptor.class);
        //禁用缓存
        http.headers().cacheControl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

}
