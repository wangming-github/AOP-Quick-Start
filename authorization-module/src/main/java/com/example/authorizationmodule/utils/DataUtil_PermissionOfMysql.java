package com.example.authorizationmodule.utils;

import com.example.servicemodule.entity.PermissionOfMysql;
import com.example.servicemodule.entity.RoleOfMysql;
import lombok.extern.slf4j.Slf4j;
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
public class DataUtil_PermissionOfMysql {
    private List<PermissionOfMysql> permissionOfMysqls;


    private DataUtil_PermissionOfMysql() {
        this.permissionOfMysqls = new ArrayList<>();
        permissionOfMysqls.add(new PermissionOfMysql(1L, "READ_PERMISSION"));
        permissionOfMysqls.add(new PermissionOfMysql(2L, "WRITE_PERMISSION"));
        permissionOfMysqls.add(new PermissionOfMysql(3L, "DELETE_PERMISSION"));
    }

    public List<PermissionOfMysql> findPermissionById(Long roleId) {
        return permissionOfMysqls.stream().filter(x -> x.getId().equals(roleId)).collect(Collectors.toList());
    }

    public Optional<PermissionOfMysql> findOnePermissionById(Long roleId) {
        return permissionOfMysqls.stream().filter(x -> x.getId().equals(roleId)).findFirst();
    }

}
