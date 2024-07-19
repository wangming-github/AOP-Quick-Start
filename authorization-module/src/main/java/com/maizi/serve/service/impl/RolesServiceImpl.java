package com.maizi.serve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.serve.dao.RolesDao;
import com.maizi.serve.entity.RolesEntity;
import com.maizi.serve.utils.PageUtils;
import com.maizi.serve.utils.QueryUtils;
import com.maizi.serve.service.RolesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("rolesService")
public class RolesServiceImpl extends ServiceImpl<RolesDao, RolesEntity> implements RolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolesEntity> page = this.page(new QueryUtils<RolesEntity>().getPage(params), new QueryWrapper<RolesEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<RolesEntity> findRolesByUserId(Integer id) {
        return baseMapper.selectList(//
                new LambdaQueryWrapper<RolesEntity>().eq(RolesEntity::getId, id));
    }

}