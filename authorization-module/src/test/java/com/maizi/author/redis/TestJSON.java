package com.maizi.author.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maizi.author.module.CustomUserUserDetails;
import com.maizi.author.utils.RedisUtil;
import com.maizi.common.dto.UserDetailsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class TestJSON {

    @Autowired
    RedisUtil redisUtil;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    String USER_KEY_PREFIX = "user:";

    public User initUser() {
        User user = new User();
        user.setId("1");
        user.setName("John Doe");
        user.setAge(30);

        UserAddress userAddress1 = new UserAddress();
        userAddress1.setStreet("123 Main St");
        userAddress1.setCity("Springfield");

        UserAddress userAddress2 = new UserAddress();
        userAddress2.setStreet("456 Elm St");
        userAddress2.setCity("Shelbyville");
        user.setUserAddresses(Arrays.asList(userAddress1, userAddress2));

        return user;
    }


    @Test
    public void test1() {
        System.out.println("存数据");
        User user = initUser();
        redisTemplate.opsForValue().set("test", user);
        User user1 = (User) redisTemplate.opsForValue().get("test");
        System.out.println("取出数据：" + user1);
    }


    @Test
    public void test2() throws JsonProcessingException {
        User user = initUser();
        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        // 将对象序列化为JSON字符串
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);

        // 创建ObjectMapper对象

        // 将JSON字符串反序列化为User对象
        User resultUser = objectMapper.readValue(json, User.class);
        // 打印反序列化后的对象
        System.out.println(resultUser.getName());
        System.out.println(resultUser.getUserAddresses());

    }


    public CustomUserUserDetails initCustomUserUserDetails() {
        CustomUserUserDetails customUserUserDetails = new CustomUserUserDetails();

        List<String> list = Arrays.asList("权限A", "权限B", "权限C");
        UserDetailsDTO wangMing = UserDetailsDTO.builder().usersId(1).usersUsername("wangMing")//
                .permissionNames(Arrays.asList("权限A", "权限B", "权限C"))//
                .rolesNames(Arrays.asList("角色A", "角色B", "角色C"))//
                .usersPassword("123").build();//
        customUserUserDetails.setUser(wangMing);

        return customUserUserDetails;
    }


    @Test
    public void 只操作Json() throws JsonProcessingException {


        // 创建ObjectMapper对象
        ObjectMapper objectMapper = new ObjectMapper();

        CustomUserUserDetails userDetails = initCustomUserUserDetails();
        // 将对象序列化为JSON字符串
        String json = objectMapper.writeValueAsString(userDetails);
        System.out.println("将对象序列化为JSON字符串" + json);

        // 创建ObjectMapper对象
        // 将JSON字符串反序列化为User对象
        CustomUserUserDetails resultUser = objectMapper.readValue(json, CustomUserUserDetails.class);
        System.out.println("将JSON字符串反序列化为User对象" + resultUser);
        // 打印反序列化后的对象
        System.out.println(resultUser.getUsername());
        System.out.println(resultUser.getAuthorities());
    }

    @Test
    public void 通过redis实现序列化反序列化() {

        CustomUserUserDetails userDetails = initCustomUserUserDetails();

        // 将对象序列化为JSON字符串
        String key = "test1";
        redisUtil.set(key, userDetails);
        // 将JSON字符串反序列化为User对象


        CustomUserUserDetails resultUser = redisUtil.get(key, CustomUserUserDetails.class);
        System.out.println("将JSON字符串反序列化为User对象" + resultUser);
        System.out.println(resultUser.getUsername());
        System.out.println(resultUser.getAuthorities());
    }


}


