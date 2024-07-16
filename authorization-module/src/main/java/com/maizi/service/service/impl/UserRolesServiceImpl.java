package com.maizi.service.service.impl;


import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.Query;
import com.maizi.service.dao.UserRolesDao;
import com.maizi.service.entity.UserRolesEntity;
import com.maizi.service.service.UserRolesService;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


@Service("userRolesService")
public class UserRolesServiceImpl extends ServiceImpl<UserRolesDao, UserRolesEntity> implements UserRolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRolesEntity> page = this.page(new Query<UserRolesEntity>().getPage(params), new QueryWrapper<UserRolesEntity>());

        return new PageUtils(page);
    }

}