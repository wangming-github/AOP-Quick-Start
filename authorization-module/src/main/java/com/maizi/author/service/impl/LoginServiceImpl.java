package com.maizi.author.service.impl;

import com.maizi.author.module.CustomUserUserDetails;
import com.maizi.author.utils.RedisUtil;
import com.maizi.common.constants.ModuleType;
import com.maizi.common.dto.UserDTO;
import com.maizi.author.service.LoginService;
import com.maizi.author.utils.JwtUtil;
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
    private RedisUtil redisUtil;

    @Override
    public R login(UserDTO userDTO) {
        log.info(ModuleType.AUTHORIZATION_MODULE + "【1】根据登录的用户名密码信息创建UsernamePasswordAuthenticationToken对象");
        // 使用authenticationManager帮助我们进行用户认证
        UsernamePasswordAuthenticationToken loginAuthenticate = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        log.info(ModuleType.AUTHORIZATION_MODULE + "【2】使用AuthenticationManager验证登录信息UsernamePasswordAuthenticationToken对象");

        // 进行用户认证
        Authentication authenticate = authenticationManager.authenticate(loginAuthenticate);
        // 认证结果成功
        if (authenticate.isAuthenticated()) {
            log.info(ModuleType.AUTHORIZATION_MODULE + "登录成功，获取登录的用户信息");
            CustomUserUserDetails user = (CustomUserUserDetails) authenticate.getPrincipal();
            redisUtil.set(user.getUsername(), user);
            log.info(ModuleType.AUTHORIZATION_MODULE + "↘将登录用户信息存放进redis,K:{}V:{}", user.getUsername(), user);
            String token = jwtUtil.generateToken(user.getUsername());
            Map<String, Object> tokenMap = new HashMap<>();
            tokenMap.put("code", HttpServletResponse.SC_OK);
            tokenMap.put("token", token);
            tokenMap.put("msg", "认证成功!");
            log.info(ModuleType.AUTHORIZATION_MODULE + "↘根据用户名生成token，返回前端。");
            return R.ok(tokenMap);
        } else {
            log.info(ModuleType.AUTHORIZATION_MODULE + "登录失败");
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
        log.info(ModuleType.AUTHORIZATION_MODULE + "登出用户：" + username);
        redisUtil.delete(username);
        return R.ok("注销成功!");
    }

}
