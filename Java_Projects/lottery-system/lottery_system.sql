/*
 Navicat Premium Dump SQL

 Source Server         : mybatis_demo
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : lottery_system

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 01/08/2025 20:40:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `activity_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '活动名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '活动描述',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '活动状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of activity
-- ----------------------------
BEGIN;
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (26, '2025-07-01 18:36:40', '2025-07-01 18:36:40', 'test', '111', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (27, '2025-07-02 10:38:10', '2025-07-02 10:38:10', 'test11', '1111', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (28, '2025-07-02 10:40:55', '2025-07-02 10:40:55', '测试测试', '测试', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (29, '2025-07-02 10:42:42', '2025-07-09 21:33:54', 'test11122', '111222', 'FINISHED');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (30, '2025-07-02 10:42:54', '2025-07-02 10:42:54', '1111', '111', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (31, '2025-07-02 10:43:08', '2025-07-02 10:43:08', '22233', '1123', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (32, '2025-07-02 10:43:27', '2025-07-15 11:34:03', '123', '123', 'FINISHED');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (33, '2025-07-02 10:44:21', '2025-07-02 10:44:21', '123', '123', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (34, '2025-07-02 10:44:38', '2025-07-02 10:44:38', '123123', '123123', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (35, '2025-07-02 10:45:07', '2025-07-02 10:45:07', 'test22', '222', 'RUNNING');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (36, '2025-07-02 10:53:39', '2025-07-15 21:03:53', '测试 111', '1234', 'FINISHED');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (37, '2025-07-15 21:05:02', '2025-07-15 21:05:17', '谁能得到邱俊航的爱意活动', '你能得到邱俊航的爱意吗？', 'FINISHED');
INSERT INTO `activity` (`id`, `gmt_create`, `gmt_modified`, `activity_name`, `description`, `status`) VALUES (38, '2025-07-15 21:06:39', '2025-07-15 21:06:52', '邱俊俊大礼包活动', '邱俊俊大礼包活动', 'FINISHED');
COMMIT;

-- ----------------------------
-- Table structure for activity_prize
-- ----------------------------
DROP TABLE IF EXISTS `activity_prize`;
CREATE TABLE `activity_prize` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `activity_id` bigint NOT NULL COMMENT '活动id',
  `prize_id` bigint NOT NULL COMMENT '活动关联的奖品id',
  `prize_amount` bigint NOT NULL DEFAULT '1' COMMENT '关联奖品的数量',
  `prize_tiers` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '奖品等级',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '活动奖品状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE,
  UNIQUE KEY `uk_a_p_id` (`activity_id`,`prize_id`) USING BTREE,
  KEY `idx_activity_id` (`activity_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of activity_prize
-- ----------------------------
BEGIN;
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (32, '2025-07-01 18:36:40', '2025-07-01 18:36:40', 26, 18, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (33, '2025-07-02 10:38:10', '2025-07-02 10:38:10', 27, 19, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (34, '2025-07-02 10:40:55', '2025-07-02 10:40:55', 28, 22, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (35, '2025-07-02 10:42:42', '2025-07-09 21:33:43', 29, 18, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (36, '2025-07-02 10:42:42', '2025-07-09 21:28:54', 29, 19, 1, 'SECOND_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (37, '2025-07-02 10:42:54', '2025-07-11 17:58:36', 30, 20, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (38, '2025-07-02 10:42:54', '2025-07-02 10:42:54', 30, 21, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (39, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 20, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (40, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 21, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (41, '2025-07-02 10:43:27', '2025-07-15 11:34:03', 32, 22, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (42, '2025-07-02 10:43:27', '2025-07-15 11:32:48', 32, 23, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (43, '2025-07-02 10:44:21', '2025-07-02 10:44:21', 33, 18, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (44, '2025-07-02 10:44:38', '2025-07-02 10:44:38', 34, 19, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (45, '2025-07-02 10:44:38', '2025-07-02 10:44:38', 34, 20, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (46, '2025-07-02 10:45:07', '2025-07-02 10:45:07', 35, 20, 1, 'FIRST_PRIZE', 'INIT');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (47, '2025-07-02 10:53:39', '2025-07-15 21:03:53', 36, 18, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (48, '2025-07-15 21:05:02', '2025-07-15 21:05:12', 37, 23, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (49, '2025-07-15 21:05:02', '2025-07-15 21:05:17', 37, 24, 1, 'SECOND_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (50, '2025-07-15 21:06:39', '2025-07-15 21:06:45', 38, 23, 1, 'FIRST_PRIZE', 'COMPLETED');
INSERT INTO `activity_prize` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `prize_id`, `prize_amount`, `prize_tiers`, `status`) VALUES (51, '2025-07-15 21:06:39', '2025-07-15 21:06:52', 38, 24, 1, 'SECOND_PRIZE', 'COMPLETED');
COMMIT;

-- ----------------------------
-- Table structure for activity_user
-- ----------------------------
DROP TABLE IF EXISTS `activity_user`;
CREATE TABLE `activity_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `activity_id` bigint NOT NULL COMMENT '活动时间',
  `user_id` bigint NOT NULL COMMENT '圈选的用户id',
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
  `status` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE,
  UNIQUE KEY `uk_a_u_id` (`activity_id`,`user_id`) USING BTREE,
  KEY `idx_activity_id` (`activity_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of activity_user
-- ----------------------------
BEGIN;
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (3, '2025-07-01 18:36:40', '2025-07-01 18:36:40', 26, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (4, '2025-07-01 18:36:40', '2025-07-01 18:36:40', 26, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (5, '2025-07-02 10:38:10', '2025-07-02 10:38:10', 27, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (6, '2025-07-02 10:38:10', '2025-07-02 10:38:10', 27, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (7, '2025-07-02 10:40:55', '2025-07-02 10:40:55', 28, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (8, '2025-07-02 10:40:55', '2025-07-02 10:40:55', 28, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (9, '2025-07-02 10:42:42', '2025-07-02 10:42:42', 29, 54, '邱小帅', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (10, '2025-07-02 10:42:42', '2025-07-02 10:42:42', 29, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (11, '2025-07-02 10:42:42', '2025-07-09 21:28:34', 29, 52, '邱俊俊', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (12, '2025-07-02 10:42:42', '2025-07-09 21:33:34', 29, 51, '陈多多', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (13, '2025-07-02 10:42:55', '2025-07-02 10:42:55', 30, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (14, '2025-07-02 10:42:55', '2025-07-15 11:09:17', 30, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (15, '2025-07-02 10:42:55', '2025-07-02 10:42:55', 30, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (16, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 54, '邱小帅', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (17, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (18, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (19, '2025-07-02 10:43:08', '2025-07-02 10:43:08', 31, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (20, '2025-07-02 10:43:27', '2025-07-02 10:43:27', 32, 54, '邱小帅', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (21, '2025-07-02 10:43:27', '2025-07-02 10:43:27', 32, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (22, '2025-07-02 10:43:27', '2025-07-15 11:34:03', 32, 52, '邱俊俊', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (23, '2025-07-02 10:43:27', '2025-07-15 11:32:48', 32, 51, '陈多多', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (24, '2025-07-02 10:44:21', '2025-07-02 10:44:21', 33, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (25, '2025-07-02 10:44:21', '2025-07-02 10:44:21', 33, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (26, '2025-07-02 10:44:21', '2025-07-02 10:44:21', 33, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (27, '2025-07-02 10:44:38', '2025-07-02 10:44:38', 34, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (28, '2025-07-02 10:44:38', '2025-07-02 10:44:38', 34, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (29, '2025-07-02 10:44:38', '2025-07-02 10:44:38', 34, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (30, '2025-07-02 10:45:07', '2025-07-02 10:45:07', 35, 53, '陈小美', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (31, '2025-07-02 10:45:07', '2025-07-02 10:45:07', 35, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (32, '2025-07-02 10:45:07', '2025-07-02 10:45:07', 35, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (33, '2025-07-02 10:53:39', '2025-07-15 21:03:53', 36, 53, '陈小美', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (34, '2025-07-02 10:53:39', '2025-07-02 10:53:39', 36, 52, '邱俊俊', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (35, '2025-07-02 10:53:39', '2025-07-02 10:53:39', 36, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (36, '2025-07-15 21:05:02', '2025-07-15 21:05:12', 37, 54, '邱小帅', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (37, '2025-07-15 21:05:02', '2025-07-15 21:05:17', 37, 52, '邱俊俊', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (38, '2025-07-15 21:05:02', '2025-07-15 21:05:02', 37, 51, '陈多多', 'INIT');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (39, '2025-07-15 21:06:39', '2025-07-15 21:06:52', 38, 52, '邱俊俊', 'COMPLETED');
INSERT INTO `activity_user` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `user_id`, `user_name`, `status`) VALUES (40, '2025-07-15 21:06:39', '2025-07-15 21:06:45', 38, 51, '陈多多', 'COMPLETED');
COMMIT;

-- ----------------------------
-- Table structure for prize
-- ----------------------------
DROP TABLE IF EXISTS `prize`;
CREATE TABLE `prize` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '奖品名称',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '奖品描述',
  `price` decimal(10,2) NOT NULL COMMENT '奖品价值',
  `image_url` varchar(2048) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '奖品展示图',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of prize
-- ----------------------------
BEGIN;
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (18, '2025-06-18 10:26:20', '2025-06-18 11:14:40', '保温杯', '保温 48 小时', 12.00, '50978383-fa2a-41c3-9431-d3ad01e24875.jpeg');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (19, '2025-06-18 10:36:59', '2025-06-18 10:36:59', '吹风机', '可以吹很强劲的风', 25.00, '113a1759-8382-46a0-8b73-2dcdb4b25138.jpeg');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (20, '2025-06-18 11:40:16', '2025-06-18 11:40:16', '华为手机', '华为最高配', 7800.00, '2bf11ef7-1ac8-405d-a304-d171efaf0c1f.jpg');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (21, '2025-06-18 11:40:57', '2025-06-18 11:40:57', '苹果手机', '苹果 16 + 128GB', 6800.00, '7937ae64-ebed-4666-8101-aab29e4afc2d.jpeg');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (22, '2025-06-18 15:31:11', '2025-06-18 15:31:11', '乐事薯片', '超级多口味的乐事薯片', 30.00, 'fad04403-46f8-4cfd-9cb1-ddb274354a61.jpg');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (23, '2025-06-18 15:45:49', '2025-06-18 15:45:49', 'MacBook Pro', 'MacBook Pro 32GB+1TB', 19999.00, '444cbe36-ba92-4056-9f79-4acf3bc145be.png');
INSERT INTO `prize` (`id`, `gmt_create`, `gmt_modified`, `name`, `description`, `price`, `image_url`) VALUES (24, '2025-06-18 15:46:36', '2025-06-18 15:46:36', '山姆会员卡', '山姆 800 元会员卡', 800.00, 'c238d934-e762-4031-8661-18f53fab4ab9.webp');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户姓名',
  `email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '邮箱',
  `phone_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '手机号',
  `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '登录密码',
  `identity` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户身份',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`(30)) USING BTREE,
  UNIQUE KEY `uk_phone_number` (`phone_number`(11)) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (47, '2025-06-15 18:09:02', '2025-07-15 11:08:28', 'KevinQiu', 'kevqiu@foxmail.com', 'f97f10321ded0eb31f961742a30c4086', '67dcf793bc98503bcdc8918b4df215e4e4005231d0c007271073db4a138f2baefd22db188c044454b03ea2e8e2d9a97d', 'ADMIN');
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (50, '2025-06-16 21:42:45', '2025-06-16 21:42:45', '王小明', '123456@qq.com', '13527f64ac69d66cd85d69c0a5aa4ee5', '6de981d084f4b366c85f8a8fe94804b9fbd1bc9494d7c79831efc7b20d3ed3f5cbf67d0c51364e92ba5b2cf8919e0e9e', 'ADMIN');
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (51, '2025-06-16 21:57:16', '2025-06-16 21:57:16', '陈多多', '1677231572@qq.com', '90bea188ceb4214e76c4bc34b6db2f80', 'd35297c660a58b46bda271bb185a332c86c0aff25e91ed03cb7a9013cd84c645b9ae9fa351944bd59ea9ed03f4b986f4', 'NORMAL');
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (52, '2025-06-18 10:36:03', '2025-07-15 11:08:37', '邱俊俊', '1477527150@qq.com', 'a73e86815b9962266924e839f8703cb9', '9be1fba90b8270f1d9f6052b994061210e1ba111e943685f6384fb462abe650292bf59dfd6d54a41be3009ec554866c2', 'NORMAL');
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (53, '2025-07-02 10:41:33', '2025-07-02 10:41:33', '陈小美', '13355557788@qq.com', 'b6cba72e09ae53ecb3c629bb8cfeb12f', 'f0bd5bfea63e61cb5843c7f9b609b56f9d75a803e2ab2734112fc319cafadb33d50d800185d94ba1b5386c4d0c42f48b', 'NORMAL');
INSERT INTO `user` (`id`, `gmt_create`, `gmt_modified`, `user_name`, `email`, `phone_number`, `password`, `identity`) VALUES (54, '2025-07-02 10:42:02', '2025-07-02 10:42:02', '邱小帅', '16677778899@qq.com', '40b9796b03aa0c964aa05f1e5bce05d5', 'add3fa6e914792e76e5f166ffd5bf2a17a0f2ce70e4f2b1e9e615c957e6ce2bb7f34a0cb67b649b1abc28422aa158fb7', 'NORMAL');
COMMIT;

-- ----------------------------
-- Table structure for winning_record
-- ----------------------------
DROP TABLE IF EXISTS `winning_record`;
CREATE TABLE `winning_record` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `activity_id` bigint NOT NULL COMMENT '活动id',
  `activity_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '活动名称',
  `prize_id` bigint NOT NULL COMMENT '奖品id',
  `prize_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '奖品名称',
  `prize_tier` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '奖品等级',
  `winner_id` bigint NOT NULL COMMENT '中奖人id',
  `winner_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '中奖人姓名',
  `winner_email` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '中奖人邮箱',
  `winner_phone_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '中奖人电话',
  `winning_time` datetime NOT NULL COMMENT '中奖时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_id` (`id`) USING BTREE,
  UNIQUE KEY `uk_w_a_p_id` (`winner_id`,`activity_id`,`prize_id`) USING BTREE,
  KEY `idx_activity_id` (`activity_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of winning_record
-- ----------------------------
BEGIN;
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (71, '2025-07-11 17:58:36', '2025-07-11 17:58:36', 30, '1111', 20, '华为手机', 'FIRST_PRIZE', 52, '邱俊俊', '1254638@qq.com', 'a73e86815b9962266924e839f8703cb9', '2025-07-11 17:58:36');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (75, '2025-07-15 11:32:48', '2025-07-15 11:32:48', 32, '123', 23, 'MacBook Pro', 'FIRST_PRIZE', 51, '陈多多', '1677231572@qq.com', '90bea188ceb4214e76c4bc34b6db2f80', '2025-07-15 11:32:48');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (76, '2025-07-15 11:34:03', '2025-07-15 11:34:03', 32, '123', 22, '乐事薯片', 'FIRST_PRIZE', 52, '邱俊俊', '1477527150@qq.com', 'a73e86815b9962266924e839f8703cb9', '2025-07-15 11:34:03');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (77, '2025-07-15 21:03:53', '2025-07-15 21:03:53', 36, '测试 111', 18, '保温杯', 'FIRST_PRIZE', 53, '陈小美', '13355557788@qq.com', 'b6cba72e09ae53ecb3c629bb8cfeb12f', '2025-07-15 21:03:53');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (78, '2025-07-15 21:05:12', '2025-07-15 21:05:12', 37, '谁能得到邱俊航的爱意活动', 23, 'MacBook Pro', 'FIRST_PRIZE', 54, '邱小帅', '16677778899@qq.com', '40b9796b03aa0c964aa05f1e5bce05d5', '2025-07-15 21:05:13');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (79, '2025-07-15 21:05:17', '2025-07-15 21:05:17', 37, '谁能得到邱俊航的爱意活动', 24, '山姆会员卡', 'SECOND_PRIZE', 52, '邱俊俊', '1477527150@qq.com', 'a73e86815b9962266924e839f8703cb9', '2025-07-15 21:05:18');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (80, '2025-07-15 21:06:45', '2025-07-15 21:06:45', 38, '邱俊俊大礼包活动', 23, 'MacBook Pro', 'FIRST_PRIZE', 51, '陈多多', '1677231572@qq.com', '90bea188ceb4214e76c4bc34b6db2f80', '2025-07-15 21:06:45');
INSERT INTO `winning_record` (`id`, `gmt_create`, `gmt_modified`, `activity_id`, `activity_name`, `prize_id`, `prize_name`, `prize_tier`, `winner_id`, `winner_name`, `winner_email`, `winner_phone_number`, `winning_time`) VALUES (81, '2025-07-15 21:06:52', '2025-07-15 21:06:52', 38, '邱俊俊大礼包活动', 24, '山姆会员卡', 'SECOND_PRIZE', 52, '邱俊俊', '1477527150@qq.com', 'a73e86815b9962266924e839f8703cb9', '2025-07-15 21:06:53');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
