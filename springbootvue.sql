/*
 Navicat Premium Data Transfer

 Source Server         : 数据库1
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : springbootvue

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 19/02/2022 21:40:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `size` bigint(20) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `is_delete` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '是否禁用',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '文件md5',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
INSERT INTO `sys_file` VALUES (13, '005.jpg', 'jpg', 269, 'http://localhost:9090/file/49e9ac7e944f4a1099b9a5b2d2cf1816.jpg', 0, 1, 'fc2dea6c36ba5e5be52c4bddc1775dc3');
INSERT INTO `sys_file` VALUES (14, '49e9ac7e944f4a1099b9a5b2d2cf1816.jpg', 'jpg', 269, 'http://localhost:9090/file/49e9ac7e944f4a1099b9a5b2d2cf1816.jpg', 1, 1, 'fc2dea6c36ba5e5be52c4bddc1775dc3');
INSERT INTO `sys_file` VALUES (15, 'a53227f6c0da4f08904e63a87b05092f.jpg', 'jpg', 515, 'http://localhost:9090/file/9e80c2cbad3941a8bd5f9400ba8d42d5.jpg', 1, 1, '315ab9a0aab968862a290f89086c768c');
INSERT INTO `sys_file` VALUES (16, '5aa88fbbbc7c5.jpg', 'jpg', 278, 'http://localhost:9090/file/7ef2ffd37f1a4cb9b3fb8c28dbba4563.jpg', 0, 1, 'eb9fe371d701747e20e65a76fa145747');
INSERT INTO `sys_file` VALUES (17, '6d10dc04e5194535a69bc5ccff713d5e.jpg', 'jpg', 427, 'http://localhost:9090/file/6784299604f64c229bff2e57f53cec1f.jpg', 0, 1, '31427c970ac50c1e998f20fffd9e4d86');
INSERT INTO `sys_file` VALUES (18, '005.jpg', 'jpg', 269, 'http://localhost:9090/file/49e9ac7e944f4a1099b9a5b2d2cf1816.jpg', 0, 1, 'fc2dea6c36ba5e5be52c4bddc1775dc3');
INSERT INTO `sys_file` VALUES (19, 'nzmqeg.jpg', 'jpg', 848, 'http://localhost:9090/file/13c08e345d22478f9be3c1889ee88246.jpg', 0, 1, 'df23376fe2e5bc10e90ef9ce0c356a01');
INSERT INTO `sys_file` VALUES (20, 'nzmqeg.jpg', 'jpg', 848, 'http://localhost:9090/file/13c08e345d22478f9be3c1889ee88246.jpg', 0, 1, 'df23376fe2e5bc10e90ef9ce0c356a01');
INSERT INTO `sys_file` VALUES (21, 'nzmqeg.jpg', 'jpg', 848, 'http://localhost:9090/file/13c08e345d22478f9be3c1889ee88246.jpg', 0, 1, 'df23376fe2e5bc10e90ef9ce0c356a01');
INSERT INTO `sys_file` VALUES (22, 'nzmqeg.jpg', 'jpg', 848, 'http://localhost:9090/file/13c08e345d22478f9be3c1889ee88246.jpg', 0, 1, 'df23376fe2e5bc10e90ef9ce0c356a01');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '1', '管理员', '222', '广西', '2022-02-12 21:56:32', 'https://img-blog.csdnimg.cn/c6d0ece75d3f4833bd820b8aa2eb952b.png');
INSERT INTO `sys_user` VALUES (6, '1', '1', '5', '3', '天津', '2022-02-16 00:28:26', 'http://localhost:9090/file/13c08e345d22478f9be3c1889ee88246.jpg');
INSERT INTO `sys_user` VALUES (8, 'T', '5', 'qwe', '123', '天津', '2022-02-04 20:38:23', NULL);
INSERT INTO `sys_user` VALUES (9, 'D', '1', 'as', '5', NULL, '2022-02-04 20:01:40', NULL);
INSERT INTO `sys_user` VALUES (10, 'E', '1', 'zxc', '6', NULL, '2022-02-04 20:01:41', NULL);
INSERT INTO `sys_user` VALUES (11, 'F', '1', NULL, '7', NULL, '2022-02-04 16:46:57', NULL);
INSERT INTO `sys_user` VALUES (19, '2', '1', '管理员', '1111', '广西', '2022-04-12 20:53:09', '');
INSERT INTO `sys_user` VALUES (20, 'A', '1', '1', '1', '天津', '2022-05-06 20:34:27', '');
INSERT INTO `sys_user` VALUES (21, 'T', '5', 'qwe', '123', '天津', '2022-05-06 20:34:27', '');
INSERT INTO `sys_user` VALUES (22, 'D', '1', 'as', '5', '', '2022-06-06 20:34:27', '');
INSERT INTO `sys_user` VALUES (23, 'E', '1', 'zxc', '6', '', '2022-06-06 20:34:27', '');
INSERT INTO `sys_user` VALUES (24, 'F', '1', '', '7', '', '2022-07-06 20:34:27', '');
INSERT INTO `sys_user` VALUES (25, '5', '1', '管理员', '1111', '广西', '2022-07-12 20:53:11', '');
INSERT INTO `sys_user` VALUES (26, '有', '1', '3', '2', '天津', '2022-08-06 20:49:44', '');
INSERT INTO `sys_user` VALUES (27, 'Tu', '5', 'qwe', '123', '天津', '2022-08-06 20:49:44', '');
INSERT INTO `sys_user` VALUES (28, 'j\'h\'f', '1', 'as', '5', '', '2022-09-06 20:49:44', '');
INSERT INTO `sys_user` VALUES (29, 'f\'g\'h', '1', 'zxc', '6', '', '2022-09-06 20:49:44', '');
INSERT INTO `sys_user` VALUES (40, 'sir', '1', '先生', 'xiansheng', '555', '2022-11-12 22:40:35', NULL);
INSERT INTO `sys_user` VALUES (42, 'uyt6', NULL, NULL, NULL, NULL, '2022-11-16 01:08:37', NULL);
INSERT INTO `sys_user` VALUES (43, NULL, NULL, NULL, '6', NULL, '2022-12-16 01:09:01', NULL);

SET FOREIGN_KEY_CHECKS = 1;
