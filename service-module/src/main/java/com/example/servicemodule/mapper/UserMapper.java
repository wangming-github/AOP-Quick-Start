package com.example.servicemodule.mapper;

import com.example.servicemodule.entity.UserOfMysql;

import java.util.List;
//
// @Mapper
// public interface UserMapper extends BaseMapper<UserOfMysql> {
//     // 这里可以定义一些自定义的查询方法，如根据用户名查询用户等
// }


public interface UserMapper {
    // 可以根据需要添加一些自定义的查询方法

    List<UserOfMysql> findByUserId(Long userId);

}