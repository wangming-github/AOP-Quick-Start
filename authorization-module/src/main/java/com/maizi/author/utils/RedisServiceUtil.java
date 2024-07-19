package com.maizi.author.utils;

import com.maizi.author.module.CustomUserUserDetails;
import com.maizi.common.constants.ModuleType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * 模拟Redis存储
 *
 * @author maizi
 */
@Slf4j
@Component
@EnableScheduling
public class RedisServiceUtil {

    private Map<String, CustomUserUserDetails> cache = new HashMap<>();

    // @Scheduled(cron = "0 0 0 */30 * *") // 每隔30天执行一次
    // public void cleanMap() {
    //     cache.clear();
    //     log.info(ModuleType.AUTHORIZATION_MODULE + "↘↘【辅助-redis】数据已经清空。");
    // }

    @PostConstruct
    public void init() {
        // InMemoryUserCache实例被依赖注入完成后，初始化一些示例用户数据。
    }


    public CustomUserUserDetails getUser(String username) {
        return cache.get(username);
    }

    public void addUser(String username, CustomUserUserDetails userDetails) {
        cache.put(username, userDetails);
        log.info(ModuleType.AUTHORIZATION_MODULE + "↘↘【辅助-redis】Redis添加新的用户信息【K:{},V:{}】", username, userDetails);
    }

    public void removeUser(String username) {
        log.info(ModuleType.AUTHORIZATION_MODULE + "↘↘【辅助-redis】Redis清除用户" + username + "的登录信息!");
        cache.remove(username);
    }
}