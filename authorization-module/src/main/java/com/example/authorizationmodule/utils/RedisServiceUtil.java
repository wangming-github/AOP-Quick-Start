package com.example.authorizationmodule.utils;

import com.example.authorizationmodule.module.CustomUserUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
public class RedisServiceUtil {

    private Map<String, CustomUserUserDetails> cache = new HashMap<>();

    @Scheduled(cron = "0 0 0 */30 * *") // 每隔30天执行一次
    public void cleanMap() {
        cache.clear();
        log.info("↘↘【辅助-redis】数据已经清空。");
    }

    @PostConstruct
    public void init() {
        // InMemoryUserCache实例被依赖注入完成后，初始化一些示例用户数据。
    }


    public CustomUserUserDetails getUser(String username) {
        return cache.get(username);
    }

    public void addUser(String username, CustomUserUserDetails userDetails) {
        cache.put(username, userDetails);
        log.warn("↘↘【辅助-redis】Redis添加新的用户信息【K: {},V:{}】", username, userDetails);
    }

    public void removeUser(String username) {
        log.warn("↘↘【辅助-redis】Redis清除用户" + username + "的登录信息!");
        cache.remove(username);
    }
}
