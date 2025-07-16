/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029 (8.0.29)
 Source Host           : 127.0.0.1:3306
 Source Schema         : tsdrs

 Target Server Type    : MySQL
 Target Server Version : 80029 (8.0.29)
 File Encoding         : 65001

 Date: 23/03/2025 20:16:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for analysis_history
-- ----------------------------
DROP TABLE IF EXISTS `analysis_history`;
CREATE TABLE `analysis_history`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `analysis_result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` tinyint NOT NULL DEFAULT 0,
  `created_at` datetime NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of analysis_history
-- ----------------------------
INSERT INTO `analysis_history` VALUES (1, '00088雾天.png', '图片', 'test', 1, '2024-12-19 01:00:00', 'admin');
INSERT INTO `analysis_history` VALUES (2, 'test.png', '图片', 'aaa', 1, '2024-12-19 00:00:00', 'test');
INSERT INTO `analysis_history` VALUES (3, '雨天.png', '图片', 'test', 1, '2024-12-17 16:23:07', 'admin');
INSERT INTO `analysis_history` VALUES (5, '模糊.png', '图片', 'test', 1, '2024-12-17 16:24:08', 'admin');
INSERT INTO `analysis_history` VALUES (6, '雨天2.png', '图片', 'test', 1, '2024-12-19 23:08:12', 'admin');
INSERT INTO `analysis_history` VALUES (7, '驾驶记录仪.mp4', '视频', 'test', 1, '2024-12-19 23:08:55', 'admin');
INSERT INTO `analysis_history` VALUES (8, '00018.png', '图片', 'http://web-empsys.oss-cn-beijing.aliyuncs.com/det_imgcb7ec59b.jpg', 1, '2024-12-20 10:21:12', 'admin');
INSERT INTO `analysis_history` VALUES (9, '00103模糊.png', '图片', 'http://web-empsys.oss-cn-beijing.aliyuncs.com/det_img76a5d9a2.jpg', 1, '2024-12-20 10:36:19', 'xiaoming');

-- ----------------------------
-- Table structure for member_points
-- ----------------------------
DROP TABLE IF EXISTS `member_points`;
CREATE TABLE `member_points`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `points` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '识别次数',
  `amount` int UNSIGNED NOT NULL DEFAULT 50 COMMENT '剩余额度',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE COMMENT '用户名索引'
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '识别次数及积分表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of member_points
-- ----------------------------
INSERT INTO `member_points` VALUES (1, 'admin', 2, 839);
INSERT INTO `member_points` VALUES (2, 'xiaoming', 1, 1049);

-- ----------------------------
-- Table structure for membership_record
-- ----------------------------
DROP TABLE IF EXISTS `membership_record`;
CREATE TABLE `membership_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `amount` int NOT NULL COMMENT '充值金额',
  `create_time` datetime NOT NULL COMMENT '充值时间',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '状态',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '会员充值记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of membership_record
-- ----------------------------
INSERT INTO `membership_record` VALUES (1, 'admin', 100, '2024-12-18 01:32:24', 'SUCCESS', '初级会员');
INSERT INTO `membership_record` VALUES (4, 'admin', 200, '2024-12-18 22:41:32', 'SUCCESS', '中级会员');
INSERT INTO `membership_record` VALUES (7, 'admin', 300, '2024-12-18 22:58:02', 'SUCCESS', '高级会员');
INSERT INTO `membership_record` VALUES (10, 'admin', 200, '2024-12-19 10:20:57', 'SUCCESS', '中级会员');
INSERT INTO `membership_record` VALUES (11, 'admin', 300, '2024-12-19 10:34:29', 'SUCCESS', '高级会员');
INSERT INTO `membership_record` VALUES (12, 'admin', 400, '2024-12-19 10:46:20', 'SUCCESS', '至尊会员');
INSERT INTO `membership_record` VALUES (15, 'admin', 100, '2024-12-19 17:14:15', 'SUCCESS', '初级会员');
INSERT INTO `membership_record` VALUES (16, 'xiaoming', 200, '2024-12-20 10:44:58', 'PENDING', '中级会员');
INSERT INTO `membership_record` VALUES (17, 'xiaoming', 300, '2024-12-20 10:45:04', 'PENDING', '高级会员');
INSERT INTO `membership_record` VALUES (18, 'xiaoming', 300, '2024-12-20 10:45:11', 'PENDING', '高级会员');
INSERT INTO `membership_record` VALUES (19, 'xiaoming', 200, '2024-12-20 10:45:20', 'PENDING', '中级会员');

-- ----------------------------
-- Table structure for opinions
-- ----------------------------
DROP TABLE IF EXISTS `opinions`;
CREATE TABLE `opinions`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `opinion_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `created_at` date NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of opinions
-- ----------------------------
INSERT INTO `opinions` VALUES (1, 'admin', '好', '2024-12-18');
INSERT INTO `opinions` VALUES (2, 'admin', '可以，但是有一些缺点：1.....2....', '2024-12-18');
INSERT INTO `opinions` VALUES (3, 'new', 'ok', '2024-12-18');
INSERT INTO `opinions` VALUES (4, 'user', '出问题了，详细加联系方式：12345678', '2024-12-19');
INSERT INTO `opinions` VALUES (6, 'visitor', '希望参与项目维护', '2024-12-16');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密码',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `regdate` date NOT NULL COMMENT '注册日期',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username` ASC) USING BTREE COMMENT '用户名索引'
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 'test', '$2a$10$9/ebwrH.j4GPWF3cyZGO/uZ1in5ONzwNs9CZyTtlhhY5aHzxwy6.K', NULL, '0000-00-00');
INSERT INTO `user_info` VALUES (3, 'newuser', '$2a$10$nK0fwxE4iZ42HUClECJRrORgHuf6KInTtBbnRXr3NAXY4Kj6oN/GC', NULL, '0000-00-00');
INSERT INTO `user_info` VALUES (4, 'new', '$2a$10$aDwGLVNl0C2tHGqR.IRraeElQ/jssxeonpqQbSlZf.xHq5/ATSYa.', NULL, '0000-00-00');
INSERT INTO `user_info` VALUES (5, 'admin', '$2a$10$3KR8AiTQLz4k2M8yO3zzZOiUn/SIznSMaq2LAQhmDd5Ukd0C7AweW', 'http://web-empsys.oss-cn-beijing.aliyuncs.com//d7560740-dba7-4dad-a92d-9600a44e6d05.webp', '2024-12-16');
INSERT INTO `user_info` VALUES (7, 'xiaoming', '$2a$10$SASnp6njtqd05ad5GGuGAeH7FrzZQsIffCPj0BUtPwUiW7Yq0dv3m', NULL, '2024-12-20');

SET FOREIGN_KEY_CHECKS = 1;
