package com.example.servicemodule.mapper;

import com.example.servicemodule.entity.RoleOfMysql;

import java.util.List;

// @Mapper
// public interface RoleMapper extends BaseMapper<Role> {
//     // 可以根据需要添加一些自定义的查询方法
// }


public interface RoleMapper {
    // 可以根据需要添加一些自定义的查询方法

    List<RoleOfMysql> findByUserId(Long userId);

}