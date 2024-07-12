package com.example.authorizationmodule.service.impl;

import com.example.authorizationmodule.module.CustomUserUserDetails;
import com.example.authorizationmodule.utils.DataUtil_PermissionOfMysql;
import com.example.authorizationmodule.utils.DataUtil_RoleOfMysql;
import com.example.authorizationmodule.utils.DataUtil_UserOfMysql;
import com.example.servicemodule.entity.PermissionOfMysql;
import com.example.servicemodule.entity.RoleOfMysql;
import com.example.servicemodule.entity.UserOfMysql;
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
@Component// 从内存中根据用户名查询用户信息
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private DataUtil_UserOfMysql dataUtilUserOfMysql;

    @Autowired
    private DataUtil_RoleOfMysql dataUtilRoleOfMysql;

    @Autowired
    private DataUtil_PermissionOfMysql dataUtilPermissionOfMysql;

    /**
     * 根根据用户名加载用户的详细信息，包括用户名、密码和角色等信息，
     * 并返回一个实现了 UserDetails 接口的对象。
     * 这个对象包含了用户的所有必要信息，供 Spring Security 后续使用。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.mysqlUserUtil.getUserByName模拟从数据库去查询出用户，
        UserOfMysql user = dataUtilUserOfMysql.getFirstUserByNameOrThrow(username);
        log.info("↘↘↘【辅助】user查询结果：{}", user);

        List<RoleOfMysql> roles = dataUtilRoleOfMysql.findRolesById(1L);
        log.info("↘↘↘【辅助】roles查询结果：{}", roles);

        List<PermissionOfMysql> permission = dataUtilPermissionOfMysql.findPermissionById(1L);
        log.info("↘↘↘【辅助】permission查询结果：{}", permission);

        return new CustomUserUserDetails(user, roles, permission);
    }

}
