/*
 Navicat Premium Dump SQL

 Source Server         : 本地连接
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43-log)
 Source Host           : localhost:3306
 Source Schema         : quarkcloud

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43-log)
 File Encoding         : 65001

 Date: 30/01/2026 15:50:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for action_logs
-- ----------------------------
DROP TABLE IF EXISTS `action_logs`;
CREATE TABLE `action_logs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` smallint(6) NOT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of action_logs
-- ----------------------------

-- ----------------------------
-- Table structure for attachment_categories
-- ----------------------------
DROP TABLE IF EXISTS `attachment_categories`;
CREATE TABLE `attachment_categories`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `uid` smallint(6) NULL DEFAULT 0,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort` smallint(6) NULL DEFAULT 0,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachment_categories
-- ----------------------------

-- ----------------------------
-- Table structure for attachments
-- ----------------------------
DROP TABLE IF EXISTS `attachments`;
CREATE TABLE `attachments`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` smallint(6) NULL DEFAULT 0,
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `category_id` smallint(6) NULL DEFAULT 0,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `sort` smallint(6) NULL DEFAULT 0,
  `size` mediumint(9) NULL DEFAULT 0,
  `ext` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `hash` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `extra` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attachments
-- ----------------------------

-- ----------------------------
-- Table structure for configs
-- ----------------------------
DROP TABLE IF EXISTS `configs`;
CREATE TABLE `configs`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort` smallint(6) NULL DEFAULT 0,
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of configs
-- ----------------------------
INSERT INTO `configs` VALUES (1, '网站名称', 'text', 'WEB_SITE_NAME', 0, '基本', 'QuarkCloud', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (2, '关键字', 'text', 'WEB_SITE_KEYWORDS', 0, '基本', 'QuarkCloud', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (3, '描述', 'textarea', 'WEB_SITE_DESCRIPTION', 0, '基本', 'QuarkCloud', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (4, 'Logo', 'picture', 'WEB_SITE_LOGO', 0, '基本', '', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (5, '统计代码', 'textarea', 'WEB_SITE_SCRIPT', 0, '基本', '', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (6, '网站域名', 'text', 'WEB_SITE_DOMAIN', 0, '基本', '', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (7, '网站版权', 'text', 'WEB_SITE_COPYRIGHT', 0, '基本', '© Company 2018', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (8, '开启SSL', 'switch', 'SSL_OPEN', 0, '基本', '0', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (9, '开启网站', 'switch', 'WEB_SITE_OPEN', 0, '基本', '1', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (10, 'KeyID', 'text', 'OSS_ACCESS_KEY_ID', 0, '阿里云存储', '', '你的AccessKeyID', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (11, 'KeySecret', 'text', 'OSS_ACCESS_KEY_SECRET', 0, '阿里云存储', '', '你的AccessKeySecret', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (12, 'EndPoint', 'text', 'OSS_ENDPOINT', 0, '阿里云存储', '', '地域节点', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (13, 'Bucket域名', 'text', 'OSS_BUCKET', 0, '阿里云存储', '', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (14, '自定义域名', 'text', 'OSS_MYDOMAIN', 0, '阿里云存储', '', '例如：oss.web.com', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');
INSERT INTO `configs` VALUES (15, '开启云存储', 'switch', 'OSS_OPEN', 0, '阿里云存储', '0', '', 1, '2025-12-14 08:26:52.617', '2025-12-14 08:26:52.617');

-- ----------------------------
-- Table structure for departments
-- ----------------------------
DROP TABLE IF EXISTS `departments`;
CREATE TABLE `departments`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pid` smallint(6) NULL DEFAULT 0,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort` smallint(6) NULL DEFAULT 0,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of departments
-- ----------------------------
INSERT INTO `departments` VALUES (1, 0, '夸克云科技', 0, 1, '2025-12-14 08:26:52.764', '2025-12-22 11:58:18.411');
INSERT INTO `departments` VALUES (2, 1, '研发中心', 0, 1, '2025-12-14 08:26:52.764', '2025-12-22 11:53:34.142');
INSERT INTO `departments` VALUES (3, 1, '营销中心', 0, 1, '2025-12-14 08:26:52.764', '2025-12-23 13:27:42.811');

-- ----------------------------
-- Table structure for menu_permissions
-- ----------------------------
DROP TABLE IF EXISTS `menu_permissions`;
CREATE TABLE `menu_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_id` smallint(6) NOT NULL,
  `permission_id` smallint(6) NOT NULL,
  `guard_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `guard_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `permission` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` bigint(20) NOT NULL,
  `page_type` bigint(20) NOT NULL,
  `pid` smallint(6) NULL DEFAULT 0,
  `sort` smallint(6) NULL DEFAULT 0,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `visible` tinyint(4) NOT NULL DEFAULT 1,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES (1, '首页', 'admin', '', 'ant-design:home-outlined', 2, 2, 0, 0, 'home', '{\"api\":\"/api/admin/dashboard/index/index\"}', 'home/index', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 16:50:09.659');
INSERT INTO `menus` VALUES (3, '用户管理', 'admin', '', 'ant-design:usergroup-add-outlined', 1, 1, 0, 100, 'user', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (4, '用户列表', 'admin', '', 'ant-design:user-add-outlined', 2, 2, 3, 0, 'user', '{\"api\":\"/api/admin/user/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (5, '权限列表', 'admin', '', 'ant-design:profile-outlined', 2, 2, 3, 0, 'permission', '{\"api\":\"/api/admin/permission/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (6, '角色列表', 'admin', '', 'ant-design:idcard-outlined', 2, 2, 3, 0, 'role', '{\"api\":\"/api/admin/role/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (7, '系统配置', 'admin', '', 'ant-design:setting-outlined', 1, 1, 0, 100, 'system', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (8, '设置管理', 'admin', '', 'ant-design:appstore-add-outlined', 1, 1, 7, 0, 'config', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (9, '网站设置', 'admin', '', 'ant-design:cluster-outlined', 2, 2, 8, 0, 'webConfig', '{\"api\":\"/api/admin/webConfig/form\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (10, '配置管理', 'admin', '', 'ant-design:tool-outlined', 2, 2, 8, 0, 'config', '{\"api\":\"/api/admin/config/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (11, '菜单管理', 'admin', '', 'ant-design:menu-outlined', 2, 2, 7, 0, 'menu', '{\"api\":\"/api/admin/menu/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (12, '操作日志', 'admin', '', 'ant-design:file-done-outlined', 2, 2, 7, 100, 'actionLog', '{\"api\":\"/api/admin/actionLog/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (13, '附件空间', 'admin', '', 'ant-design:folder-outlined', 1, 1, 0, 100, 'attachment', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (14, '文件管理', 'admin', '', 'ant-design:file-outlined', 2, 2, 13, 0, 'file', '{\"api\":\"/api/admin/file/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (15, '图片管理', 'admin', '', 'ant-design:picture-outlined', 2, 2, 13, 0, 'image', '{\"api\":\"/api/admin/image/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (16, '我的账号', 'admin', '', 'ant-design:user-outlined', 1, 1, 0, 110, 'account', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (17, '个人设置', 'admin', '', 'ant-design:user-switch-outlined', 2, 2, 16, 0, 'setting', '{\"api\":\"/api/admin/account/form\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (18, '部门列表', 'admin', '', 'ant-design:apartment-outlined', 2, 2, 3, 0, 'department', '{\"api\":\"/api/admin/department/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (19, '职位列表', 'admin', '', 'ant-design:bars-outlined', 2, 2, 3, 0, 'position', '{\"api\":\"/api/admin/position/index\"}', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (20, '组件调试', 'admin', '', 'ant-design:appstore-outlined', 1, 1, 0, 100, 'develop', '', '', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');
INSERT INTO `menus` VALUES (21, '组件开发', 'admin', '', 'ant-design:experiment-outlined', 2, 1, 20, 0, 'index', '', 'develop/index', 1, 1, '2025-12-14 08:26:52.664', '2025-12-14 08:26:52.664');

-- ----------------------------
-- Table structure for permissions
-- ----------------------------
DROP TABLE IF EXISTS `permissions`;
CREATE TABLE `permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `guard_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `method` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permissions
-- ----------------------------

-- ----------------------------
-- Table structure for positions
-- ----------------------------
DROP TABLE IF EXISTS `positions`;
CREATE TABLE `positions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sort` smallint(6) NULL DEFAULT 0,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of positions
-- ----------------------------
INSERT INTO `positions` VALUES (1, '董事长', 0, 1, '', '2025-12-14 08:26:52.811', '2025-12-14 08:26:52.811');
INSERT INTO `positions` VALUES (2, '项目经理', 0, 1, '', '2025-12-14 08:26:52.811', '2025-12-14 08:26:52.811');
INSERT INTO `positions` VALUES (3, '普通员工', 0, 1, '', '2025-12-14 08:26:52.811', '2025-12-14 08:26:52.811');

-- ----------------------------
-- Table structure for role_departments
-- ----------------------------
DROP TABLE IF EXISTS `role_departments`;
CREATE TABLE `role_departments`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` smallint(6) NOT NULL,
  `department_id` smallint(6) NOT NULL,
  `guard_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_departments
-- ----------------------------

-- ----------------------------
-- Table structure for role_menus
-- ----------------------------
DROP TABLE IF EXISTS `role_menus`;
CREATE TABLE `role_menus`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` smallint(6) NOT NULL,
  `menu_id` smallint(6) NOT NULL,
  `guard_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menus
-- ----------------------------

-- ----------------------------
-- Table structure for role_permissions
-- ----------------------------
DROP TABLE IF EXISTS `role_permissions`;
CREATE TABLE `role_permissions`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` smallint(6) NOT NULL,
  `permission_id` smallint(6) NOT NULL,
  `guard_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permissions
-- ----------------------------

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `data_scope` tinyint(4) NOT NULL DEFAULT 1,
  `guard_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES (1, '普通角色', 1, 'admin', 1, '2025-12-14 08:26:52.714', '2025-12-23 17:57:12.152');

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` smallint(6) NOT NULL,
  `role_id` smallint(6) NOT NULL,
  `guard_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_roles
-- ----------------------------

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `sex` tinyint(4) NOT NULL DEFAULT 1,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department_id` smallint(6) NULL DEFAULT NULL,
  `position_ids` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_login_ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `last_login_time` datetime(3) NULL DEFAULT NULL,
  `wx_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `wx_unionid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT 1,
  `created_at` datetime(3) NULL DEFAULT NULL,
  `updated_at` datetime(3) NULL DEFAULT NULL,
  `deleted_at` datetime(3) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username_unique`(`username`) USING BTREE,
  UNIQUE INDEX `email_unique`(`email`) USING BTREE,
  UNIQUE INDEX `phone_unique`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'administrator', '超级管理员', 1, 'admin@yourweb.com', '10086', '$2a$04$eiu2HPsbHg8c6tYERuNrqeLy9myEMI49.yptTaeAPgZdMvG2WTJ6O', '/admin/default.png', 1, NULL, '127.0.0.1', '2026-01-30 15:43:33.448', '', '', 1, '2025-12-14 08:26:52.563', '2026-01-30 07:43:33.449', NULL);

SET FOREIGN_KEY_CHECKS = 1;
