package com.maizi.service.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.Query;
import com.maizi.service.dao.RolePermissionsDao;
import com.maizi.service.entity.RolePermissionsEntity;
import com.maizi.service.entity.RolesEntity;
import com.maizi.service.service.RolePermissionsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("rolePermissionsService")
public class RolePermissionsServiceImpl extends ServiceImpl<RolePermissionsDao, RolePermissionsEntity> implements RolePermissionsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionsEntity> page = this.page(new Query<RolePermissionsEntity>().getPage(params), new QueryWrapper<RolePermissionsEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<RolePermissionsEntity> findAllPermissionsEntityByRole(List<RolesEntity> roles) {

        List<Integer> roleIds = roles.stream().map(RolesEntity::getId).collect(Collectors.toList());
        LambdaQueryWrapper<RolePermissionsEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(RolePermissionsEntity::getRoleId, roleIds);
        return this.baseMapper.selectList(wrapper);
    }

}