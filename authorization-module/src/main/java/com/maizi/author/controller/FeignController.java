package com.maizi.author.controller;

import com.maizi.author.feign.ServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private ServiceFeignClient myFeignClient;

    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return myFeignClient.greet(name);
    }

    @PostMapping("/echo")
    public String echo(@RequestBody String message) {
        return myFeignClient.echo(message);
    }

    @GetMapping("/sum/{a}/{b}")
    public String sum(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.sum(a, b);
    }

    @GetMapping("/multiply/{a}/{b}")
    public String multiply(@PathVariable int a, @PathVariable int b) {
        return myFeignClient.multiply(a, b);
    }
}
