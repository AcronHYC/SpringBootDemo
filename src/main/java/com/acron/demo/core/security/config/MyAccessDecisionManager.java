package com.acron.demo.core.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Acron
 * @ClassName MyAccessDecisionManager
 * @Description 资源权限认证器, 接口AccessDecisionManager也是必须实现的
 *                功能：证用户是否拥有所请求资源的权限
 *                详情：decide方法里面写的就是授权策略了，需要什么策略，可以自己写其中的策略逻辑
 *                认证通过就返回，不通过抛异常就行了，spring security会自动跳到权限不足处理类（WebSecurityConfig 类中配置文件上配的）
 * @since 2019/07/29 22:00
 */
@Slf4j
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {
    /***
      * @Description:授权策略
      * @Date: 2019/7/29
      * * @Param authentication:装载了从数据库读出来的权限(角色) 数据。这里是从MyUserDetailService里的loadUserByUsername方法里的grantedAuths对象的值传过来给 authentication 对象,简单点就是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     * @Param o:
     * @Param collection:装载了请求的url允许的角色数组 。这里是从MyInvocationSecurityMetadataSource里的loadResourceDefine方法里的atts对象取出的角色数据赋予给了configAttributes对象
      * @return: void
      **/
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        if(CollectionUtils.isEmpty(collection)){
            log.info("无访问权限!");
            throw new AccessDeniedException("无访问权限！");
        }

        for(ConfigAttribute configAttribute:collection){
            String needRole=configAttribute.getAttribute();
            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
                // 登录用户是否有该资源对应的角色
                if (needRole.trim().equals(grantedAuthority.getAuthority().trim())) {
                    log.info("拥有该角色:{},权限验证通过!", needRole);
                    return;
                }
            }
        };
        throw new AccessDeniedException("无访问权限！");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute){
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass){
        return true;
    }
}
