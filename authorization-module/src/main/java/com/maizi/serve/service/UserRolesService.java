package com.maizi.serve.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.serve.entity.UserRolesEntity;
import com.maizi.serve.utils.PageUtils;

import java.util.Map;

/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
public interface UserRolesService extends IService<UserRolesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

