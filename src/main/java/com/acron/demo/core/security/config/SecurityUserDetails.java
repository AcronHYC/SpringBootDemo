package com.acron.demo.core.security.config;

import com.acron.demo.entity.database.Role;
import com.acron.demo.entity.database.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Acron
 * @ClassName SecurityUserDetails
 * @Description TODO
 * @since 2019/08/03 13:54
 */
public class SecurityUserDetails extends User implements UserDetails {
    private static final long serialVersionUID = -7622081664372134562L;

    public SecurityUserDetails(User user) {
        if (user != null) {
            this.setId(user.getId());
            this.setUserName(user.getUserName());
            this.setPassword(user.getPassword());
            this.setSex(user.getSex());
            this.setEmail(user.getEmail());
            this.setTelephone(user.getTelephone());
            this.setCreateDate(user.getCreateDate());
            this.setModifyDate(user.getModifyDate());
            this.setVersion(user.getVersion());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        List<Role> roles = this.getRoles();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                authorityList.add(new SimpleGrantedAuthority(role.getTag()));
            }
        }
        return authorityList;
    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    /**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
