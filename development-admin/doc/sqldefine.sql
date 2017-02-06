create table TBSMSQLDEFINE
(
  oid                     VARCHAR(50) not null,
  pid                     VARCHAR(50),
  mid                     VARCHAR(50),
  objversion             int(10),
  sqlid                   VARCHAR(100),
  sqlname                 VARCHAR(1000),
  sqldesc                 VARCHAR(4000),
  defselectsql          text,
  defselectparams       text,
  defselectmetadata     text,
  definsertsql          text,
  definsertparams       text,
  defupdatesql          text,
  defupdateparams       text,
  defdeletesql          text,
  defdeleteparams       text,
  selectsql              text,
  updatesql              text,
  insertsql              text,
  deletesql              text,
  updatedate             text,
  tablename               VARCHAR(100),
  sqloptions             int(20),
  keyfield                VARCHAR(100),
  defsingleselectsql    text,
  singleselectsql        text,
  defsingleselectparams text,
  datasource              VARCHAR(100)
);

create table TBSMSQLGROUP
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
create table TBSMUSER
(
  id          VARCHAR(50),
  username    VARCHAR(100),
  password    VARCHAR(100),
  objversion int(10)
);

CREATE TABLE `tbsmsqldefine` (
  `id` varchar(50) NOT NULL,
  `sqlId` varchar(50) DEFAULT NULL,
  `sqlName` varchar(50) DEFAULT NULL,
  `groupid` varchar(50) DEFAULT NULL,
  `selectSql` text,
  `datasource` varchar(50) DEFAULT NULL,
  `sqldesc` text,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(50) DEFAULT NULL,
  `version` decimal(50,0) DEFAULT NULL,
  `lastUpdateUser` varchar(50) DEFAULT NULL,
  `lastUpdateTime` datetime DEFAULT NULL,
  `status` decimal(2,0) DEFAULT NULL,
  `cache` decimal(2,0) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

