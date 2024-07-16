drop database if exists aopquickstart;
create database if not exists aopquickstart;
use aopquickstart;

-- 创建用户表，包含用户的唯一ID、用户名和密码
CREATE TABLE `users`
(
    `id`       INT AUTO_INCREMENT PRIMARY KEY, -- 用户ID，自增
    `username` VARCHAR(50)  NOT NULL,          -- 用户名
    `password` VARCHAR(100) NOT NULL           -- 密码
);

-- 创建角色表，包含角色的唯一ID和名称
CREATE TABLE `roles`
(
    `id`   INT AUTO_INCREMENT PRIMARY KEY, -- 角色ID，自增
    `name` VARCHAR(50) NOT NULL            -- 角色名称
);

-- 创建权限表，包含权限的唯一ID、名称和描述
CREATE TABLE `permissions`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY, -- 权限ID，自增
    `name`        VARCHAR(50) NOT NULL,           -- 权限名称
    `description` VARCHAR(255)                    -- 权限描述
);

-- 创建用户-角色关系表，关联用户和角色
CREATE TABLE `user_roles`
(
    `user_id` INT NOT NULL,                                              -- 用户ID
    `role_id` INT NOT NULL,                                              -- 角色ID
    PRIMARY KEY (`user_id`, `role_id`),                                  -- 联合主键，确保每个用户-角色组合唯一
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE, -- 外键，用户ID，级联删除（即当 users 表中的一行被删除时，引用该行的所有行（在当前表中）也会被自动删除。）
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE  -- 外键，角色ID，级联删除（即当 roles 表中的一行被删除时，引用该行的所有行（在当前表中）也会被自动删除。）
);

-- 创建角色-权限关系表，关联角色和权限
CREATE TABLE `role_permissions`
(
    `role_id`       INT NOT NULL,                                                   -- 角色ID
    `permission_id` INT NOT NULL,                                                   -- 权限ID
    PRIMARY KEY (`role_id`, `permission_id`),                                       -- 联合主键，确保每个角色-权限组合唯一
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,            -- 外键，角色ID，级联删除（即当 roles 表中的一行被删除时，引用该行的所有行（在当前表中）也会被自动删除。）
    FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE -- 外键，权限ID，级联删除（即当 permissions 表中的一行被删除时，引用该行的所有行（在当前表中）也会被自动删除。）
);

-- 插入一些示例用户数据
INSERT INTO `users` (`username`, `password`)
VALUES ('alice', 'password1'),   -- 用户 alice
       ('bob', 'password2'),     -- 用户 bob
       ('charlie', 'password3'), -- 用户 charlie
       ('david', 'password4'),   -- 用户 david
       ('eve', 'password5');
-- 用户 eve

-- 插入一些示例角色数据
INSERT INTO `roles` (`name`)
VALUES ('ADMIN'),     -- 管理员角色
       ('USER'),      -- 普通用户角色
       ('MODERATOR'), -- 版主角色
       ('GUEST');
-- 访客角色

-- 插入一些示例权限数据
INSERT INTO `permissions` (`name`, `description`)
VALUES ('READ_PRIVILEGES', 'Can read data'),     -- 读取权限
       ('WRITE_PRIVILEGES', 'Can write data'),   -- 写入权限
       ('DELETE_PRIVILEGES', 'Can delete data'), -- 删除权限
       ('UPDATE_PRIVILEGES', 'Can update data');
-- 更新权限

-- 为用户分配角色
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES (1, 1), -- 将用户ID为1的用户（alice）分配为角色ID为1的角色（ADMIN）
       (2, 2), -- 将用户ID为2的用户（bob）分配为角色ID为2的角色（USER）
       (3, 3), -- 将用户ID为3的用户（charlie）分配为角色ID为3的角色（MODERATOR）
       (4, 4), -- 将用户ID为4的用户（david）分配为角色ID为4的角色（GUEST）
       (5, 2);
-- 将用户ID为5的用户（eve）分配为角色ID为2的角色（USER）

-- 为角色分配权限
# ADMIN 拥有所有权限：读取、写入、删除和更新
# USER 只拥有读取权限
# MODERATOR 拥有读取和更新权限
# GUEST 只拥有读取权限
INSERT INTO `role_permissions` (`role_id`, `permission_id`)
VALUES (1, 1), -- 将角色ID为1的角色（ADMIN）分配权限ID为1的权限（READ_PRIVILEGES）
       (1, 2), -- 将角色ID为1的角色（ADMIN）分配权限ID为2的权限（WRITE_PRIVILEGES）
       (1, 3), -- 将角色ID为1的角色（ADMIN）分配权限ID为3的权限（DELETE_PRIVILEGES）
       (1, 4), -- 将角色ID为1的角色（ADMIN）分配权限ID为4的权限（UPDATE_PRIVILEGES）
       (2, 1), -- 将角色ID为2的角色（USER）分配权限ID为1的权限（READ_PRIVILEGES）
       (3, 1), -- 将角色ID为3的角色（MODERATOR）分配权限ID为1的权限（READ_PRIVILEGES）
       (3, 4), -- 将角色ID为3的角色（MODERATOR）分配权限ID为4的权限（UPDATE_PRIVILEGES）
       (4, 1); -- 将角色ID为4的角色（GUEST）分配权限ID为1的权限（READ_PRIVILEGES）