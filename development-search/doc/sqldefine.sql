create table TB_SM_SQLDEFINE
(
  oid                     VARCHAR(50) not null,
  pid                     VARCHAR(50),
  mid                     VARCHAR(50),
  obj_version             int(10),
  sqlid                   VARCHAR(100),
  sqlname                 VARCHAR(1000),
  sqldesc                 VARCHAR(4000),
  def_select_sql          text,
  def_select_params       text,
  def_select_metadata     text,
  def_insert_sql          text,
  def_insert_params       text,
  def_update_sql          text,
  def_update_params       text,
  def_delete_sql          text,
  def_delete_params       text,
  select_sql              text,
  update_sql              text,
  insert_sql              text,
  delete_sql              text,
  update_date             text,
  tablename               VARCHAR(100),
  sql_options             int(20),
  keyfield                VARCHAR(100),
  def_singleselect_sql    text,
  singleselect_sql        text,
  def_singleselect_params text,
  datasource              VARCHAR(100)
);

create table TB_SM_SQLGROUP
(
  id       VARCHAR(50),
  code       VARCHAR(50),
  name      VARCHAR(50),
  parentId VARCHAR(50),
  path VARCHAR(50),
  remark VARCHAR(500),
  version NUMERIC,
  createUser VARCHAR(50),
  createTime datetime,
  lastUpdateUser VARCHAR(50),
  lastUpdateTime datetime
);
create table TB_SM_USER
(
  id          VARCHAR(50),
  username    VARCHAR(100),
  password    VARCHAR(100),
  obj_version int(10)
);

CREATE TABLE `tb_sm_sqldefine` (
  `id` varchar(50) NOT NULL,
  `sql_Id` varchar(50) DEFAULT NULL,
  `sql_Name` varchar(50) DEFAULT NULL,
  `group_id` varchar(50) DEFAULT NULL,
  `select_Sql` text,
  `datasource` varchar(50) DEFAULT NULL,
  `sqldesc` text,
  `create_Time` datetime DEFAULT NULL,
  `create_User` varchar(50) DEFAULT NULL,
  `version` decimal(50,0) DEFAULT NULL,
  `last_Update_User` varchar(50) DEFAULT NULL,
  `last_Update_Time` datetime DEFAULT NULL,
  `status` decimal(2,0) DEFAULT NULL,
  `cache` decimal(2,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

