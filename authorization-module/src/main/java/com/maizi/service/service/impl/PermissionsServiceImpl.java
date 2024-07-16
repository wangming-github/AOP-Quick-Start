package com.maizi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.Query;
import com.maizi.service.dao.PermissionsDao;
import com.maizi.service.entity.PermissionsEntity;
import com.maizi.service.entity.RolePermissionsEntity;
import com.maizi.service.entity.RolesEntity;
import com.maizi.service.service.PermissionsService;
import com.maizi.service.service.RolePermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("permissionsService")
public class PermissionsServiceImpl extends ServiceImpl<PermissionsDao, PermissionsEntity> implements PermissionsService {

    @Autowired
    private RolePermissionsService rolePermissionsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionsEntity> page = this.page(new Query<PermissionsEntity>().getPage(params), new QueryWrapper<PermissionsEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<PermissionsEntity> findAllPermissions() {
        return this.list();
    }

    @Override
    public List<PermissionsEntity> findPermissionsByRoles(List<RolesEntity> roles) {


        List<RolePermissionsEntity> rolePermissions = rolePermissionsService.findAllPermissionsEntityByRole(roles);
        List<Integer> permissionIds = rolePermissions.stream().map(RolePermissionsEntity::getPermissionId).collect(Collectors.toList());

        return this.baseMapper.selectList(new LambdaQueryWrapper<PermissionsEntity>().in(PermissionsEntity::getId, permissionIds));
    }

}