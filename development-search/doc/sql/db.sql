/*
Navicat MySQL Data Transfer

Source Server         : 192.168.32.177
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : development

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2016-10-12 14:41:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_basic_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_dict`;
CREATE TABLE `tb_basic_dict` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`value`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`text`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ,
`remark`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parent`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`idx`  decimal(5,0) NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT 0 ,
`createuser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  datetime NULL DEFAULT NULL ,
INDEX `idx_unqiue_id` (`id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_basic_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_menu`;
CREATE TABLE `tb_basic_menu` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pid`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' ,
`ico`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标' ,
`link`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`path`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remark`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`idx`  decimal(5,0) NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT 0 ,
`createuser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  datetime NULL DEFAULT NULL ,
INDEX `idx_unqiue_id` (`id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_sm_sqldefine
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_sqldefine`;
CREATE TABLE `tb_sm_sqldefine` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`sqlId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sqlName`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`groupId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`selectSql`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`datasource`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sqldesc`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`createUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`version`  decimal(50,0) NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  datetime NULL DEFAULT NULL ,
`status`  decimal(2,0) NULL DEFAULT NULL ,
`cache`  decimal(2,0) NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_sm_sqlgroup
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_sqlgroup`;
CREATE TABLE `tb_sm_sqlgroup` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`code`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`parentId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`path`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remark`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT NULL ,
`createUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  datetime NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  datetime NULL DEFAULT NULL ,
INDEX `idx_unqiue_id` (`id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_sm_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view`;
CREATE TABLE `tb_sm_view` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT NULL ,
`createUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  timestamp NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  timestamp NULL DEFAULT NULL ,
`code`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`showRowNum`  decimal(10,0) NULL DEFAULT NULL ,
`multiple`  decimal(10,0) NULL DEFAULT NULL ,
`conditions`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`buttons`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`treedata`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`sqlexpand`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`mainsql`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`sqlId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_sm_view_field
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view_field`;
CREATE TABLE `tb_sm_view_field` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT NULL ,
`createUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  timestamp NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  timestamp NULL DEFAULT NULL ,
`viewId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`field`  decimal(10,0) NULL DEFAULT NULL ,
`isdisplay`  decimal(10,0) NULL DEFAULT NULL ,
`expand`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`isSort`  decimal(10,0) NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for tb_sm_view_fieldsetting
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view_fieldsetting`;
CREATE TABLE `tb_sm_view_fieldsetting` (
`id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`version`  decimal(10,0) NULL DEFAULT NULL ,
`createUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`createTime`  timestamp NULL DEFAULT NULL ,
`lastUpdateUser`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`lastUpdateTime`  timestamp NULL DEFAULT NULL ,
`viewId`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`title`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`field`  decimal(10,0) NULL DEFAULT NULL ,
`expand`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`sort`  decimal(10,0) NULL DEFAULT NULL ,
`required`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`componentType`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;
