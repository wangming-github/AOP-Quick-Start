package com.maizi.authorizationmodule.config;

import com.maizi.authorizationmodule.exception.MyAccessDeniedHandler;
import com.maizi.authorizationmodule.exception.MyAuthenticationEntryPoint;
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

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(//
        prePostEnabled = true, // @PreAuthorize 功能: @PreAuthorize注解用于在方法执行之前进行权限验证。它使用SpEL（Spring Expression Language）表达式来定义权限规则，非常灵活。
        securedEnabled = true)//@Secured 功能: @Secured注解用于在方法级别进行权限控制。它依赖于Spring Security的角色机制，主要用于简单的角色检查。
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;


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


    //  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()// 关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 前后端分离，session基本没用。不通过Session获取SecurityContext
        http.authorizeRequests()// 请求认证
                .antMatchers("/login").anonymous()// /login允许匿名访问，携带token不允许访问
                .antMatchers("/api/admin").hasRole("ADMIN")// 1.这里不添加这些配置，
                .antMatchers("/api/user").hasRole("USER")// 2.MyAccessDeniedHandler 和 MyAuthenticationEntryPoint
                .antMatchers("/api/moderator").hasRole("MODERATOR")// 3.不会返回指定异常，会被全局异常捕获。
                .anyRequest().authenticated(); // 除上面外的所有请求全部需要鉴权认证

        // 关闭默认注销接口,否则/logout会被spring security修改
        http.logout().disable();

        // 将自定义的过滤器添加到UsernamePasswordAuthenticationFilter过滤器之前，进行一开始的检查
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置自定义异常处理器
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler).authenticationEntryPoint(myAuthenticationEntryPoint);

    }


}
