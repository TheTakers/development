CREATE TABLE `tb_basic_menu` (
  `id` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pid` varchar(50) DEFAULT '0',
  `ico` varchar(50) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tb_basic_dict` (
  `id` varchar(50) DEFAULT NULL,
  `value` varchar(50) DEFAULT NULL,
  `text` varchar(50) DEFAULT '0',
  `remark` varchar(500) DEFAULT NULL,
	 parent VARCHAR(50) ,
  `idx` decimal(5,0) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT '0',
  `createuser` varchar(50) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;