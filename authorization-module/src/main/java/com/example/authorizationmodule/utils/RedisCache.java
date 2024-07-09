package com.example.authorizationmodule.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 模拟Redis存储
 */
@Slf4j
@Component
@EnableScheduling
public class RedisCache {

    private Map<String, UserDetails> cache = new HashMap<>();

    // @Scheduled(fixedDelay = 1000000) // 每隔100秒执行一次
    // public void cleanMap() {
    //     cache.clear();
    //     log.warn("【辅助-redis】Redis中UserDetails的数据清除...");
    // }

    @PostConstruct// InMemoryUserCache实例被依赖注入完成后，初始化一些示例用户数据。
    public void init() {
        UserDetails user1 = User.withUsername("user1").password("{noop}user").roles("USER").build();
        cache.put("user1", user1);
    }


    public UserDetails getUser(String username) {
        return cache.get(username);
    }

    public void addUser(String username, UserDetails userDetails) {
        cache.put(username, userDetails);
        log.warn("【辅助-redis】Redis添加新的UserDetails用户信息【K: " + username + ",V:UserDetails信息】");
    }

    public void removeUser(String username) {
        log.warn("【辅助-redis】Redis清除用户" + username + "的登录信息!");
        cache.remove(username);
    }
}
