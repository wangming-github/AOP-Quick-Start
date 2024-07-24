package com.maizi.serve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maizi.common.dto.UserDetailsDTO;
import com.maizi.serve.dao.UsersDao;
import com.maizi.serve.entity.*;
import com.maizi.serve.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2024-07-12 10:50:00
 */
public interface UsersService extends IService<UsersEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UsersEntity getUserByName(String username);

    UserDetailsDTO findAuthorByUsername(String username);


}


