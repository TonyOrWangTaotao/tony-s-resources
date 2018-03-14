
drop TABLE project;
drop TABLE problem;
CREATE TABLE projects(
  id    BIGINT PRIMARY KEY  AUTO_INCREMENT,
  name   VARCHAR(100),
  created_at   DATETIME,
  updated_at   DATETIME
);

create TABLE problems(
  id   BIGINT PRIMARY KEY  AUTO_INCREMENT,
  project_id  BIGINT,
  begin_time DATETIME,
  find_by   VARCHAR(20),
  description      VARCHAR(2000),
  solution  VARCHAR(2000),
  finish_by VARCHAR(20),
  finish_time DATETIME,

  created_at   DATETIME,
  updated_at   DATETIME

);

CREATE TABLE depts(
  id    BIGINT PRIMARY KEY  AUTO_INCREMENT,
  name   VARCHAR(100),
  created_at   DATETIME,
  updated_at   DATETIME
);


drop TABLE assets;
CREATE TABLE assets(
  id    BIGINT PRIMARY KEY  AUTO_INCREMENT,
  user_id int(11) NOT NULL ,
  asset_name VARCHAR(100) COMMENT '资产名称',
  model VARCHAR(100) COMMENT '型号',
  asset_number  VARCHAR(100) COMMENT '资产编号',
  created_at   DATETIME,
  updated_at   DATETIME
);

create TABLE users(
  id INT(11) PRIMARY KEY  AUTO_INCREMENT,
  dept_id INT(11) NOT NULL ,
  user_name VARCHAR(20),
  mobile  VARCHAR(11),

  created_at   DATETIME,
  updated_at   DATETIME
);

