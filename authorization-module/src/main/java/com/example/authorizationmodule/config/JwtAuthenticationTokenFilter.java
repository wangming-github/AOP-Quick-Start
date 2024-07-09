package com.example.authorizationmodule.config;

import com.example.authorizationmodule.exception.MyException;
import com.example.authorizationmodule.utils.JwtUtil;
import com.example.authorizationmodule.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {


    @PostConstruct// 实例被依赖注入完成后,打印信息。
    public void init() {
        log.info("【自定义过滤器-JwtAuthenticationTokenFilter】已经加入容器中。");
    }

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisCache cache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws MyException, ServletException, IOException {


        // 获取token
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            log.info("【自定义过滤器-1】请求[没有]携带的token,需要经过后续过滤器校验...");
            filterChain.doFilter(request, response);  // 放行
            return;
        }
        log.info("【自定义过滤器-1】请求携带的token:" + token);

        // 使用 Optional 和 Lambda 表达式处理异常
        // 如果 jwtUtil.getUsernameFromToken(token) 抛出异常，则将会抛出 ForbiddenException 异常，并带有 "token非法" 的错误信息。
        // String userName = Optional.ofNullable(jwtUtil.getUsernameFromToken(token)).orElseThrow(() -> new ForbiddenException("token非法"));
        // 解析token
        String userName;
        try {
            userName = jwtUtil.getUsernameFromToken(token);
            log.info("【自定义过滤器-2】请求携带Token中解析出的用户名:" + userName);
        } catch (Exception e) {
            log.info("【自定义过滤器-2】[没有]解析出请求中携带Token的用户名!");
            throw new MyException("token非法");
        }


        // UserDetails userDetails = cache.getUser(userName);
        // // Optional.ofNullable：将可能为 null 的对象包装为 Optional 对象。
        // // 如果 userDetails 返回 null，则 userDetailsOpt 将是一个空的 Optional 对象。
        // Optional<UserDetails> userDetailsOpt = Optional.ofNullable(userDetails);
        // // orElseThrow：如果 userDetailsOpt 是空的（即 cache.getUser(userName) 返回 null），
        // // 则通过 Lambda 表达式抛出 ForbiddenException 异常，异常消息为 "用户未登录，登录信息过期!"。
        // userDetailsOpt.orElseThrow(() -> new ForbiddenException("用户未登录，登录信息过期!"));
        // 等同于
        // 从redis中获取用户信息
        UserDetails userDetails = cache.getUser(userName);

        if (Objects.isNull(userDetails)) {
            log.info("【自定义过滤器-3】[没有]在redis中找到用户名" + userName + "的缓存信息");
            throw new MyException("登录信息过期!");
        }
        log.info("【自定义过滤器-3】解析出的用户名查询redis中的 UserDetails 缓存信息:" + userDetails.getUsername() + "/" + userDetails.getPassword());

        // 存入SecurityContextHolder
        log.info("【自定义过滤器-4】获取UserDetails权限信息封装到第三个参数 Authentication中");
        // TODO 获取权限信息封装到第三个参数 Authentication中
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        // 将登录信息设置到容器中
        log.info("【自定义过滤器-5】将Authentication登录信息设置到容器中");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}