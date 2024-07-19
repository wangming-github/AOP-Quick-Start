package com.maizi.serve.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.serve.entity.RolePermissionsEntity;
import com.maizi.serve.utils.PageUtils;
import org.apache.ibatis.javassist.runtime.Inner;

import java.util.List;
import java.util.Map;

/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
public interface RolePermissionsService extends IService<RolePermissionsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据角色信息查询【角色-权限】信息
     */
    List<RolePermissionsEntity> findByRoleId(List<Integer> roles);
}

