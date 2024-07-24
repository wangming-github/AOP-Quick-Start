package com.maizi.config;

import com.maizi.exception.Service_AccessDeniedHandler;
import com.maizi.exception.Service_AuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 安全配置类，配置Spring Security相关的安全策略。
 * <p>
 * 该类主要配置了：
 * 1. 密码编码器。
 * 2. 认证管理器。
 * 3. HTTP安全策略，包括CSRF、会话管理、请求授权和异常处理。
 * 4. 自定义JWT过滤器。
 * </p>
 * <p>
 * {@link EnableGlobalMethodSecurity}注解用于启用全局方法安全性，支持使用注解来进行方法级别的权限控制。
 * </p>
 * <p>
 * 注解说明：
 * - prePostEnabled：启用Spring Security的Pre/Post注解功能。
 * - securedEnabled：启用Spring Security的@Secured注解功能。
 * </p>
 * <p>
 * 类使用了{@link Slf4j}注解，自动生成用于记录日志的logger对象。
 * </p>
 * <p>
 * 类注解：
 * - {@link Configuration}：标识该类是一个配置类，Spring会将其作为配置类来处理。
 * </p>
 * <p>
 * 依赖注入：
 * - {@link Service_JwtTokenFilter}：自定义的JWT过滤器。
 * - {@link com.maizi.exception.Service_AccessDeniedHandler}：自定义的访问拒绝处理器。
 * - {@link Service_AuthenticationEntryPoint}：自定义的认证入口点。
 * </p>
 *
 * @author maizi
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class Service_SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Service_JwtTokenFilter serviceJwtTokenFilter; // 注入自定义的JWT过滤器

    @Autowired
    private Service_AccessDeniedHandler serviceAccessDeniedHandler; // 注入自定义的访问拒绝处理器

    @Autowired
    private Service_AuthenticationEntryPoint serviceAuthenticationEntryPoint; // 注入自定义的认证入口点

    /**
     * 定义密码编码器Bean。
     * <p>
     * 使用 {@link BCryptPasswordEncoder} 来替代过时的 withDefaultPasswordEncoder() 方法。
     * </p>
     *
     * @return 返回密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 暴露 {@link AuthenticationManager} Bean。
     * <p>
     * 重写 authenticationManagerBean 方法，以便在其他组件中使用 AuthenticationManager 进行认证管理。
     * </p>
     *
     * @return 返回认证管理器
     * @throws Exception 如果配置失败
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置 HTTP 安全策略。
     * <p>
     * 配置内容包括：
     * 1. 禁用 CSRF 保护。
     * 2. 设置无状态会话管理策略。
     * 3. 配置请求的访问权限。
     * 4. 配置自定义异常处理器。
     * 5. 添加自定义JWT认证过滤器。
     * </p>
     *
     * @param http HttpSecurity 对象，用于配置安全策略
     * @throws Exception 如果配置失败
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用 CSRF 保护，适用于前后端分离的场景
        http.csrf().disable()
                // 设置无状态会话管理策略，不使用 Session 存储安全上下文
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 配置请求的访问权限
                .and().authorizeRequests()
                // ADMIN
                .antMatchers("/admin/**").hasRole("ADMIN") // 只有 ADMIN 角色可以访问 /admin/** 路径
                // .antMatchers("/admin/greet/**").hasAuthority("READ_PRIVILEGES") // 对应的权限配置：只有拥有 READ_PRIVILEGES 权限的用户才能访问 /admin/greet
                // .antMatchers("/admin/echo").hasAuthority("WRITE_PRIVILEGES") // 对应的权限配置：只有拥有 WRITE_PRIVILEGES 权限的用户才能访问 /admin/echo
                // .antMatchers("/admin/sum/**").hasAuthority("DELETE_PRIVILEGES") // 对应的权限配置：只有拥有 DELETE_PRIVILEGES 权限的用户才能访问 /admin/sum
                // .antMatchers("/admin/multiply/**").hasAuthority("UPDATE_PRIVILEGES") // 对应的权限配置：只有拥有 UPDATE_PRIVILEGES 权限的用户才能访问 /admin/multiply
                // USER
                .antMatchers("/user/**").hasRole("USER")//
                .antMatchers("/user/greet/**").hasAuthority("READ_PRIVILEGES")//
                .antMatchers("/user/echo").hasAuthority("WRITE_PRIVILEGES")//
                .antMatchers("/user/sum/**").hasAnyAuthority("DELETE_PRIVILEGES")//
                .antMatchers("/user/multiply/**").hasAnyAuthority("UPDATE_PRIVILEGES") // 对应的权限配置：只有拥有 UPDATE_PRIVILEGES 权限的用户才能访问 /admin/multiply
                .anyRequest().authenticated() // 其他所有请求都允许访问
                // 禁用默认注销接口，避免 Spring Security 修改 /logout 行为
                .and().logout().disable()
                // 将自定义 JWT 认证过滤器添加到 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(serviceJwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置自定义异常处理器
                .exceptionHandling()//
                .accessDeniedHandler(serviceAccessDeniedHandler) // 处理已认证用户访问被拒绝的资源（没有足够权限时）
                .authenticationEntryPoint(serviceAuthenticationEntryPoint); // 设置自定义认证入口点
    }
}
