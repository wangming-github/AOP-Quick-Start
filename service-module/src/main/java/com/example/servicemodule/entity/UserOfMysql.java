package com.example.servicemodule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 模拟mysql功能
 *
 * @author maizi
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserOfMysql {
    private Long id;
    private String userName;
    private String password;

}
