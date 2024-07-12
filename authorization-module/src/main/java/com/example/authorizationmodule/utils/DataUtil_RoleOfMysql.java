package com.example.authorizationmodule.utils;

import com.example.servicemodule.entity.PermissionOfMysql;
import com.example.servicemodule.entity.RoleOfMysql;
import com.example.servicemodule.entity.UserOfMysql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author maizi
 */
@Slf4j
@Component
public class DataUtil_RoleOfMysql {
    private List<RoleOfMysql> roles;

    public DataUtil_RoleOfMysql() {
        this.roles = new ArrayList<>();
        // 初始化一些数据
        roles.add(new RoleOfMysql(1L, "USER"));
        roles.add(new RoleOfMysql(2L, "ADMIN"));
    }

    public List<RoleOfMysql> findRolesById(Long roleId) {
        return roles.stream().filter(role -> role.getId().equals(roleId)).collect(Collectors.toList());
    }

    public Optional<RoleOfMysql> findOneRoleById(Long roleId) {
        return roles.stream().filter(role -> role.getId().equals(roleId)).findFirst();
    }

}
