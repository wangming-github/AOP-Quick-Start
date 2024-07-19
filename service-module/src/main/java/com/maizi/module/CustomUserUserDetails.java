package com.maizi.module;

import com.alibaba.fastjson.annotation.JSONField;
import com.maizi.common.constants.ModuleType;
import com.maizi.common.dto.UserDetailsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 定义了一个自定义的用户详细信息类 CustomUserUserDetails，
 * 实现了 Spring Security 的 UserDetails 接口，
 * 用于封装从数据库查询出的用户信息及其角色和权限。
 *
 * @author maizi
 */
@Slf4j
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomUserUserDetails implements UserDetails {

    // 数据库查询出来的用户
    private UserDetailsDTO user;

    // 构造方法
    public CustomUserUserDetails(UserDetailsDTO user) {
        this.user = user;
        log.info(ModuleType.SERVICE_MODULE +"↘↘角色：" + user.getRolesNames());
        log.info(ModuleType.SERVICE_MODULE +"↘↘权限：" + user.getPermissionNames());
    }

    // 系统所需的权限集合List<GrantedAuthority>
    @JSONField(serialize = false)// 表示在序列化过程中，被注解的字段不会被包含在生成的 JSON 中。
    private List<GrantedAuthority> authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 只需要第一次进来时将permission 转换为authoritys
        if (authority == null) {
            mapRolesToAuthorities(user.getRolesNames(), user.getPermissionNames());
        }
        return authority;
    }

    /**
     * 将角色和权限列表转换为 Spring Security 的 GrantedAuthority 对象列表
     *
     * @param roles       角色列表
     * @param permissions 权限列表
     */
    private void mapRolesToAuthorities(List<String> roles, List<String> permissions) {
        authority = new ArrayList<>();
        // 将角色转换为 Spring Security 的 ROLE_ 角色格式并添加到权限列表中
        roles.forEach(role -> authority.add(new SimpleGrantedAuthority("ROLE_" + role)));
        // 将权限添加到权限列表中
        permissions.forEach(permission -> authority.add(new SimpleGrantedAuthority(permission)));
        log.info(ModuleType.SERVICE_MODULE +"↘↘authority：" + this.authority);
    }


    @Override
    public String getPassword() {
        return user.getUsersPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsersUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
