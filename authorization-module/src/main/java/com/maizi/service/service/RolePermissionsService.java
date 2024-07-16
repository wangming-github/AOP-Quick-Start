package com.maizi.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.common.utils.PageUtils;
import com.maizi.service.entity.RolePermissionsEntity;
import com.maizi.service.entity.RolesEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
public interface RolePermissionsService extends IService<RolePermissionsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<RolePermissionsEntity> findAllPermissionsEntityByRole(List<RolesEntity> roles);
}

