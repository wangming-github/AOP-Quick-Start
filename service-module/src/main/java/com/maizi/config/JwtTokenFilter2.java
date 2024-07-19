package com.maizi.config;

import com.maizi.common.constants.ModuleType;
import com.maizi.module.CustomUserUserDetails;
import com.maizi.utils.JwtUtil;
import com.maizi.common.exception.RRException;
import com.maizi.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;


/**
 * 定义Jwt过滤器
 * 1.获取token
 * 2.解析token中的用户名
 * 3.从reids中根据用户名获取用户信息
 * 4.将用户信息存放在SecurityContextHolder
 */
// 自定义过滤器-用户在登录后的请求中只要在请求头中添加了token字段并且值是登录成功后的响应值
// 此过滤器将会首先进行过滤，不用经过后续的验证，直接将用户信息存放在容器中

@Slf4j
@Component
public class JwtTokenFilter2 extends OncePerRequestFilter {


    @PostConstruct// 实例被依赖注入完成后,打印信息。
    public void init() {
        log.info(ModuleType.SERVICE_MODULE + "【service-module自定义过滤器-JwtAuthenticationTokenFilter】已经加入容器中。");
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            log.info(ModuleType.SERVICE_MODULE + "请求[没有]携带的token,需要经过后续过滤器校验...");
            filterChain.doFilter(request, response);  // 放行
            return;
        }
        // 解析token
        String userName;
        try {
            userName = jwtUtil.getUsernameFromToken(token);
        } catch (Exception e) {
            throw new RRException("token解析失败");
        }


        // 从redis中获取用户信息
        CustomUserUserDetails user = redisUtil.get(userName, CustomUserUserDetails.class);
        if (Objects.isNull(user)) {
            log.info(ModuleType.SERVICE_MODULE + "[没有]在redis中找到用户名" + userName + "的缓存信息");
            throw new RRException("登录信息过期!");
        }
        log.info(ModuleType.SERVICE_MODULE + "请求携带的token,解析出用户名【{}】，其缓存信息:{}", user.getUsername(), user.getAuthorities());

        // 将UserOfLoggedIn封装成Authentication，存放进SecurityContextHolder
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行,继续执行后续过滤器。
        filterChain.doFilter(request, response);

    }
}