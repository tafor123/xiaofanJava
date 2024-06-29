/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80031 (8.0.31)
 Source Host           : localhost:3306
 Source Schema         : honey2024

 Target Server Type    : MySQL
 Target Server Version : 80031 (8.0.31)
 File Encoding         : 65001

 Date: 29/06/2024 13:24:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username12`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (8, 'xf4', '123', '小凡1', '12345', '123@136.com', '西安', NULL, '用户');
INSERT INTO `user` VALUES (9, 'sd', '123', 'sd', NULL, NULL, NULL, NULL, '用户');
INSERT INTO `user` VALUES (10, 'admin', '123', 'admin', '136375643', NULL, NULL, 'http://localhost:9090/file/download/1694678258561_logo1.png', '管理员');
INSERT INTO `user` VALUES (14, 'haohuang', NULL, '浩黄', '13456567879', '145@qq.com', '汉中', 'http://localhost:9090/file/download/1695042089447_image.png', '用户');
INSERT INTO `user` VALUES (15, 'bobo', '123', '波波', '1234567', '1234@qq.com', '榆林', 'http://localhost:9090/file/download/head4.jpg', '用户');
INSERT INTO `user` VALUES (16, '磊顾', '123', 'sd', '135678', '', '太原', 'http://localhost:9090/file/download/head3.jpg', '用户');
INSERT INTO `user` VALUES (17, 'haohao', '', '浩黄', '13456567879', '145@qq.com', '汉中', 'http://localhost:9090/file/download/1695042089447_image.png', '用户');
INSERT INTO `user` VALUES (18, 'heihei', '123', '波波', '1234567', '1234@qq.com', '榆林', 'http://localhost:9090/file/download/head4.jpg', '用户');
INSERT INTO `user` VALUES (19, 'hh', '123', 'hhhh', '123', '123@qq.com', '汉中市', NULL, '用户');
INSERT INTO `user` VALUES (20, 'user1234', 'Password123', 'user1234', NULL, NULL, NULL, NULL, 'USER');

SET FOREIGN_KEY_CHECKS = 1;
