package com.maizi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.common.utils.PageUtils;
import com.maizi.service.dao.RolesDao;
import com.maizi.service.entity.RolesEntity;
import com.maizi.service.service.RolesService;
import com.maizi.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("rolesService")
public class RolesServiceImpl extends ServiceImpl<RolesDao, RolesEntity> implements RolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolesEntity> page = this.page(new Query<RolesEntity>().getPage(params), new QueryWrapper<RolesEntity>());

        return new PageUtils(page);
    }

    @Override
    public List<RolesEntity> findRolesByUserId(Integer id) {
        LambdaQueryWrapper<RolesEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RolesEntity::getId, id);
        return baseMapper.selectList(wrapper);
    }

}