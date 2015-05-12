/*
Navicat MySQL Data Transfer

Source Server         : 192.168.80.128
Source Server Version : 50624
Source Host           : 192.168.80.128:3306
Source Database       : roadshow

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-05-12 22:38:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `person`
-- ----------------------------
DROP TABLE IF EXISTS `person`;
CREATE TABLE `person` (
  `name` varchar(50) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of person
-- ----------------------------
INSERT INTO `person` VALUES ('张三', '2015-05-11 01:22:33');
INSERT INTO `person` VALUES ('李四', '2015-05-12 02:32:33');
INSERT INTO `person` VALUES ('王五', '2015-05-13 01:22:33');