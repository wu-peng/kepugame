/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50610
Source Host           : localhost:3306
Source Database       : kepugame_db

Target Server Type    : MYSQL
Target Server Version : 50610
File Encoding         : 65001

Date: 2016-08-31 13:31:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user_score
-- ----------------------------
DROP TABLE IF EXISTS `user_score`;
CREATE TABLE `user_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weixin_id` int(11) DEFAULT NULL,
  `score` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_score
-- ----------------------------
INSERT INTO `user_score` VALUES ('1', '1', '20');
INSERT INTO `user_score` VALUES ('2', '2', '16');

-- ----------------------------
-- Table structure for user_weixin
-- ----------------------------
DROP TABLE IF EXISTS `user_weixin`;
CREATE TABLE `user_weixin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openid` varchar(255) DEFAULT NULL COMMENT '用户的唯一标识',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `sex` varchar(255) DEFAULT NULL COMMENT '用户的性别，值为1时是男性，值为2时是女性，值为0时是未知',
  `province` varchar(255) DEFAULT NULL COMMENT '用户个人资料填写的省份',
  `city` varchar(255) DEFAULT NULL COMMENT '普通用户个人资料填写的城市',
  `country` varchar(255) DEFAULT NULL COMMENT '国家，如中国为CN',
  `headimgurl` varchar(255) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_weixin
-- ----------------------------
INSERT INTO `user_weixin` VALUES ('1', 'q1', 'Test', '1', 'dsdd', 'ssss', 'CN', '1472480300989.jpg');

-- ----------------------------
-- View structure for game_score
-- ----------------------------
DROP VIEW IF EXISTS `game_score`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost`  VIEW `game_score` AS SELECT
user_weixin.nickname,
user_score.score
FROM
user_score ,
user_weixin
WHERE
user_score.weixin_id = user_weixin.id ;
