package com.maizi.author.controller;

import com.maizi.author.service.AuthorService;
import com.maizi.common.dto.UserDTO;
import com.maizi.author.service.LoginService;
import com.maizi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class LoginController {


    @Autowired
    LoginService loginService;

    @Autowired
    AuthorService authorService;

    /**
     * 处理用户登录请求，并返回 JWT Token。
     *
     * @param userDTO 包含用户名和密码的请求体
     * @return JWT Token，用于后续身份验证
     */
    @PostMapping("/login")
    public R login(@RequestBody UserDTO userDTO) {
        return loginService.login(userDTO);
    }

    /**
     * 处理POST请求的/logout
     * http.logout().disable();
     */

    @PostMapping("/logout")
    public R logoutPost() {
        return loginService.logout();
    }
}


