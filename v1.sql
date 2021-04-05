/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2021/4/5 19:55:47                            */
/*==============================================================*/


drop table if exists sys_course;

drop table if exists sys_dept;

drop table if exists sys_dict;

drop table if exists sys_log;

drop table if exists sys_menu;

drop table if exists sys_role;

drop table if exists sys_role_dept;

drop table if exists sys_role_menu;

drop table if exists sys_user;

drop table if exists sys_user_role_course;

drop table if exists sys_user_token;

/*==============================================================*/
/* Table: sys_course                                            */
/*==============================================================*/
create table sys_course
(
   id                   bigint(20) not null comment '编号',
   teacher_id           bigint(20),
   message              varchar(200),
   homework             varchar(200),
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   id                   bigint(20) not null auto_increment comment '编号',
   name                 varchar(50) default NULL comment '机构名称',
   parent_id            bigint(20) default NULL comment '上级机构ID，一级机构为0',
   order_num            int(11) default NULL comment '排序',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   del_flag             tinyint(4) default 0 comment '是否删除  -1：已删除  0：正常',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='机构管理';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   bigint(20) not null auto_increment comment '编号',
   value                varchar(100) not null comment '数据值',
   "label"              varchar(100) not null comment '标签名',
   type                 varchar(100) not null comment '类型',
   description          varchar(100) not null comment '描述',
   sort                 decimal(10,0) not null comment '排序（升序）',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   remarks              varchar(255) default NULL comment '备注信息',
   del_flag             tinyint(4) default 0 comment '是否删除  -1：已删除  0：正常',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='字典表';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   bigint(20) not null auto_increment comment '编号',
   user_name            varchar(50) default NULL comment '用户名',
   operation            varchar(50) default NULL comment '用户操作',
   method               varchar(200) default NULL comment '请求方法',
   params               varchar(5000) default NULL comment '请求参数',
   time                 bigint(20) not null comment '执行时长(毫秒)',
   ip                   varchar(64) default NULL comment 'IP地址',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=1754 DEFAULT CHARSET=utf8 COMMENT='系统日志';

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   bigint(20) not null auto_increment comment '编号',
   name                 varchar(50) default NULL comment '菜单名称',
   parent_id            bigint(20) default NULL comment '父菜单ID，一级菜单为0',
   url                  varchar(200) default NULL comment '菜单URL,类型：1.普通页面（如用户管理， /sys/user） 2.嵌套完整外部页面，以http(s)开头的链接 3.嵌套服务器页面，使用iframe:前缀+目标URL(如SQL监控， iframe:/druid/login.html, iframe:前缀会替换成服务器地址)',
   perms                varchar(500) default NULL comment '授权(多个用逗号分隔，如：sys:user:add,sys:user:edit)',
   type                 int(11) default NULL comment '类型   0：目录   1：菜单   2：按钮',
   icon                 varchar(50) default NULL comment '菜单图标',
   order_num            int(11) default NULL comment '排序',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   del_flag             tinyint(4) default 0 comment '是否删除  -1：已删除  0：正常',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint(20) not null auto_increment comment '编号',
   name                 varchar(100) default NULL comment '角色名称',
   remark               varchar(100) default NULL comment '备注',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   del_flag             tinyint(4) default 0 comment '是否删除  -1：已删除  0：正常',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='角色管理';

/*==============================================================*/
/* Table: sys_role_dept                                         */
/*==============================================================*/
create table sys_role_dept
(
   id                   bigint(20) not null comment '编号',
   role_id              bigint(20) default NULL comment '角色ID',
   dept_id              bigint(20) default NULL comment '机构ID',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色机构';

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   bigint(20) not null comment '编号',
   role_id              bigint(20) default NULL comment '角色ID',
   menu_id              bigint(20) default NULL comment '菜单ID',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=utf8 COMMENT='角色菜单';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint(20) not null auto_increment comment '编号',
   name                 varchar(50) not null comment '用户名',
   password             varchar(100) default NULL comment '密码',
   salt                 varchar(40) default NULL comment '盐',
   sex                  varchar(8),
   email                varchar(100) default NULL comment '邮箱',
   mobile               varchar(100) default NULL comment '手机号',
   status               tinyint(4) default NULL comment '状态  0：禁用   1：正常',
   dept_id              bigint(20) default NULL comment '机构ID',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   del_flag             tinyint(4) default 0 comment '是否删除  -1：已删除  0：正常',
   primary key (id),
   unique key name (name)
)
ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='用户管理';

/*==============================================================*/
/* Table: sys_user_role_course                                  */
/*==============================================================*/
create table sys_user_role_course
(
   id                   bigint(20) not null comment '编号',
   user_id              bigint(20) default NULL comment '用户ID',
   role_id              bigint(20) default NULL comment '角色ID',
   course_id            bigint(20),
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='用户角色';

/*==============================================================*/
/* Table: sys_user_token                                        */
/*==============================================================*/
create table sys_user_token
(
   id                   bigint(20) not null comment '编号',
   user_id              bigint(20) not null,
   token                varchar(100) not null comment 'token',
   expire_time          datetime default NULL comment '过期时间',
   create_by            varchar(50) default NULL comment '创建人',
   create_time          datetime default NULL comment '创建时间',
   last_update_by       varchar(50) default NULL comment '更新人',
   last_update_time     datetime default NULL comment '更新时间',
   primary key (id),
   unique key token (token)
)
ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户Token';

alter table sys_course add constraint FK_Reference_10 foreign key (teacher_id)
      references sys_user_role_course (id) on delete restrict on update restrict;

alter table sys_dept add constraint FK_Reference_5 foreign key (parent_id)
      references sys_dept (id) on delete restrict on update restrict;

alter table sys_menu add constraint FK_Reference_6 foreign key (parent_id)
      references sys_menu (id) on delete restrict on update restrict;

alter table sys_role_dept add constraint FK_Reference_3 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_role_dept add constraint FK_Reference_4 foreign key (dept_id)
      references sys_dept (id) on delete restrict on update restrict;

alter table sys_role_menu add constraint FK_Reference_8 foreign key (menu_id)
      references sys_menu (id) on delete restrict on update restrict;

alter table sys_role_menu add constraint FK_Reference_9 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_user_role_course add constraint FK_Reference_1 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

alter table sys_user_role_course add constraint FK_Reference_11 foreign key (course_id)
      references sys_course (id) on delete restrict on update restrict;

alter table sys_user_role_course add constraint FK_Reference_2 foreign key (role_id)
      references sys_role (id) on delete restrict on update restrict;

alter table sys_user_token add constraint FK_Reference_7 foreign key (user_id)
      references sys_user (id) on delete restrict on update restrict;

