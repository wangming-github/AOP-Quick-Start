package com.maizi.authorizationmodule.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.maizi.authorizationmodule.module.CustomUserUserDetails;
import com.maizi.service.entity.PermissionsEntity;
import com.maizi.service.entity.RolesEntity;
import com.maizi.service.entity.UsersEntity;
import com.maizi.service.service.PermissionsService;
import com.maizi.service.service.RolesService;
import com.maizi.service.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import java.util.List;


/**
 * @author maizi
 */ // 不启用此类：Sprint security默认从我们在SecurityConfig中配置的用户名查询用户信息校验登录信息
// 启用此类。我们可以从第三方获取数据用户信息
@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersService usersService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PermissionsService permissionsService;


    /**
     * 根根据用户名加载用户的详细信息，包括用户名、密码和角色等信息，
     * 并返回一个实现了 UserDetails 接口的对象。
     * 这个对象包含了用户的所有必要信息，供 Spring Security 后续使用。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.mysqlUserUtil.getUserByName模拟从数据库去查询出用户，
        UsersEntity user = usersService.getUserByName(username);
        log.info("↘↘↘【辅助】user查询结果：{}", user);
        Assert.notNull(user, "User cannot be null");
        List<RolesEntity> roles = rolesService.findRolesByUserId(user.getId());
        log.info("↘↘↘【辅助】roles查询结果：{}", roles);
        Assert.notEmpty(roles, "roles cannot be empty");
        List<PermissionsEntity> permission = permissionsService.findPermissionsByRoles(roles);
        log.info("↘↘↘【辅助】permission查询结果：{}", permission);
        Assert.notEmpty(permission, "permission cannot be empty");
        return new CustomUserUserDetails(user, roles, permission);
    }

}
