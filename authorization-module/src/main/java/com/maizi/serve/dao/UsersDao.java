package com.maizi.serve.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.maizi.serve.entity.UsersEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
@Mapper
public interface UsersDao extends BaseMapper<UsersEntity> {

}