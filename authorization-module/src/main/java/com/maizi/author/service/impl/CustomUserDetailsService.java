package com.maizi.author.service.impl;

import com.maizi.author.feign.ServiceFeign;
import com.maizi.author.module.CustomUserUserDetails;
import com.maizi.common.dto.UserDTO;
import com.maizi.common.dto.UserDetailsDTO;
import com.maizi.common.utils.R;
import com.maizi.serve.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


/**
 * @author maizi
 */ // 不启用此类：Sprint security默认从我们在SecurityConfig中配置的用户名查询用户信息校验登录信息
// 启用此类。我们可以从第三方获取数据用户信息
@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsersService usersService;


    /**
     * 根根据用户名加载用户的详细信息，包括用户名、密码和角色等信息，
     * 并返回一个实现了 UserDetails 接口的对象。
     * 这个对象包含了用户的所有必要信息，供 Spring Security 后续使用。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsDTO userDetailsDTO = usersService.findAuthorByUsername(username);
        return new CustomUserUserDetails(userDetailsDTO);
    }

}
