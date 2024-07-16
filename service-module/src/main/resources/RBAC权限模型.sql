drop database if exists aopquickstart;
create database if not exists aopquickstart;
use aopquickstart;

-- �����û��������û���ΨһID���û���������
CREATE TABLE `users`
(
    `id`       INT AUTO_INCREMENT PRIMARY KEY, -- �û�ID������
    `username` VARCHAR(50)  NOT NULL,          -- �û���
    `password` VARCHAR(100) NOT NULL           -- ����
);

-- ������ɫ��������ɫ��ΨһID������
CREATE TABLE `roles`
(
    `id`   INT AUTO_INCREMENT PRIMARY KEY, -- ��ɫID������
    `name` VARCHAR(50) NOT NULL            -- ��ɫ����
);

-- ����Ȩ�ޱ�����Ȩ�޵�ΨһID�����ƺ�����
CREATE TABLE `permissions`
(
    `id`          INT AUTO_INCREMENT PRIMARY KEY, -- Ȩ��ID������
    `name`        VARCHAR(50) NOT NULL,           -- Ȩ������
    `description` VARCHAR(255)                    -- Ȩ������
);

-- �����û�-��ɫ��ϵ�������û��ͽ�ɫ
CREATE TABLE `user_roles`
(
    `user_id` INT NOT NULL,                                              -- �û�ID
    `role_id` INT NOT NULL,                                              -- ��ɫID
    PRIMARY KEY (`user_id`, `role_id`),                                  -- ����������ȷ��ÿ���û�-��ɫ���Ψһ
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE, -- ������û�ID������ɾ�������� users ���е�һ�б�ɾ��ʱ�����ø��е������У��ڵ�ǰ���У�Ҳ�ᱻ�Զ�ɾ������
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE  -- �������ɫID������ɾ�������� roles ���е�һ�б�ɾ��ʱ�����ø��е������У��ڵ�ǰ���У�Ҳ�ᱻ�Զ�ɾ������
);

-- ������ɫ-Ȩ�޹�ϵ��������ɫ��Ȩ��
CREATE TABLE `role_permissions`
(
    `role_id`       INT NOT NULL,                                                   -- ��ɫID
    `permission_id` INT NOT NULL,                                                   -- Ȩ��ID
    PRIMARY KEY (`role_id`, `permission_id`),                                       -- ����������ȷ��ÿ����ɫ-Ȩ�����Ψһ
    FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE,            -- �������ɫID������ɾ�������� roles ���е�һ�б�ɾ��ʱ�����ø��е������У��ڵ�ǰ���У�Ҳ�ᱻ�Զ�ɾ������
    FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`) ON DELETE CASCADE -- �����Ȩ��ID������ɾ�������� permissions ���е�һ�б�ɾ��ʱ�����ø��е������У��ڵ�ǰ���У�Ҳ�ᱻ�Զ�ɾ������
);

-- ����һЩʾ���û�����
INSERT INTO `users` (`username`, `password`)
VALUES ('alice', 'password1'),   -- �û� alice
       ('bob', 'password2'),     -- �û� bob
       ('charlie', 'password3'), -- �û� charlie
       ('david', 'password4'),   -- �û� david
       ('eve', 'password5');
-- �û� eve

-- ����һЩʾ����ɫ����
INSERT INTO `roles` (`name`)
VALUES ('ADMIN'),     -- ����Ա��ɫ
       ('USER'),      -- ��ͨ�û���ɫ
       ('MODERATOR'), -- ������ɫ
       ('GUEST');
-- �ÿͽ�ɫ

-- ����һЩʾ��Ȩ������
INSERT INTO `permissions` (`name`, `description`)
VALUES ('READ_PRIVILEGES', 'Can read data'),     -- ��ȡȨ��
       ('WRITE_PRIVILEGES', 'Can write data'),   -- д��Ȩ��
       ('DELETE_PRIVILEGES', 'Can delete data'), -- ɾ��Ȩ��
       ('UPDATE_PRIVILEGES', 'Can update data');
-- ����Ȩ��

-- Ϊ�û������ɫ
INSERT INTO `user_roles` (`user_id`, `role_id`)
VALUES (1, 1), -- ���û�IDΪ1���û���alice������Ϊ��ɫIDΪ1�Ľ�ɫ��ADMIN��
       (2, 2), -- ���û�IDΪ2���û���bob������Ϊ��ɫIDΪ2�Ľ�ɫ��USER��
       (3, 3), -- ���û�IDΪ3���û���charlie������Ϊ��ɫIDΪ3�Ľ�ɫ��MODERATOR��
       (4, 4), -- ���û�IDΪ4���û���david������Ϊ��ɫIDΪ4�Ľ�ɫ��GUEST��
       (5, 2);
-- ���û�IDΪ5���û���eve������Ϊ��ɫIDΪ2�Ľ�ɫ��USER��

-- Ϊ��ɫ����Ȩ��
# ADMIN ӵ������Ȩ�ޣ���ȡ��д�롢ɾ���͸���
# USER ֻӵ�ж�ȡȨ��
# MODERATOR ӵ�ж�ȡ�͸���Ȩ��
# GUEST ֻӵ�ж�ȡȨ��
INSERT INTO `role_permissions` (`role_id`, `permission_id`)
VALUES (1, 1), -- ����ɫIDΪ1�Ľ�ɫ��ADMIN������Ȩ��IDΪ1��Ȩ�ޣ�READ_PRIVILEGES��
       (1, 2), -- ����ɫIDΪ1�Ľ�ɫ��ADMIN������Ȩ��IDΪ2��Ȩ�ޣ�WRITE_PRIVILEGES��
       (1, 3), -- ����ɫIDΪ1�Ľ�ɫ��ADMIN������Ȩ��IDΪ3��Ȩ�ޣ�DELETE_PRIVILEGES��
       (1, 4), -- ����ɫIDΪ1�Ľ�ɫ��ADMIN������Ȩ��IDΪ4��Ȩ�ޣ�UPDATE_PRIVILEGES��
       (2, 1), -- ����ɫIDΪ2�Ľ�ɫ��USER������Ȩ��IDΪ1��Ȩ�ޣ�READ_PRIVILEGES��
       (3, 1), -- ����ɫIDΪ3�Ľ�ɫ��MODERATOR������Ȩ��IDΪ1��Ȩ�ޣ�READ_PRIVILEGES��
       (3, 4), -- ����ɫIDΪ3�Ľ�ɫ��MODERATOR������Ȩ��IDΪ4��Ȩ�ޣ�UPDATE_PRIVILEGES��
       (4, 1); -- ����ɫIDΪ4�Ľ�ɫ��GUEST������Ȩ��IDΪ1��Ȩ�ޣ�READ_PRIVILEGES��