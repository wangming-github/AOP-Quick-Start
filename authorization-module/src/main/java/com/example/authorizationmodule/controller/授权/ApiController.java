package com.example.authorizationmodule.controller.授权;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {


    private static int count = 1;

    @Secured("ROLE_USER")
    @GetMapping("/user")
    @ResponseBody
    public String userEndpoint() {
        String message = "This is a user endpoint. Only users with ROLE_USER can access this.";
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/hasRoleUser")
    @ResponseBody
    public String user() {
        String message = "@PreAuthorize(\"hasRole('USER')\")";
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }

    @Secured("ADMIN")
    @GetMapping("/admin")
    @ResponseBody
    public String adminEndpoint() {
        String message = "This is an admin endpoint. Only users with ROLE_ADMIN can access this.";
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }


    @PreAuthorize("hasAnyAuthority('READ_PERMISSION')")
    @GetMapping("/preAuthorizeUser")
    @ResponseBody
    public String preAuthorizeUserEndpoint() {
        String message = "This is a user endpoint secured with @PreAuthorize.";
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }


    @GetMapping("/read")
    @ResponseBody
    @PreAuthorize("hasAuthority('READ_PERMISSION')")
    public String readUserInfo() {
        String message = "Read User Info"; // 只有具有 READ_USER 权限的用户才能访问该接口
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }

    @GetMapping("/write")
    @ResponseBody
    @PreAuthorize("hasAuthority('WRITE_PERMISSION')")
    public String writeUserInfo() {
        String message = "Write User Info"; // 只有具有 WRITE_USER 权限的用户才能访问该接口
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }


    @GetMapping("/public")
    @ResponseBody
    public String publicEndpoint() {
        String message = "This is a public endpoint. Anyone can access this.";
        log.info("第{}号 - 返回信息: {}", count++, message);
        return message;
    }

}