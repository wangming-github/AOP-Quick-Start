package com.example.authorizationmodule.utils;

import com.example.servicemodule.entity.UserOfMysql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author maizi
 */
@Slf4j
@Component
public class DataUtil_UserOfMysql {
    private List<UserOfMysql> userOfMysqls;
    private PasswordEncoder passwordEncoder;


    public DataUtil_UserOfMysql() {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userOfMysqls = new ArrayList<>();
        // 初始化一些数据
        userOfMysqls.add(new UserOfMysql(1L, "user", passwordEncoder.encode("user")));
        userOfMysqls.add(new UserOfMysql(2L, "admin", passwordEncoder.encode("admin")));
        log.info("↘↘【辅助-mysql】模拟在mysql中添加账户：1L/user/user");
        log.info("↘↘【辅助-mysql】模拟在mysql中添加账户：2L/admin/admin");
    }


    public List<UserOfMysql> getAllUsers() {
        return userOfMysqls;
    }

    public Optional<UserOfMysql> getFirstUserByName(String name) {
        return userOfMysqls.stream().filter(user -> user.getUserName().equals(name)).findFirst();
    }

    public UserOfMysql getFirstUserByNameOrThrow(String name) {
        log.info("↘↘【辅助-mysql】根据用户名查询出登录用户信息");
        return userOfMysqls.stream().filter(user -> user.getUserName().equals(name)).findFirst().orElseThrow(() -> new RuntimeException("用户名或密码错误"));
    }

    public Optional<UserOfMysql> getUserByName(String name) {
        return userOfMysqls.stream().filter(userOfMysql -> userOfMysql.getUserName().equals(name)).findFirst();
    }

    public boolean validateUser(String name, String password) {
        return userOfMysqls.stream().anyMatch(userOfMysql -> userOfMysql.getUserName().equals(name) && userOfMysql.getPassword().equals(password));
    }

    public void addUser(Long id, String name, String password) {
        userOfMysqls.add(new UserOfMysql(id, name, password));
    }
}
