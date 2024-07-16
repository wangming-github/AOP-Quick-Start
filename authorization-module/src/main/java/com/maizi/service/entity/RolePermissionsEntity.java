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
@TableName("role_permissions")
public class RolePermissionsEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
    private Integer roleId;
    /**
     *
     */
    private Integer permissionId;

}
