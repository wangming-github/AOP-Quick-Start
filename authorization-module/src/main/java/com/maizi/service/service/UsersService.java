package com.maizi.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.service.entity.UsersEntity;
import com.maizi.common.utils.PageUtils;

import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UsersEntity getUserByName(String username);

    // 示例：对已有密码进行重新编码
    void reEncodePasswords();
}

