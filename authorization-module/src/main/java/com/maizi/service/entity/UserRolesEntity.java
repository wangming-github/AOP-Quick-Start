package com.maizi.service.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * @author wangming
 * @email myoneray@gmail.com
 * @date 2024-07-12 17:44:48
 */
@Data
@TableName("user_roles")
public class UserRolesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer userId;
    /**
     *
     */
    private Integer roleId;

}
