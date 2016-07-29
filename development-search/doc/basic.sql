CREATE TABLE `tb_basic_menu` (
  `id` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pid` varchar(50) DEFAULT '0',
  `ico` varchar(50) DEFAULT NULL,
  `path` varchar(50) DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  `version` decimal(10,0) DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_Update_User` varchar(50) DEFAULT NULL,
  `last_Update_Time` datetime DEFAULT NULL,
  `link` varchar(255) DEFAULT NULL,
  KEY `idx_unqiue_id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

