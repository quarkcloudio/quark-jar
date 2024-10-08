/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : quarkjar

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 05/10/2024 20:57:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action_logs
-- ----------------------------
DROP TABLE IF EXISTS `action_logs`;
CREATE TABLE `action_logs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `object_id` smallint(6) NOT NULL,
  `username` longtext COLLATE utf8mb4_bin,
  `url` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `remark` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `ip` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `type` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of action_logs
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for configs
-- ----------------------------
DROP TABLE IF EXISTS `configs`;
CREATE TABLE `configs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `type` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `sort` smallint(6) DEFAULT '0',
  `group_name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `value` varchar(2000) COLLATE utf8mb4_bin DEFAULT NULL,
  `remark` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of configs
-- ----------------------------
BEGIN;
INSERT INTO `configs` VALUES (1, '网站名称', 'text', 'WEB_SITE_NAME', 0, '基本', 'QuarkCloud', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (2, '关键字', 'text', 'WEB_SITE_KEYWORDS', 0, '基本', 'QuarkCloud', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (3, '描述', 'textarea', 'WEB_SITE_DESCRIPTION', 0, '基本', 'QuarkCloud', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (4, 'Logo', 'picture', 'WEB_SITE_LOGO', 0, '基本', '', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (5, '统计代码', 'textarea', 'WEB_SITE_SCRIPT', 0, '基本', '', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (6, '网站域名', 'text', 'WEB_SITE_DOMAIN', 0, '基本', '', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (7, '网站版权', 'text', 'WEB_SITE_COPYRIGHT', 0, '基本', '© Company 2018', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (8, '开启SSL', 'switch', 'SSL_OPEN', 0, '基本', '0', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (9, '开启网站', 'switch', 'WEB_SITE_OPEN', 0, '基本', '1', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (10, 'KeyID', 'text', 'OSS_ACCESS_KEY_ID', 0, '阿里云存储', '', '你的AccessKeyID', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (11, 'KeySecret', 'text', 'OSS_ACCESS_KEY_SECRET', 0, '阿里云存储', '', '你的AccessKeySecret', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (12, 'EndPoint', 'text', 'OSS_ENDPOINT', 0, '阿里云存储', '', '地域节点', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (13, 'Bucket域名', 'text', 'OSS_BUCKET', 0, '阿里云存储', '', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (14, '自定义域名', 'text', 'OSS_MYDOMAIN', 0, '阿里云存储', '', '例如：oss.web.com', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
INSERT INTO `configs` VALUES (15, '开启云存储', 'switch', 'OSS_OPEN', 0, '阿里云存储', '0', '', 1, '2024-05-16 17:10:19.460', '2024-05-16 17:10:19.460');
COMMIT;

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` smallint(6) DEFAULT '0',
  `name` varchar(500) NOT NULL,
  `sort` smallint(6) DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of departments
-- ----------------------------
BEGIN;
INSERT INTO `departments` VALUES (1, 0, '夸克云网络科技', 0, 1, '2024-10-05 20:44:31.873', '2024-10-05 20:44:31.873');
COMMIT;

