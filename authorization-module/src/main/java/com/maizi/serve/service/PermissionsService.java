package com.maizi.serve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.serve.entity.PermissionsEntity;
import com.maizi.serve.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
public interface PermissionsService extends IService<PermissionsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> findPermissionNameById(List<Integer> ids);
}
