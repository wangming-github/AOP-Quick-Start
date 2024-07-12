package com.example.servicemodule.mapper;

import com.example.servicemodule.entity.PermissionOfMysql;

import java.util.List;

// @Mapper
// public interface PermissionMapper extends BaseMapper<Permission> {
//     // 可以根据需要添加一些自定义的查询方法
// }


public interface PermissionMapper {
    // 可以根据需要添加一些自定义的查询方法

    List<PermissionOfMysql> findByUserId(Long userId);

}