-- ----------------------------
-- Table structure for file_categories
-- ----------------------------
DROP TABLE IF EXISTS `file_categories`;
CREATE TABLE `file_categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `obj_type` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `obj_id` smallint(6) DEFAULT '0',
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `sort` smallint(6) DEFAULT '0',
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of file_categories
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `obj_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `obj_id` smallint(6) DEFAULT '0',
  `file_category_id` smallint(6) DEFAULT '0',
  `sort` smallint(6) DEFAULT '0',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `size` mediumint(9) DEFAULT '0',
  `ext` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `url` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `hash` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of files
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for menu_has_permissions
-- ----------------------------
DROP TABLE IF EXISTS `menu_has_permissions`;
CREATE TABLE `menu_has_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu_has_permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `guard_name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `icon` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `type` bigint(20) NOT NULL,
  `pid` smallint(6) DEFAULT '0',
  `sort` smallint(6) DEFAULT '0',
  `path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `show` tinyint(4) NOT NULL DEFAULT '1',
  `is_engine` tinyint(4) NOT NULL DEFAULT '0',
  `is_link` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `key` longtext COLLATE utf8mb4_bin,
  `locale` longtext COLLATE utf8mb4_bin,
  `hide_in_menu` tinyint(1) DEFAULT NULL,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menus
-- ----------------------------
BEGIN;
INSERT INTO `menus` VALUES (1, '控制台', 'admin', 'icon-home', 1, 0, 0, '/dashboard', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (2, '主页', 'admin', '', 2, 1, 0, '/api/admin/dashboard/index/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (3, '用户管理', 'admin', 'icon-admin', 1, 0, 100, '/user', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (4, '用户列表', 'admin', '', 2, 3, 0, '/api/admin/user/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (5, '权限列表', 'admin', '', 2, 3, 0, '/api/admin/permission/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (6, '角色列表', 'admin', '', 2, 3, 0, '/api/admin/role/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (7, '系统配置', 'admin', 'icon-setting', 1, 0, 100, '/system', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (8, '设置管理', 'admin', '', 1, 7, 0, '/system/config', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (9, '网站设置', 'admin', '', 2, 8, 0, '/api/admin/webConfig/form', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (10, '配置管理', 'admin', '', 2, 8, 0, '/api/admin/config/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (11, '菜单管理', 'admin', '', 2, 7, 0, '/api/admin/menu/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (12, '操作日志', 'admin', '', 2, 7, 100, '/api/admin/actionLog/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (13, '附件空间', 'admin', 'icon-attachment', 1, 0, 100, '/attachment', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (14, '文件管理', 'admin', '', 2, 13, 0, '/api/admin/file/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (15, '图片管理', 'admin', '', 2, 13, 0, '/api/admin/picture/index', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (16, '我的账号', 'admin', 'icon-user', 1, 0, 100, '/account', 1, 0, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
INSERT INTO `menus` VALUES (17, '个人设置', 'admin', '', 2, 16, 0, '/api/admin/account/form', 1, 1, 0, 1, NULL, NULL, NULL, '2024-05-16 17:10:19.463', '2024-05-16 17:10:19.463');
COMMIT;

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `guard_name` varchar(100) COLLATE utf8mb4_bin NOT NULL,
  `path` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `method` varchar(500) COLLATE utf8mb4_bin NOT NULL,
  `remark` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for picture_categories
-- ----------------------------
DROP TABLE IF EXISTS `picture_categories`;
CREATE TABLE `picture_categories` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `obj_type` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `obj_id` smallint(6) DEFAULT '0',
  `title` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `sort` smallint(6) DEFAULT '0',
  `description` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of picture_categories
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for pictures
-- ----------------------------
DROP TABLE IF EXISTS `pictures`;
CREATE TABLE `pictures` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `obj_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `obj_id` smallint(6) DEFAULT '0',
  `picture_category_id` smallint(6) DEFAULT '0',
  `sort` smallint(6) DEFAULT '0',
  `name` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `size` mediumint(9) DEFAULT '0',
  `width` smallint(6) DEFAULT '0',
  `height` smallint(6) DEFAULT '0',
  `ext` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `url` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `hash` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of pictures
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for positions
-- ----------------------------
DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) NOT NULL,
  `sort` smallint(6) DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `remark` varchar(100) DEFAULT NULL,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of positions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role_has_departments
-- ----------------------------
DROP TABLE IF EXISTS `role_has_departments`;
CREATE TABLE `role_has_departments` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_has_departments
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role_has_menus
-- ----------------------------
DROP TABLE IF EXISTS `role_has_menus`;
CREATE TABLE `role_has_menus` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_has_menus
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for role_has_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_has_permissions`;
CREATE TABLE `role_has_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_has_permissions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `data_scope` tinyint(4) NOT NULL DEFAULT '1',
  `guard_name` varchar(100) NOT NULL,
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roles
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_has_positions
-- ----------------------------
DROP TABLE IF EXISTS `user_has_positions`;
CREATE TABLE `user_has_positions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `position_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_has_positions
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for user_has_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_has_roles`;
CREATE TABLE `user_has_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `guard_name` varchar(200) COLLATE utf8mb4_bin DEFAULT 'admin',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_has_roles
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8mb4_bin NOT NULL,
  `nickname` varchar(200) COLLATE utf8mb4_bin NOT NULL,
  `sex` tinyint(4) NOT NULL DEFAULT '1',
  `email` varchar(50) COLLATE utf8mb4_bin NOT NULL,
  `phone` varchar(11) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin NOT NULL,
  `avatar` varchar(1000) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_login_ip` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_login_time` datetime(3) DEFAULT NULL,
  `wx_openid` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `wx_unionid` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` datetime(3) DEFAULT NULL,
  `updated_at` datetime(3) DEFAULT NULL,
  `deleted_at` datetime(3) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `users_username_unique` (`username`) USING BTREE,
  UNIQUE KEY `users_email_unique` (`email`) USING BTREE,
  UNIQUE KEY `users_phone_unique` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of users
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES (1, 'administrator', '超级管理员', 1, 'admin@yourweb.com', '10086', '$2a$04$d/gRv3MdXWSByWOFp0xqce0g.RSyp3c91PCLcEIIX9rsJ/l2QocsW', '', '', '2024-05-16 17:10:19.457', '', '', 1, '2024-05-16 17:10:19.458', '2024-05-16 17:10:19.458', NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
