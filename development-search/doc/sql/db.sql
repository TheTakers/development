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
  `id` varchar(50) NOT NULL,
  `sqlId` varchar(50) DEFAULT NULL,
  `sqlName` varchar(50) DEFAULT NULL COMMENT '别名',
  `groupId` varchar(50) DEFAULT NULL COMMENT '所属分组',
  `selectSql` text COMMENT '查询SQL',
  `datasource` varchar(50) DEFAULT NULL COMMENT '数据源',
  `sqldesc` text COMMENT '排序',
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(50) DEFAULT NULL,
  `version` decimal(50,0) DEFAULT NULL COMMENT '版本号',
  `lastUpdateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  `lastUpdateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `cache` decimal(2,0) DEFAULT NULL COMMENT '缓存数据',
  `sqlExpand` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
  `id` varchar(50) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `createUser` varchar(50) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  `lastUpdateTime` timestamp NULL DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL COMMENT '视图编码',
  `name` varchar(50) DEFAULT NULL COMMENT '视图名称',
  `showRowNum` decimal(10,0) DEFAULT NULL COMMENT '是否显示行号',
  `multiple` decimal(10,0) DEFAULT NULL COMMENT '选项(单选,多选)',
  `conditions` text COMMENT '过滤条件',
  `buttons` text COMMENT '按钮',
  `treeData` text COMMENT '功能树',
  `sqlId` varchar(50) DEFAULT NULL COMMENT 'SQLID',
  `remark` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;

-- ----------------------------
-- Table structure for tb_sm_view_field
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view_field`;
CREATE TABLE `tb_sm_view_field` (
  `id` varchar(50) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `createUser` varchar(50) DEFAULT NULL,
  `createTime` timestamp NULL DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  `lastUpdateTime` timestamp NULL DEFAULT NULL,
  `viewId` varchar(50) DEFAULT NULL COMMENT '视图标识',
  `title` varchar(50) DEFAULT NULL COMMENT '视图名称',
  `field` varchar(50) DEFAULT NULL COMMENT '字段名称',
  `isDisplay` int(2) DEFAULT NULL COMMENT '是否显示',
  `expand` varchar(500) DEFAULT NULL COMMENT '扩展字段',
  `isSort` decimal(10,0) DEFAULT NULL COMMENT '是否排序',
  `dataType` varchar(50) DEFAULT NULL,
  `idx` int(2) DEFAULT NULL,
  `componentType` varchar(20) DEFAULT NULL COMMENT '控件类型',
  `isSearch` int(2) DEFAULT '0' COMMENT '详情查看',
  `isUpdate` int(2) DEFAULT '0' COMMENT '是否修改',
  `length` varchar(10) DEFAULT NULL,
  `rule` text COMMENT '验证规则'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
