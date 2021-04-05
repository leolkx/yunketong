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
   id                   bigint(20) not null comment '���',
   teacher_id           bigint(20),
   message              varchar(200),
   homework             varchar(200),
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=utf8 COMMENT='��ɫ�˵�';

/*==============================================================*/
/* Table: sys_dept                                              */
/*==============================================================*/
create table sys_dept
(
   id                   bigint(20) not null auto_increment comment '���',
   name                 varchar(50) default NULL comment '��������',
   parent_id            bigint(20) default NULL comment '�ϼ�����ID��һ������Ϊ0',
   order_num            int(11) default NULL comment '����',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   del_flag             tinyint(4) default 0 comment '�Ƿ�ɾ��  -1����ɾ��  0������',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='��������';

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   bigint(20) not null auto_increment comment '���',
   value                varchar(100) not null comment '����ֵ',
   "label"              varchar(100) not null comment '��ǩ��',
   type                 varchar(100) not null comment '����',
   description          varchar(100) not null comment '����',
   sort                 decimal(10,0) not null comment '��������',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   remarks              varchar(255) default NULL comment '��ע��Ϣ',
   del_flag             tinyint(4) default 0 comment '�Ƿ�ɾ��  -1����ɾ��  0������',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='�ֵ��';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   bigint(20) not null auto_increment comment '���',
   user_name            varchar(50) default NULL comment '�û���',
   operation            varchar(50) default NULL comment '�û�����',
   method               varchar(200) default NULL comment '���󷽷�',
   params               varchar(5000) default NULL comment '�������',
   time                 bigint(20) not null comment 'ִ��ʱ��(����)',
   ip                   varchar(64) default NULL comment 'IP��ַ',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=1754 DEFAULT CHARSET=utf8 COMMENT='ϵͳ��־';

/*==============================================================*/
/* Table: sys_menu                                              */
/*==============================================================*/
create table sys_menu
(
   id                   bigint(20) not null auto_increment comment '���',
   name                 varchar(50) default NULL comment '�˵�����',
   parent_id            bigint(20) default NULL comment '���˵�ID��һ���˵�Ϊ0',
   url                  varchar(200) default NULL comment '�˵�URL,���ͣ�1.��ͨҳ�棨���û����� /sys/user�� 2.Ƕ�������ⲿҳ�棬��http(s)��ͷ������ 3.Ƕ�׷�����ҳ�棬ʹ��iframe:ǰ׺+Ŀ��URL(��SQL��أ� iframe:/druid/login.html, iframe:ǰ׺���滻�ɷ�������ַ)',
   perms                varchar(500) default NULL comment '��Ȩ(����ö��ŷָ����磺sys:user:add,sys:user:edit)',
   type                 int(11) default NULL comment '����   0��Ŀ¼   1���˵�   2����ť',
   icon                 varchar(50) default NULL comment '�˵�ͼ��',
   order_num            int(11) default NULL comment '����',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   del_flag             tinyint(4) default 0 comment '�Ƿ�ɾ��  -1����ɾ��  0������',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='�˵�����';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   bigint(20) not null auto_increment comment '���',
   name                 varchar(100) default NULL comment '��ɫ����',
   remark               varchar(100) default NULL comment '��ע',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   del_flag             tinyint(4) default 0 comment '�Ƿ�ɾ��  -1����ɾ��  0������',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='��ɫ����';

/*==============================================================*/
/* Table: sys_role_dept                                         */
/*==============================================================*/
create table sys_role_dept
(
   id                   bigint(20) not null comment '���',
   role_id              bigint(20) default NULL comment '��ɫID',
   dept_id              bigint(20) default NULL comment '����ID',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ɫ����';

/*==============================================================*/
/* Table: sys_role_menu                                         */
/*==============================================================*/
create table sys_role_menu
(
   id                   bigint(20) not null comment '���',
   role_id              bigint(20) default NULL comment '��ɫID',
   menu_id              bigint(20) default NULL comment '�˵�ID',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=utf8 COMMENT='��ɫ�˵�';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   bigint(20) not null auto_increment comment '���',
   name                 varchar(50) not null comment '�û���',
   password             varchar(100) default NULL comment '����',
   salt                 varchar(40) default NULL comment '��',
   sex                  varchar(8),
   email                varchar(100) default NULL comment '����',
   mobile               varchar(100) default NULL comment '�ֻ���',
   status               tinyint(4) default NULL comment '״̬  0������   1������',
   dept_id              bigint(20) default NULL comment '����ID',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   del_flag             tinyint(4) default 0 comment '�Ƿ�ɾ��  -1����ɾ��  0������',
   primary key (id),
   unique key name (name)
)
ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='�û�����';

/*==============================================================*/
/* Table: sys_user_role_course                                  */
/*==============================================================*/
create table sys_user_role_course
(
   id                   bigint(20) not null comment '���',
   user_id              bigint(20) default NULL comment '�û�ID',
   role_id              bigint(20) default NULL comment '��ɫID',
   course_id            bigint(20),
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id)
)
ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='�û���ɫ';

/*==============================================================*/
/* Table: sys_user_token                                        */
/*==============================================================*/
create table sys_user_token
(
   id                   bigint(20) not null comment '���',
   user_id              bigint(20) not null,
   token                varchar(100) not null comment 'token',
   expire_time          datetime default NULL comment '����ʱ��',
   create_by            varchar(50) default NULL comment '������',
   create_time          datetime default NULL comment '����ʱ��',
   last_update_by       varchar(50) default NULL comment '������',
   last_update_time     datetime default NULL comment '����ʱ��',
   primary key (id),
   unique key token (token)
)
ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='�û�Token';

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

