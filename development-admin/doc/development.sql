/*
Navicat MySQL Data Transfer

Source Server         : 192.168.32.177
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : development

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-02-17 14:58:44
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_basic_code_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_code_template`;
CREATE TABLE `tb_basic_code_template` (
  `id` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL COMMENT '模板名称',
  `template` text COMMENT '模板文本',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `filepath` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `version` decimal(10,0) DEFAULT '0',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_code_template
-- ----------------------------
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698e', 'domain', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.domain;\r\n\r\n<#list vars.columnList as column>\r\n	<#if column.attr != \"id\" &&\r\n		 column.attr != \"version\"&&\r\n		 column.attr != \"createUser\"&&\r\n		 column.attr != \"createTime\"&&\r\n		 column.attr != \"lastUpdateUser\"&&\r\n		 column.attr != \"lastUpdateTime\" &&\r\n		 (column.package)??\r\n		 > \r\n${column.package}\r\n	</#if> \r\n</#list>\r\nimport javax.persistence.Column;\r\nimport javax.persistence.Entity;\r\nimport javax.persistence.Table;\r\nimport com.${vars.packageName}.domain.Auditable;\r\n\r\n/**\r\n * ${vars.comment}实体类\r\n * @author ${vars.author}\r\n */  \r\n@Entity\r\n@Table(name=\"${vars.tableName}\")\r\npublic class ${vars.beanName} extends Auditable{\r\n	/**\r\n	 * \r\n	 */\r\n	private static final long serialVersionUID = 1L;\r\n	\r\n	<#list vars.columnList as column>\r\n	<#if column.attr != \"id\" &&\r\n		 column.attr != \"version\"&&\r\n		 column.attr != \"createUser\"&&\r\n		 column.attr != \"createTime\"&&\r\n		 column.attr != \"lastUpdateUser\"&&\r\n		 column.attr != \"lastUpdateTime\"\r\n		 > \r\n	@Column(name=\"${column.name}\")\r\n	private ${column.dtype} ${column.attr};\r\n	\r\n	</#if> \r\n	</#list>\r\n\r\n	<#list vars.columnList as column>\r\n	<#if column.attr != \"id\" &&\r\n		 column.attr != \"version\"&&\r\n		 column.attr != \"createUser\"&&\r\n		 column.attr != \"createTime\"&&\r\n		 column.attr != \"lastUpdateUser\"&&\r\n		 column.attr != \"lastUpdateTime\"\r\n		 > \r\n	/**\r\n	 * ${column.name}\r\n	 */\r\n	public ${column.dtype} get${column.methodName}() {\r\n		return ${column.attr};\r\n	}\r\n	public void set${column.methodName}(${column.dtype} ${column.attr}) {\r\n		this.${column.attr} = ${column.attr};\r\n	}\r\n	</#if> \r\n	</#list>\r\n	\r\n}\r\n', 'domain', '/domain/${module}/${beanName}.java', '0', null, '2016-11-02 15:10:19', 'admin', '2016-11-24 02:37:08');
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698f', 'controller', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.web.system;\r\n\r\nimport org.springframework.stereotype.Controller;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.servlet.ModelAndView;\r\nimport com.${vars.packageName}.api.BaseController;\r\n\r\n/**\r\n * ${vars.comment}控制器\r\n * @author ${vars.author}\r\n */\r\n@Controller\r\n@RequestMapping(\"/${vars.beanName}\")\r\npublic class ${vars.beanName}Controller extends BaseController{\r\n\r\n}\r\n', 'controller', '/web/${module}/${beanName}Controller.java', '0', null, '2017-02-03 10:07:21', null, '2017-01-16 10:07:23');
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698z', 'repository', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.repository;\r\n\r\nimport org.springframework.data.jpa.repository.JpaRepository;\r\nimport org.springframework.stereotype.Repository;\r\nimport com.${vars.packageName}.domain.${vars.beanName};\r\n/**\r\n * ${vars.comment} Repository\r\n * @author ${vars.author}\r\n */\r\n@Repository\r\npublic interface ${vars.beanName}Repository extends JpaRepository<${vars.beanName}, String>{\r\n\r\n}\r\n', 'repository', '/repository/${module}/${beanName}Repository.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698a', 'service', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.service;\r\n\r\nimport com.${vars.packageName}.repository.JpaRepository;\r\nimport com.${vars.packageName}.repository.${vars.beanName}Repository;\r\n\r\n/**\r\n * ${vars.comment}服务\r\n * @author ${vars.author}\r\n */   \r\npublic interface ${vars.beanName}Service  extends JpaRepository<${vars.beanName}Repository>{\r\n	\r\n}', 'service', '/service/${module}/${beanName}Service.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTbBKcXaR8698a', 'serviceImpl', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.service.impl;\r\n\r\nimport org.springframework.stereotype.Service;\r\nimport com.${vars.packageName}.repository.impl.JpaRepositoryImpl;\r\nimport com.${vars.packageName}.repository.${vars.beanName}Repository;\r\nimport com.${vars.packageName}.service.${vars.beanName}Service;\r\n\r\n/**\r\n * ${vars.comment}服务实现类\r\n * @author ${vars.author}\r\n */  \r\n@Service\r\npublic class ${vars.beanName}ServiceImpl extends JpaRepositoryImpl<${vars.beanName}Repository> implements ${vars.beanName}Service{\r\n	\r\n}\r\n', 'serviceImpl', '/service/${module}/impl/${beanName}ServiceImpl.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g574BVKTbBKcXaR8698a', 'api', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.${vars.packageName}.api;\r\n\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\nimport com.${vars.packageName}.api.BaseController;\r\n\r\n/**\r\n * ${vars.comment}API\r\n * @author ${vars.author}\r\n */\r\n@RestController\r\n@RequestMapping(value = \"/api/${vars.beanName}\")\r\npublic class ${vars.beanName}RestController extends BaseController {\r\n\r\n}\r\n', 'api', '/api/${module}/${beanName}Controller.java', '0', null, null, null, null);

-- ----------------------------
-- Table structure for tb_basic_dict
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_dict`;
CREATE TABLE `tb_basic_dict` (
  `id` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL COMMENT '值',
  `text` varchar(50) DEFAULT '0' COMMENT '文本值',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `parent` varchar(50) DEFAULT NULL COMMENT '所属字典',
  `idx` decimal(5,0) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT '0',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_dict
-- ----------------------------
INSERT INTO `tb_basic_dict` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698e', 'COMPONENTTYPE', '控件', '控件类型', '0', '0', '0', null, '2016-11-02 15:10:19', 'admin', '2016-11-24 02:37:08');
INSERT INTO `tb_basic_dict` VALUES ('015595f06985j3JXb9gCN86GFPKf51KV', 'TEXT', '文本框', null, '0155952ad3c1g534BVKTUBKcXaR8698e', '1111', '0', null, '2016-11-10 16:17:52', 'admin', '2016-11-02 23:46:12');
INSERT INTO `tb_basic_dict` VALUES ('0157b30549808Tkk1FaOHO5QPUDF0328', 'DROPDOWN', '下拉框', null, '0155952ad3c1g534BVKTUBKcXaR8698e', null, '0', null, '2016-11-18 16:08:32', 'admin', '2016-11-03 08:41:02');
INSERT INTO `tb_basic_dict` VALUES ('0157b3074bb6TQRITOWBifdg64e11dIQ', 'SELECTOR', '选择器', '889999999999999', '0155952ad3c1g534BVKTUBKcXaR8698e', '3366', '0', null, '2016-11-20 00:08:57', 'admin', '2016-11-19 11:25:38');
INSERT INTO `tb_basic_dict` VALUES ('0157b309ff4bcca8aV2kMHQ9dYBF6eF2', 'TEXTAREA', '文本域', null, '0155952ad3c1g534BVKTUBKcXaR8698e', null, '0', null, '2016-11-01 16:10:11', 'admin', '2016-11-03 08:41:14');
INSERT INTO `tb_basic_dict` VALUES ('0157b30b256fLaUHRG8mh8i1iA6l5dgV', 'GENERATECODE', '自动编码', null, '0155952ad3c1g534BVKTUBKcXaR8698e', null, '0', null, '2016-11-23 16:11:22', 'admin', '2016-11-03 10:10:51');
INSERT INTO `tb_basic_dict` VALUES ('0157b30c0096jl2AUYT0bKfImlFG15H0', 'CHECKBOX', '单选框', '单选框', '0155952ad3c1g534BVKTUBKcXaR8698e', '1', '0', null, null, null, null);
INSERT INTO `tb_basic_dict` VALUES ('0157b30c0096jl2AUYT0bKfImlFG15H1', 'DATEPICKER', '日期', null, '0155952ad3c1g534BVKTUBKcXaR8698e', null, '0', null, null, null, null);

-- ----------------------------
-- Table structure for tb_basic_res
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_res`;
CREATE TABLE `tb_basic_res` (
  `id` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pid` varchar(50) DEFAULT '0',
  `ico` varchar(50) DEFAULT NULL COMMENT '图标',
  `link` varchar(255) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  `remark` text,
  `idx` decimal(5,0) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT '0',
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_res
-- ----------------------------
INSERT INTO `tb_basic_res` VALUES ('70d39ffec3654d24977fa2518a6ab427', '控制台', '0', 'ti-spray', null, null, '系统信息', '3', '1', null, null, 'admin', '2016-08-29 16:52:27');
INSERT INTO `tb_basic_res` VALUES ('2ab11cc9846748d8aaa9247e96f730ef', '系统设置', '0', 'ti-light-bulb', null, '系统设置', null, '2', '0', 'admin', '2016-07-28 15:13:53', 'admin', '2016-07-28 15:13:53');
INSERT INTO `tb_basic_res` VALUES ('ccc32ee4e418491bac0ff184f4a2ed97', '菜单配置', '2ab11cc9846748d8aaa9247e96f730ef', 'ti-spray', '/basic/menu/index', '系统设置,菜单配置', '管理系统菜单', '4', '0', 'admin', '2016-07-28 15:21:13', 'admin', '2016-07-28 15:21:13');
INSERT INTO `tb_basic_res` VALUES ('3f042ab4546740298f1c2d685e097769', '查询配置', '002d69f4dab444f3ad1d18fd8dcf576b', 'ti-pencil-alt', '', null, '11', '5', '1', null, null, 'admin', '2016-08-12 16:57:29');
INSERT INTO `tb_basic_res` VALUES ('586f7d6d230b47008a649490af90d195', 'SQL分组', '3f042ab4546740298f1c2d685e097769', 'glyphicon glyphicon-text-width', '/search/sqlgroup/index', null, '3', '6', '1', null, null, 'admin', '2016-08-12 16:58:11');
INSERT INTO `tb_basic_res` VALUES ('9a88e1c6f0a14b52a76c00bb8a3adcd3', 'SQL', '3f042ab4546740298f1c2d685e097769', 'glyphicon glyphicon-chevron-left', '/search/sqldefine/index', '系统设置,查询配置,SQL', null, '7', '0', 'admin', '2016-07-28 15:27:38', 'admin', '2016-07-28 15:27:38');
INSERT INTO `tb_basic_res` VALUES ('5efa6a06d25c4b1da1fad2138125d37d', '系统上下文', '70d39ffec3654d24977fa2518a6ab427', 'glyphicon glyphicon-bookmark', null, '控制台,系统上下文', '系统上下文详情', '8', '0', 'admin', '2016-07-29 09:14:33', 'admin', '2016-07-29 09:14:33');
INSERT INTO `tb_basic_res` VALUES ('0', '菜单管理', '-1', null, null, null, null, '0', '0', null, '2016-07-29 09:14:33', null, null);
INSERT INTO `tb_basic_res` VALUES ('8eab212400824f83a50373658ef0b19a', '首页', '0', 'ti-home', '/home', '首页', '系统首页', '1', '1', null, '2016-07-01 09:14:33', 'admin', '2016-07-01 17:15:43');
INSERT INTO `tb_basic_res` VALUES ('002d69f4dab444f3ad1d18fd8dcf576b', '开发者中心', '0', 'ti-signal', '', null, '444', '9', '1', null, null, 'admin', '2016-08-12 09:16:17');
INSERT INTO `tb_basic_res` VALUES ('dbf610a771bc43bbab89662462fbc228', '数据字典', '002d69f4dab444f3ad1d18fd8dcf576b', 'ti-spray', '/search/sqlview/index/20161024101259', null, '22222', '10', '8', null, null, 'admin', '2016-10-24 15:18:16');
INSERT INTO `tb_basic_res` VALUES ('a75f30a215bc43b3bec5bf3abc0157d1', '视图管理', '002d69f4dab444f3ad1d18fd8dcf576b', 'ti-pencil-alt', '/search/sqlview/index', null, '2', '11', '2', null, null, 'admin', '2016-09-12 11:41:44');
INSERT INTO `tb_basic_res` VALUES ('91737abb1ff04863916f60044c59c593', '代码模板', '002d69f4dab444f3ad1d18fd8dcf576b', null, '/search/sqlview/index/201701101023479230', null, '当前项目代码模板管理', null, '0', 'admin', '2017-01-10 10:25:45', 'admin', '2017-01-10 10:25:45');
INSERT INTO `tb_basic_res` VALUES ('c4d526a75b404860a2fad924ca5f7681', 'SQL查询视图', '002d69f4dab444f3ad1d18fd8dcf576b', null, '/search/sqlview/index/201701110611558331', null, null, null, '1', null, null, 'admin', '2017-01-11 18:13:31');

-- ----------------------------
-- Table structure for tb_basic_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_role`;
CREATE TABLE `tb_basic_role` (
  `id` varchar(32) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `status` int(2) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_role
-- ----------------------------

-- ----------------------------
-- Table structure for tb_basic_role_res_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_role_res_relation`;
CREATE TABLE `tb_basic_role_res_relation` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `resource_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_role_res_relation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_basic_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `tb_basic_user_role_relation`;
CREATE TABLE `tb_basic_user_role_relation` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_update_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `last_update_user` varchar(50) DEFAULT NULL,
  `version` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_basic_user_role_relation
-- ----------------------------

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
  `create_time` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `version` decimal(50,0) DEFAULT NULL COMMENT '版本号',
  `last_Update_User` varchar(50) DEFAULT NULL COMMENT '修改人',
  `last_Update_Time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` decimal(2,0) DEFAULT NULL COMMENT '状态',
  `cache` decimal(2,0) DEFAULT NULL COMMENT '缓存数据',
  `masterTableId` varchar(50) DEFAULT NULL,
  `masterTable` varchar(50) DEFAULT NULL,
  `sqlExpand` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sm_sqldefine
-- ----------------------------
INSERT INTO `tb_sm_sqldefine` VALUES ('f70ea5e32a374fb1be288387327b02b1', '20161024101134', '数据字典', '1ed813273add435381a8f2a319cc0eb0', 'select t.*,b.text parentText from tb_basic_dict t \nleft join tb_basic_dict b on t.parent = b.id', 'jdbcTemplateMaster', '系统数据字典查询', null, null, '2', 'admin', '2016-11-04 14:19:29', '2', '1', 'id', 'tb_basic_dict', null);
INSERT INTO `tb_sm_sqldefine` VALUES ('43f8d8b5039a40d8aedea7ffb0066543', '20161116041618', 'SQL定义视图', '1ed813273add435381a8f2a319cc0eb0', 'select * from tb_sm_sqldefine', 'jdbcTemplateMaster', 'SQL定义视图查询', '2016-11-16 16:17:06', 'admin', '0', 'admin', '2016-11-16 16:17:06', null, '1', 'id', 'tb_sm_sqldefine', null);
INSERT INTO `tb_sm_sqldefine` VALUES ('6f145a962c9c467f9d2e6da81b02b7c7', '201701101022146336', '代码模板查询', '1ed813273add435381a8f2a319cc0eb0', 'select * from tb_basic_code_template', 'jdbcTemplateMaster', null, null, null, '1', 'admin', '2017-01-10 10:23:08', null, '1', 'id', 'tb_basic_code_template', null);

-- ----------------------------
-- Table structure for tb_sm_sqlgroup
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_sqlgroup`;
CREATE TABLE `tb_sm_sqlgroup` (
  `id` varchar(50) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `parentid` varchar(50) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `create_User` varchar(50) DEFAULT NULL,
  `create_Time` datetime DEFAULT NULL,
  `last_Update_User` varchar(50) DEFAULT NULL,
  `last_Update_Time` datetime DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sm_sqlgroup
-- ----------------------------
INSERT INTO `tb_sm_sqlgroup` VALUES ('74c005535128491294dd3c690f1ad4b9', '20160722045130', 'SQL组', '0', null, 'SQL分组管理', '3', null, null, 'admin', '2016-10-20 19:11:41');
INSERT INTO `tb_sm_sqlgroup` VALUES ('1ed813273add435381a8f2a319cc0eb0', '20160728110151', '基础服务', '74c005535128491294dd3c690f1ad4b9', null, '系统初始化服务', '1', null, null, 'admin', '2016-09-06 19:24:22');

-- ----------------------------
-- Table structure for tb_sm_view
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view`;
CREATE TABLE `tb_sm_view` (
  `id` varchar(50) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `create_User` varchar(50) DEFAULT NULL,
  `create_Time` datetime DEFAULT NULL,
  `last_Update_User` varchar(50) DEFAULT NULL,
  `last_Update_Time` datetime DEFAULT NULL,
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

-- ----------------------------
-- Records of tb_sm_view
-- ----------------------------
INSERT INTO `tb_sm_view` VALUES ('3fae66ee0b714c778dbeb6856a35d9cb', '56', null, null, 'admin', '2017-01-11 18:24:35', '20161024101259', '数据字典视图', '0', '0', '[{\"field\":\"value\",\"isSort\":0,\"title\":\"值\",\"dataType\":\"VARCHAR\",\"expr\":\"LIKE\",\"componentType\":\"TEXT\"},{\"field\":\"text\",\"isSort\":0,\"title\":\"文本值\",\"dataType\":\"VARCHAR\",\"expr\":\"LIKE\",\"componentType\":\"TEXT\"},{\"field\":\"parentText\",\"dataType\":\"VARCHAR\",\"title\":\"上级\",\"idx\":2,\"expand\":\"\",\"expr\":\"LIKE\",\"componentType\":\"TEXT\"}]', '[{\"id\":\"10001\",\"icon\":\"md md-add\",\"title\":\"增加\",\"showWin\":1,\"winSize\":\"40\",\"type\":1,\"url\":\"\"},{\"id\":\"10002\",\"icon\":\"md md-mode-edit\",\"title\":\"修改\",\"showWin\":1,\"winSize\":\"50\",\"type\":0,\"url\":\"\"},{\"id\":\"10003\",\"icon\":\"ion-trash-a\",\"title\":\"删除\",\"showWin\":0,\"winSize\":\"\",\"type\":0,\"url\":\"\"},{\"id\":\"10004\",\"icon\":\"ion-eye\",\"title\":\"查看\",\"showWin\":1,\"winSize\":50,\"type\":0,\"url\":\"\"}]', '{\"pIdKey\":\"parent\",\"nodeOpts\":\"ALL\",\"name\":\"text\",\"width\":2,\"sqlId\":\"20161024101134\",\"rootPId\":\"\",\"relationField\":\"id\",\"url\":\"\",\"isShow\":1,\"idKey\":\"id\"}', '20161024101134', '系统数据字典');
INSERT INTO `tb_sm_view` VALUES ('7105f0cf9a7f4d4dbd9c7316436d0952', '3', null, null, 'admin', '2017-01-10 15:38:03', '201701101023479230', '代码模板视图', '0', '0', '[{\"field\":\"name\",\"dataType\":\"VARCHAR\",\"title\":\"name\",\"idx\":0,\"expr\":\"=\",\"componentType\":\"TEXT\"}]', '[{\"id\":\"10004\",\"icon\":\"ion-eye\",\"title\":\"查看\",\"idx\":0,\"showWin\":1,\"winSize\":50,\"type\":0,\"url\":\"\"},{\"id\":\"10001\",\"icon\":\"md-add\",\"title\":\"增加\",\"idx\":1,\"showWin\":1,\"winSize\":50,\"type\":1,\"url\":\"\"},{\"id\":\"10002\",\"icon\":\"ion-edit\",\"title\":\"修改\",\"idx\":2,\"showWin\":1,\"winSize\":50,\"type\":0,\"url\":\"\"},{\"id\":\"10003\",\"icon\":\"ion-trash-a\",\"title\":\"删除\",\"idx\":3,\"showWin\":0,\"winSize\":\"\",\"type\":0,\"url\":\"\"}]', '{\"pIdKey\":\"\",\"nodeOpts\":\"ALL\",\"name\":\"\",\"width\":2,\"sqlId\":\"\",\"relationField\":\"\",\"url\":\"\",\"idKey\":\"\",\"isShow\":0}', '201701101022146336', '自动生成本项目代码结构');
INSERT INTO `tb_sm_view` VALUES ('24e4199836ba49d79b78e57bb0720326', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '201701110611558331', 'SQL定义视图', '0', '0', '[{\"title\":\"sqlName\",\"field\":\"sqlName\",\"dataType\":\"VARCHAR\",\"componentType\":\"TEXT\",\"expr\":\"=\",\"idx\":0},{\"title\":\"sqlId\",\"field\":\"sqlId\",\"dataType\":\"VARCHAR\",\"componentType\":\"TEXT\",\"expr\":\"=\",\"idx\":1}]', '[{\"id\":\"10001\",\"title\":\"增加\",\"icon\":\"md-add\",\"type\":1,\"url\":\"\",\"showWin\":1,\"winSize\":50,\"idx\":1},{\"id\":\"10002\",\"title\":\"修改\",\"icon\":\"ion-edit\",\"type\":0,\"url\":\"\",\"showWin\":1,\"winSize\":50,\"idx\":2},{\"id\":\"10003\",\"title\":\"删除\",\"icon\":\"ion-trash-a\",\"type\":0,\"url\":\"\",\"showWin\":0,\"winSize\":\"\",\"idx\":3},{\"id\":\"10004\",\"title\":\"查看\",\"icon\":\"ion-eye\",\"type\":0,\"url\":\"\",\"showWin\":1,\"winSize\":50,\"idx\":0}]', '{\"pIdKey\":\"\",\"nodeOpts\":\"ALL\",\"width\":2,\"name\":\"\",\"sqlId\":\"\",\"relationField\":\"\",\"url\":\"\",\"isShow\":0,\"idKey\":\"\"}', '20161116041618', '定义所有查询语句');

-- ----------------------------
-- Table structure for tb_sm_view_field
-- ----------------------------
DROP TABLE IF EXISTS `tb_sm_view_field`;
CREATE TABLE `tb_sm_view_field` (
  `id` varchar(50) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `create_User` varchar(50) DEFAULT NULL,
  `create_Time` datetime DEFAULT NULL,
  `last_Update_User` varchar(50) DEFAULT NULL,
  `last_Update_Time` datetime DEFAULT NULL,
  `viewId` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL COMMENT '名称',
  `field` varchar(200) DEFAULT NULL COMMENT '字段名',
  `isdisplay` int(10) DEFAULT NULL COMMENT '是否显示',
  `options` varchar(1000) DEFAULT NULL COMMENT '扩展选项',
  `dataType` varchar(255) DEFAULT NULL COMMENT '数据类型',
  `length` varchar(5) DEFAULT NULL COMMENT '数据长度',
  `sort` varchar(12) DEFAULT NULL COMMENT '是否排序',
  `modiftyType` int(2) DEFAULT NULL COMMENT '是否修改',
  `isView` int(2) DEFAULT NULL COMMENT '是否查看',
  `idx` int(4) DEFAULT NULL COMMENT '排序',
  `componentType` varchar(255) DEFAULT NULL COMMENT '组件类型',
  `rule` varchar(255) DEFAULT NULL,
  `isInsert` int(2) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_sm_view_field
-- ----------------------------
INSERT INTO `tb_sm_view_field` VALUES ('8b248db6346e43368090669113c67755', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'id', 'id', '1', null, 'VARCHAR', '50', null, '1', '1', '0', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('8b8cc5f66f5743bb81c0fbdba88a5314', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'value', 'value', '1', null, 'VARCHAR', '50', null, '1', '1', '1', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('97aa9dd48c5c4810a414fcf7d601a49d', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'text', 'text', '1', null, 'VARCHAR', '50', null, '1', '1', '2', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('8b041eebfdd443d090f7ccf54b1a4093', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'remark', 'remark', '1', null, 'VARCHAR', '500', null, '1', '1', '3', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('50ce338d1e22454fac7cd8443deabdfe', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'parent', 'parent', '1', null, 'VARCHAR', '50', null, '1', '1', '4', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('098f92a202874714ae6baf3f0aab9ce0', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'idx', 'idx', '1', null, 'DECIMAL', '5', null, '1', '1', '5', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('8e4154d7dd57464980a0ca789afb72fd', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', '版本号', 'version', '1', null, 'DECIMAL', '10', null, '1', '1', '6', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('1b035331cc6044f8a6ba237f876277d6', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', '创建者', 'createuser', '1', null, 'VARCHAR', '50', null, '1', '1', '7', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('fe0d35f7698542e48533f19fc8684a74', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', '创建时间', 'createTime', '1', 'Y-m-d H:i:s', 'DATETIME', '19', null, '1', '1', '8', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('1306592356a544a1bb17c93a987a9429', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', '更新者', 'lastUpdateUser', '1', null, 'VARCHAR', '50', null, '1', '1', '9', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('c4874f04921a4c92b24f5d5cb85dd676', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', '更新时间', 'lastUpdateTime', '1', 'Y-m-d H:i:s', 'DATETIME', '19', null, '1', '1', '10', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('9d518f6865d84165aacad4e253276071', '0', 'admin', '2016-12-23 15:04:57', 'admin', '2016-12-23 15:04:57', '2fb6782397154ed8b7824dea86e677e3', 'parentText', 'parentText', '1', null, 'VARCHAR', '50', null, '0', '1', '11', 'TEXT', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('dc568db9371f4b40a84d88f1bf767e88', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'id', 'id', '1', null, 'VARCHAR', '50', null, '1', '1', '0', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('ab6d3c1dd7df451d901281b6c2dd9480', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'name', 'name', '1', null, 'VARCHAR', '50', null, '1', '1', '1', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('69676b8f9a6342ab8dbb4741d31c3540', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'template', 'template', '0', null, 'VARCHAR', '21845', null, '1', '1', '2', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('5a35bf4833224a5a93680ebb9306e9d1', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'remark', 'remark', '1', null, 'VARCHAR', '500', null, '1', '1', '3', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('35709ac3157944edbb2290733678e175', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'filepath', 'filepath', '1', null, 'VARCHAR', '255', null, '1', '1', '4', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('322d61973e8540e58dfebc881d43a106', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', '版本号', 'version', '1', null, 'DECIMAL', '10', null, '1', '1', '5', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('824300d50321428aa20e038a58d7fe5a', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'create_user', 'create_user', '1', null, 'VARCHAR', '50', null, '1', '1', '6', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('2f93cbf247bf428285fec66ffa3d51a5', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'create_time', 'create_time', '1', '%Y-%m-%d %H:%i:%s', 'DATETIME', '19', null, '1', '1', '7', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('0b0e378e4d764bf18a5dd5839f5d00e2', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'last_update_user', 'last_update_user', '1', null, 'VARCHAR', '50', null, '1', '1', '8', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('539918b3f0b74a768716c6aaed0359b5', '0', 'admin', '2017-01-10 15:38:03', 'admin', '2017-01-10 15:38:03', '7105f0cf9a7f4d4dbd9c7316436d0952', 'last_update_time', 'last_update_time', '1', '%Y-%m-%d %H:%i:%s', 'DATETIME', '19', null, '1', '1', '9', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('de5dabfd7b4c444ea8ff9f19dedf2900', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'id', 'id', '1', null, 'VARCHAR', '50', null, '1', '1', '0', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('72412f58c006451d82c2671a8fd596f1', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'sqlId', 'sqlId', '1', null, 'VARCHAR', '50', null, '1', '1', '1', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('e224f04605de415abda279f0aa4be8ee', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'sqlName', 'sqlName', '1', null, 'VARCHAR', '50', null, '1', '1', '2', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('a476ec03235e44fb85ec0893b103dae1', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'groupId', 'groupId', '1', null, 'VARCHAR', '50', null, '1', '1', '3', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('81dcd3ce992c4c539c90f706d9752f71', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'selectSql', 'selectSql', '1', null, 'VARCHAR', '21845', null, '1', '1', '4', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('0467cc31bbbe4ea9b19b8f3e3dad7e3b', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'datasource', 'datasource', '1', null, 'VARCHAR', '50', null, '1', '1', '5', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('b78b299bdeee4b39be4e6878576d80dd', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'sqldesc', 'sqldesc', '1', null, 'VARCHAR', '21845', null, '1', '1', '6', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('542a24bcc5b1428fa6fceddf8c63caea', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'create_time', 'create_time', '1', 'Y-m-d H:i:s', 'DATETIME', '19', null, '1', '1', '7', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('635b380d04dd41adaa0caeb86ae0a31a', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'create_user', 'create_user', '1', null, 'VARCHAR', '50', null, '1', '1', '8', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('1ce41f2d69934f80b338768633ed8b58', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', '版本号', 'version', '1', null, 'DECIMAL', '50', null, '1', '1', '9', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('496b8c08207a49df925a37e69f9f3153', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'last_Update_User', 'last_Update_User', '1', null, 'VARCHAR', '50', null, '1', '1', '10', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('1c0fb5191e7f42109b8bded77ba2e90c', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'last_Update_Time', 'last_Update_Time', '1', 'Y-m-d H:i:s', 'DATETIME', '19', null, '1', '1', '11', 'DATEPICKER', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('f4645bc5a39444c48c42e13023aa90e0', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'status', 'status', '1', null, 'DECIMAL', '2', null, '1', '1', '12', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('521200ca5b6b4d97a8e638e289baed32', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'cache', 'cache', '1', null, 'DECIMAL', '2', null, '1', '1', '13', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('b40d85a7a7e04cbcb50f9d09e8e3af13', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'masterTableId', 'masterTableId', '1', null, 'VARCHAR', '50', null, '1', '1', '14', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('be00a311b1174b76988a7ce5588ef7a7', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'masterTable', 'masterTable', '1', null, 'VARCHAR', '50', null, '1', '1', '15', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('1e2deb8dfe394269bcb3af988acab358', '0', 'admin', '2017-01-11 18:12:29', 'admin', '2017-01-11 18:12:29', '24e4199836ba49d79b78e57bb0720326', 'sqlExpand', 'sqlExpand', '1', null, 'VARCHAR', '21845', null, '1', '1', '16', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('00a88937aaa04a7ab66f6962ecd23fbd', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'id', 'id', '1', null, 'VARCHAR', '50', null, '0', '1', '0', 'TEXT', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('a8b16940680d42e0ad34dc6ffff6dec3', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'value', 'value', '1', null, 'VARCHAR', '50', null, '1', '1', '1', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('76b8e554d5894ba3bdf695d6639dabfc', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'text', 'text', '1', null, 'VARCHAR', '50', null, '1', '1', '2', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('723ea8e6868a4ca6b7dab70de0ddd172', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'remark', 'remark', '1', null, 'VARCHAR', '500', null, '1', '1', '3', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('d42ee3de78224e729688ff5077799e39', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'parent', 'parent', '1', null, 'VARCHAR', '50', null, '1', '1', '4', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('978a140b56194b2e9338cb59ccbfe221', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'idx', 'idx', '1', null, 'DECIMAL', '5', null, '1', '1', '5', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('693d9ad91b28473c864c43ba13aaa85c', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', '版本号', 'version', '1', null, 'DECIMAL', '10', null, '1', '1', '6', 'TEXT', '[]', '1');
INSERT INTO `tb_sm_view_field` VALUES ('b915d9df300e47afa0b2420544fe3541', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'create_user', 'create_user', '1', null, 'VARCHAR', '50', null, '0', '1', '7', 'TEXT', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('8402f88a273b42d099a86177f211fcd5', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'create_time', 'create_time', '1', '%Y-%m-%d %H:%i:%s', 'DATETIME', '19', null, '0', '1', '8', 'DATEPICKER', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('17714c48d9ef4ade855ff5c4b5b73433', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'last_update_user', 'last_update_user', '1', null, 'VARCHAR', '50', null, '0', '1', '9', 'TEXT', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('89c7096aa7c042babc975946591dc627', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'last_update_time', 'last_update_time', '1', '%Y-%m-%d %H:%i:%s', 'DATETIME', '19', null, '0', '1', '10', 'DATEPICKER', '[]', '0');
INSERT INTO `tb_sm_view_field` VALUES ('9dbe31bddbe54d9d9adbd5bd0a406df9', '0', 'admin', '2017-01-11 18:24:35', 'admin', '2017-01-11 18:24:35', '3fae66ee0b714c778dbeb6856a35d9cb', 'parentText', 'parentText', '1', null, 'VARCHAR', '50', null, '0', '1', '11', 'TEXT', '[]', '0');
SET FOREIGN_KEY_CHECKS=1;
