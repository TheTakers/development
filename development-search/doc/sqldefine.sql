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

create table TB_SM_SQLDEFINE
(
  id                     VARCHAR(50) not null,
  sqlid                   VARCHAR(100),
  sqlname                 VARCHAR(1000),
  select_sql              text,
  datasource              VARCHAR(100),
  sqldesc                 VARCHAR(4000),
  cache NUMERIC,
	STATUS NUMERIC,
  groupid VARCHAR(50),
  version NUMERIC,
  createUser VARCHAR(50),
  createTime datetime,
  lastUpdateUser VARCHAR(50),
  lastUpdateTime datetime
);