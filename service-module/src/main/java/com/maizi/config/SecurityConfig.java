package com.maizi.config;

import com.maizi.exception.MyAccessDeniedHandler;
import com.maizi.exception.MyAuthenticationEntryPoint;
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
 * @author maizii
 */
@Slf4j
@Configuration
@EnableGlobalMethodSecurity(//
        prePostEnabled = true, // @PreAuthorize 功能: @PreAuthorize注解用于在方法执行之前进行权限验证。它使用SpEL（Spring Expression Language）表达式来定义权限规则，非常灵活。
        securedEnabled = true)//@Secured 功能: @Secured注解用于在方法级别进行权限控制。它依赖于Spring Security的角色机制，主要用于简单的角色检查。
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenFilter2 jwtTokenFilter2;


    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler; // 注入自定义的访问拒绝处理器

    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint; // 注入自定义的认证入口点


    /**
     * withDefaultPasswordEncoder() 方法已经被标记为过时使用
     * BCryptPasswordEncoder 来替代 withDefaultPasswordEncoder()
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 重写authenticationManager方法 暴露Bean：AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置一个简单的内存用户
     */
    // @Bean
    // public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    //     PasswordEncoder passwordEncoder = passwordEncoder();
    //
    //     String encodedPasswordAdmin = passwordEncoder.encode("admin");
    //     String encodedPasswordUser = passwordEncoder.encode("user");
    //
    //     UserDetails admin = User.withUsername("admin").password(encodedPasswordAdmin).roles("ADMIN").build();
    //     UserDetails user = User.withUsername("user").password(encodedPasswordUser).roles("USER").build();
    //
    //     log.warn("【辅助-mysql】模拟在mysql中添加账户：admin/admin");
    //     log.warn("【辅助-mysql】模拟在mysql中添加账户：user/user");
    //     return new InMemoryUserDetailsManager(admin, user);
    // }


    /**
     * 配置 HTTP 安全策略。
     *
     * @param http HttpSecurity 对象，用于配置安全策略
     * @throws Exception 如果配置失败
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // 禁用 CSRF 保护，适用于前后端分离的场景
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 设置无状态会话管理策略，不使用 Session 存储安全上下文

        /**
         * TODO
         * 这次是用角色不对的用户访问，出现异常但是没有抛出自定义的
         * // 配置自定义异常处理器
         *         http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler) // 设置自定义访问拒绝处理器
         *                 .authenticationEntryPoint(myAuthenticationEntryPoint); // 设置自定义认证入口点
         *  配置失效了
         */


        http.authorizeRequests() // 配置请求的访问权限
                .regexMatchers("/admin/.*").hasRole("ADMIN")// regexMatchers 方法 用于使用正则表达式来匹配路径。
                .regexMatchers("/user/.*").hasRole("USER") // regexMatchers 方法 用于使用正则表达式来匹配路径。
                .antMatchers("/moderator").hasRole("MODERATOR") // antMatchers 方法 用于指定具体的路径。
                .anyRequest().authenticated(); // 其他所有请求必须经过认证


        http.logout().disable(); // 禁用默认注销接口，避免 Spring Security 修改 /logout 行为

        // 将自定义 JWT 认证过滤器添加到 UsernamePasswordAuthenticationFilter 之前
        http.addFilterBefore(jwtTokenFilter2, UsernamePasswordAuthenticationFilter.class);

        // 配置自定义异常处理器
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler) // 设置自定义访问拒绝处理器
                .authenticationEntryPoint(myAuthenticationEntryPoint); // 设置自定义认证入口点

    }


}
