-- auto-generated definition
create table t_temp
(
  id          bigint(20) auto_increment    primary key,
  is_del      bit default b'0' not null comment '是否删除',
  version     int default 0    not null comment '版本',
  create_date datetime         null comment '创建时间',
  create_user varchar(255)     null comment '创建人',
  update_date datetime         null comment '更新时间',
  update_user varchar(255)     null comment '更新人'
)
  comment '通用模版';
