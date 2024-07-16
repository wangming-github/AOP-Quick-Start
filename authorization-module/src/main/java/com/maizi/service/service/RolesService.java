package com.maizi.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.common.utils.PageUtils;
import com.maizi.service.entity.RolesEntity;

import java.util.List;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
public interface RolesService extends IService<RolesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RolesEntity> findRolesByUserId(Integer id);
}

