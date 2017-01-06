/*
Navicat MySQL Data Transfer

Source Server         : 192.168.32.177
Source Server Version : 50547
Source Host           : localhost:3306
Source Database       : development

Target Server Type    : MYSQL
Target Server Version : 50547
File Encoding         : 65001

Date: 2017-01-06 18:50:07
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
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698e', 'domain', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.domain;\r\n\r\nimport javax.persistence.Column;\r\nimport javax.persistence.Entity;\r\nimport javax.persistence.Table;\r\n\r\n/**\r\n * ${vars.comment}实体类\r\n * @author ${vars.author}\r\n */  \r\n@Entity\r\n@Table(name=\"${vars.tableName}\")\r\npublic class ${vars.beanName} extends Auditable{\r\n	private static final long serialVersionUID = 1L;\r\n	/**\r\n	 * \r\n	 */\r\n	private static final long serialVersionUID = 1L;\r\n	\r\n	<#list vars.columnList as column>\r\n\r\n\r\n	@Column(name=\"${column.name}\")\r\n	private ${column.dtype} ${column.attr};\r\n	\r\n	</#list>\r\n\r\n	<#list vars.columnList as column>\r\n	\r\n	/**\r\n	 * ${column.name}\r\n	 */\r\n	public String get${column.methodName}() {\r\n		return ${column.attr};\r\n	}\r\n	public void set${column.methodName}(String ${column.attr}) {\r\n		this.${column.attr} = ${column.attr};\r\n	}\r\n	</#list>\r\n	\r\n}\r\n', 'domain', '/domain/${module}/${beanName}.java', '0', null, '2016-11-02 15:10:19', 'admin', '2016-11-24 02:37:08');
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698f', 'controller', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.web.system;\r\n\r\nimport org.springframework.stereotype.Controller;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.servlet.ModelAndView;\r\nimport com.sophia.api.BaseController;\r\n\r\n/**\r\n * ${vars.comment}控制器\r\n * @author ${vars.author}\r\n */\r\n@Controller\r\n@RequestMapping(\"/${vars.beanName}\")\r\npublic class ${vars.beanName}Controller extends BaseController{\r\n\r\n}\r\n', 'controller', '/web/${module}/${beanName}Controller.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698z', 'repository', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.repository;\r\n\r\nimport org.springframework.data.jpa.repository.JpaRepository;\r\nimport org.springframework.stereotype.Repository;\r\nimport com.sophia.domain.${vars.beanName};\r\n/**\r\n * ${vars.comment} Repository\r\n * @author ${vars.author}\r\n */\r\n@Repository\r\npublic interface ${vars.beanName}Repository extends JpaRepository<${vars.beanName}, String>{\r\n\r\n}\r\n', 'repository', '/repository/${module}/${beanName}Repository.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTUBKcXaR8698a', 'service', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.service;\r\n\r\nimport com.sophia.repository.JpaRepository;\r\nimport com.sophia.repository.${vars.beanName}Repository;\r\n\r\n/**\r\n * ${vars.comment}服务\r\n * @author ${vars.author}\r\n */   \r\npublic interface ${vars.beanName}Service  extends JpaRepository<${vars.beanName}Repository>{\r\n	\r\n}', 'service', '/service/${module}/${beanName}Service.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g534BVKTbBKcXaR8698a', 'serviceImpl', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.service.impl;\r\n\r\nimport org.springframework.stereotype.Service;\r\nimport com.sophia.repository.impl.JpaRepositoryImpl;\r\nimport com.sophia.repository.${vars.beanName}Repository;\r\nimport com.sophia.service.${vars.beanName}Service;\r\n\r\n/**\r\n * ${vars.comment}服务实现类\r\n * @author ${vars.author}\r\n */  \r\n@Service\r\npublic class ${vars.beanName}ServiceImpl extends JpaRepositoryImpl<${vars.beanName}Repository> implements ${vars.beanName}Service{\r\n	\r\n}\r\n', 'serviceImpl', '/service/${module}/impl/${beanName}ServiceImpl.java', '0', null, null, null, null);
INSERT INTO `tb_basic_code_template` VALUES ('0155952ad3c1g574BVKTbBKcXaR8698a', 'api', '<#assign text=\"${param}\" />\r\n<#assign vars=text?eval />\r\npackage com.sophia.api;\r\n\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n\r\n/**\r\n * ${vars.comment}API\r\n * @author ${vars.author}\r\n */\r\n@RestController\r\n@RequestMapping(value = \"/api/${vars.beanName}\")\r\npublic class ${vars.beanName}RestController extends BaseController {\r\n\r\n}\r\n', 'api', '/api/${module}/${beanName}Controller.java', '0', null, null, null, null);
SET FOREIGN_KEY_CHECKS=1;
