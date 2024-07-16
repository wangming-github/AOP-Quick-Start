package com.maizi.authorizationmodule.service.impl;

import com.maizi.authorizationmodule.module.CustomUserUserDetails;
import com.maizi.authorizationmodule.module.UserOfRequest;
import com.maizi.authorizationmodule.service.LoginService;
import com.maizi.authorizationmodule.utils.RedisServiceUtil;
import com.maizi.authorizationmodule.utils.JwtUtil;
import com.maizi.common.utils.R;
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
    private RedisServiceUtil redisServiceUtil;

    @Override
    public R login(UserOfRequest userOfRequest) {
        log.info("【1】根据登录的用户名密码信息创建UsernamePasswordAuthenticationToken对象");
        // 使用authenticationManager帮助我们进行用户认证
        UsernamePasswordAuthenticationToken loginAuthenticate = new UsernamePasswordAuthenticationToken(userOfRequest.getUsername(), userOfRequest.getPassword());
        log.info("【2】使用AuthenticationManager验证登录信息UsernamePasswordAuthenticationToken对象");

        // 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(loginAuthenticate);
        // 认证结果成功
        if (authenticate.isAuthenticated()) {
            log.info("登录成功，获取登录的用户信息");
            CustomUserUserDetails user = (CustomUserUserDetails) authenticate.getPrincipal();
            log.info("↘将登录用户信息存放进redis。");
            redisServiceUtil.addUser(user.getUsername(), user);

            log.info("↘根据用户名生成token，返回前端。");
            String token = jwtUtil.generateToken(user.getUsername());
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("code", HttpServletResponse.SC_OK);
            tokenMap.put("token", token);
            tokenMap.put("msg", "认证成功!");
            return R.ok(tokenMap);
        } else {
            log.info("登录失败");
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
        redisServiceUtil.removeUser(username);
        return R.ok("注销成功!");
    }

}
