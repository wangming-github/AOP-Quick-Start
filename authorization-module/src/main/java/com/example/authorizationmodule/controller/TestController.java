package com.example.authorizationmodule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    // GET请求，返回数据示例
    @GetMapping("/get")
    public String getExample() {

        return "This is a GET request example!";
    }

    // POST请求，接收数据示例
    @PostMapping("/post")
    public String postExample(@RequestBody String data) {

        return "Received POST data: " + data;
    }
}
