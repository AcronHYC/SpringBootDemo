package com.acron.demo.core.security.config;

import com.acron.demo.core.security.utils.IUrlMatcher;
import com.acron.demo.entity.database.Role;
import com.acron.demo.service.IRoleService;
import com.acron.demo.service.IUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Acron
 * @ClassName MyInvocationSecurityMetadataSourceService
 * @Description 加载资源与权限的对应关系
 * @since 2019/07/29 22:14
 */
@Slf4j
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    //存放资源配置对象
    private static Map<com.acron.demo.entity.database.Resource, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    private IUrlMatcher iUrlMatcher;

    @Resource
    private IRoleService iRoleService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        loadResourceDefine();
        HttpServletRequest request=((FilterInvocation)o).getRequest();
        String url=request.getRequestURI();
        String method=request.getMethod();
        int index=url.indexOf("?");
        if(index != -1){
            url=url.substring(0,index);
        }
        log.info("加载对应资源,url:{},method:{}",url,method);
        for(Map.Entry<com.acron.demo.entity.database.Resource, Collection<ConfigAttribute>> map:resourceMap.entrySet()) {
            com.acron.demo.entity.database.Resource key = map.getKey();
            if (key.getType().equals(method) && iUrlMatcher.pathMatchesUrl(key.getUrl(), url)){
                return map.getValue();
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public void loadResourceDefine(){
        resourceMap = new ConcurrentHashMap<>();
        List<Role> roleList=iRoleService.list();
        roleList.forEach(role -> {
            String tagName=role.getTag();
            ConfigAttribute configAttribute=new SecurityConfig(tagName);
            role.getResources().forEach(resource -> {
                boolean existed=false;
                for(Map.Entry<com.acron.demo.entity.database.Resource, Collection<ConfigAttribute>> map:resourceMap.entrySet()){
                    com.acron.demo.entity.database.Resource key=map.getKey();
                    if(key.getUrl().equals(resource.getUrl()) && key.getType().equals(resource.getType()) ) {
                        Collection<ConfigAttribute> value = map.getValue();
                        value.add(configAttribute);
                        resourceMap.put(key, value);
                        existed=true;
                        break;
                    }
                }
                if(!existed){
                    Collection<ConfigAttribute> value=new ArrayList<>();
                    value.add(configAttribute);
                    resourceMap.put(resource,value);
                }
            });
        });
    }
}
