package com.maizi.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maizi.common.utils.PageUtils;
import com.maizi.common.utils.Query;
import com.maizi.service.dao.UsersDao;
import com.maizi.service.entity.UsersEntity;
import com.maizi.service.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("usersService")
public class UsersServiceImpl extends ServiceImpl<UsersDao, UsersEntity> implements UsersService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UsersEntity> page = this.page(new Query<UsersEntity>().getPage(params), new QueryWrapper<>());
        return new PageUtils(page);
    }

    @Override
    public UsersEntity getUserByName(String username) {
        LambdaQueryWrapper<UsersEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsersEntity::getUsername, username);
        return this.getOne(wrapper);
    }


    // 示例：对已有密码进行重新编码
    @Override
    public void reEncodePasswords() {

        this.list().forEach(user -> {
            System.out.println(user.toString());
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            this.updateById(user);
            System.out.println(user.toString());
        });
    }


}