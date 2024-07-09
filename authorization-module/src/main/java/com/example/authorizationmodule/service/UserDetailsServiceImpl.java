package com.example.authorizationmodule.service;

import com.example.authorizationmodule.module.LoginUser;
import com.example.authorizationmodule.module.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


// 不启用此类：Sprint security默认从我们在SecurityConfig中配置的用户名查询用户信息校验登录信息
// 启用此类。我们可以从第三方获取数据用户信息
@Slf4j
// @Component//从内存中根据用户名查询用户信息
public class UserDetailsServiceImpl implements UserDetailsService {


    /**
     * 根据用户名去查询用户信息、权限信息
     * 并将这些信息封装成UserDetails对象【UserDetails包含用户名密码以外还有权限信息】
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("根据用户名(" + username + ")去查询用户信息、权限信息...");
        // 1.模拟从数据库去查询出用户，
        MyUser user = new MyUser("user", "user");
        // 2.并且实现UserDetails方法
        // MyUser 转为 LoginUser
        // 3.封装成UserDetails
        return new LoginUser();
    }
}
