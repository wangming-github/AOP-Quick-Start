package com.example.authorizationmodule.service;

import com.example.authorizationmodule.utils.RedisCache;
import com.example.authorizationmodule.module.MyUser;
import com.example.authorizationmodule.module.R;
import com.example.authorizationmodule.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisCache redisCache;

    @Override
    public R<Map<String, String>> login(MyUser myUser) {
        log.info("【1】根据登录的用户名密码信息创建UsernamePasswordAuthenticationToken对象");
        // 使用authenticationManager帮助我们进行用户认证
        UsernamePasswordAuthenticationToken loginAuthenticate = new UsernamePasswordAuthenticationToken(myUser.getUsername(), myUser.getPassword());
        log.info("【2】使用AuthenticationManager验证登录信息UsernamePasswordAuthenticationToken对象");

        // 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(loginAuthenticate);
        // 认证结果成功
        if (authenticate.isAuthenticated()) {
            log.info("【3】登录信息认证成功，返回Authentication（包含权限信息）");

            // 返回用户信息及其权限信息
            // 方案A：实际开发中重写UserDetailsServiceImpl,用户信息存储在LoginUser。
            // LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

            // 方案B：用户是存储在inMemoryUserDetailsManager中所以用UserDetails接收
            log.info("【4】根据Authentication获取UserDetails（包含权限信息）");
            UserDetails user = (UserDetails) authenticate.getPrincipal();
            String token = jwtUtil.generateToken(user.getUsername());
            log.info("【5】根据UserDetails获取用户名，并且将用户名生成jwt加密后的token:" + token);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            log.info("【6】在redis中存储k:用户名，V:" + user.getUsername() + ",UserDetails（包含权限信息）");
            redisCache.addUser(user.getUsername(), user);
            log.info("【7】将jwt加密后的用户名token返回给客户端。");
            return R.success(HttpServletResponse.SC_OK, "认证成功!", tokenMap);
        } else {
            log.info("【3】登录信息认证失败");
            return R.error(HttpServletResponse.SC_FORBIDDEN, "认证失败!");
        }


    }

    @Override
    public R logout() {

        // 从SecurityContextHolder中获取Authorization信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 获取容器中当前登录的用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 获取用户名
        String username = userDetails.getUsername();
        log.info("登出：" + username);
        redisCache.removeUser(username);

        log.info("注销成功");
        return R.success(HttpServletResponse.SC_OK, "注销成功!");
    }

}
