package com.maizi.service.controller;


import java.util.Arrays;
import java.util.Map;

import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.R;
import com.maizi.service.entity.RolePermissionsEntity;
import com.maizi.service.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
@RestController
@RequestMapping("XXXXX/rolepermissions")
public class RolePermissionsController {

    @Autowired
    private RolePermissionsService rolePermissionsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = rolePermissionsService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    public R info(@PathVariable("roleId") Integer roleId) {
        RolePermissionsEntity rolePermissions = rolePermissionsService.getById(roleId);

        return R.ok().put("rolePermissions", rolePermissions);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody RolePermissionsEntity rolePermissions) {
        rolePermissionsService.save(rolePermissions);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody RolePermissionsEntity rolePermissions) {
        rolePermissionsService.updateById(rolePermissions);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] roleIds) {
        rolePermissionsService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
