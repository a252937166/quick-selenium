/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 127.0.0.1
 Source Database       : comic

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 02/22/2018 15:05:42 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `comic_area`
-- ----------------------------
DROP TABLE IF EXISTS `comic_area`;
CREATE TABLE `comic_area` (
  `id` varchar(50) NOT NULL,
  `name` varchar(20) DEFAULT NULL COMMENT '地区名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁版本号',
  `status` int(11) DEFAULT '1' COMMENT '状态 1有效 -1无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漫画地区表';

-- ----------------------------
--  Table structure for `comic_basic`
-- ----------------------------
DROP TABLE IF EXISTS `comic_basic`;
CREATE TABLE `comic_basic` (
  `id` varchar(50) NOT NULL,
  `name` varchar(30) DEFAULT NULL COMMENT '漫画名',
  `author` varchar(30) DEFAULT NULL COMMENT '漫画作者',
  `check_num` int(11) DEFAULT '0' COMMENT '点击量',
  `serialize_status` tinyint(4) DEFAULT '1' COMMENT '连载状态 1未完结 -1完结',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` int(11) DEFAULT '1' COMMENT '状态 1有效 -1 失效',
  `introduction` varchar(300) DEFAULT NULL,
  `cover` varchar(300) DEFAULT NULL COMMENT '封面',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漫画基本信息表';

-- ----------------------------
--  Table structure for `comic_basic_area`
-- ----------------------------
DROP TABLE IF EXISTS `comic_basic_area`;
CREATE TABLE `comic_basic_area` (
  `id` varchar(50) NOT NULL,
  `basic_id` varchar(50) DEFAULT NULL COMMENT '漫画基本表ID',
  `area_id` varchar(50) DEFAULT NULL COMMENT '漫画地区表ID',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁版本号',
  `status` int(11) DEFAULT '1' COMMENT '状态',
  PRIMARY KEY (`id`),
  KEY `comic_basic_area_basic_id_area_id_index` (`basic_id`,`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漫画地区中间表';

-- ----------------------------
--  Table structure for `comic_basic_type`
-- ----------------------------
DROP TABLE IF EXISTS `comic_basic_type`;
CREATE TABLE `comic_basic_type` (
  `id` varchar(50) NOT NULL,
  `basic_id` varchar(50) DEFAULT NULL COMMENT '漫画基本表ID',
  `type_id` varchar(50) DEFAULT NULL COMMENT '漫画分类表ID',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `version` int(11) DEFAULT NULL COMMENT '乐观锁版本号',
  `status` int(11) DEFAULT '1' COMMENT '状态 -1失效 1有效',
  PRIMARY KEY (`id`),
  KEY `comic_basic_type_basic_id_type_id_index` (`basic_id`,`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漫画类型中间表';

-- ----------------------------
--  Table structure for `comic_chapter`
-- ----------------------------
DROP TABLE IF EXISTS `comic_chapter`;
CREATE TABLE `comic_chapter` (
  `id` varchar(32) NOT NULL,
  `basic_id` varchar(32) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `chapter_no` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `origin_name` varchar(50) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comic_chapter_basic_id_pk` (`basic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
--  Table structure for `comic_content`
-- ----------------------------
DROP TABLE IF EXISTS `comic_content`;
CREATE TABLE `comic_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_id` varchar(32) DEFAULT NULL,
  `img_url` varchar(500) DEFAULT NULL,
  `page_no` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `file_name` varchar(50) DEFAULT NULL,
  `original_url` varchar(500) DEFAULT NULL COMMENT '原始地址',
  PRIMARY KEY (`id`),
  KEY `comic_contant_basic_id_pk` (`chapter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=54276 DEFAULT CHARSET=utf8mb4 COMMENT='漫画内容表';

-- ----------------------------
--  Table structure for `comic_error_content`
-- ----------------------------
DROP TABLE IF EXISTS `comic_error_content`;
CREATE TABLE `comic_error_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `chapter_id` varchar(32) DEFAULT NULL,
  `img_url` varchar(500) DEFAULT NULL,
  `page_no` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '1',
  `file_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='漫画内容表';

SET FOREIGN_KEY_CHECKS = 1;
