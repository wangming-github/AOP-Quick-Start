package com.maizi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/admin")
public class adminController {

    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello,READ_PRIVILEGES : " + name + "!";
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGES')")
    @PostMapping("/echo")
    public String echo(@RequestBody String message) {
        return "Hello, WRITE_PRIVILEGES:" + message;
    }

    @PreAuthorize("hasAuthority('DELETE_PRIVILEGES')")
    @GetMapping("/sum/{a}/{b}")
    public String sum(@PathVariable int a, @PathVariable int b) {
        return "Hello,DELETE_PRIVILEGES: " + (a + b);
    }

    @PreAuthorize("hasAuthority('UPDATE_PRIVILEGES')")
    @GetMapping("/multiply/{a}/{b}")
    public String multiply(@PathVariable int a, @PathVariable int b) {
        return "Hello, UPDATE_PRIVILEGES:" + (a * b);
    }


}
