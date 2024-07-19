package com.maizi.author.controller.授权;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/admin")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Admin endpoint accessed");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("User endpoint accessed");
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<String> moderatorEndpoint() {
        return ResponseEntity.ok("Moderator endpoint accessed");
    }

    @GetMapping("/guest")
    @Secured("IS_AUTHENTICATED_ANONYMOUSLY")// 允许匿名用户访问受保护的资源
    public ResponseEntity<String> guestEndpoint() {
        return ResponseEntity.ok("Guest endpoint accessed");
    }

}