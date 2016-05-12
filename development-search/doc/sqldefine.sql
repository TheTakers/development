create table TB_SM_DATATYPES_ORCL
(
  id        int(16),
  typevalue int(10),
  typelabel VARCHAR(50),
  orcltype  VARCHAR(50)
);
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

create table TB_SM_DATATYPES
(
  oid       VARCHAR(50),
  pid       VARCHAR(50),
  mid       VARCHAR(50),
  typevalue int(10),
  typelabel VARCHAR(50)
);
create table TB_SM_SQLGROUP
(
  oid         VARCHAR(50) not null,
  pid         VARCHAR(50),
  mid         VARCHAR(50),
  obj_version int(10),
  groupcode   VARCHAR(1000),
  groupname   VARCHAR(1000),
  groupdesc   text,
  grouppath   VARCHAR(4000)
);
create table TB_SM_USER
(
  id          VARCHAR(50),
  username    VARCHAR(100),
  password    VARCHAR(100),
  obj_version int(10)
);