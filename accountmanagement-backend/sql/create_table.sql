#
#
-- auto-generated definition
create table account
(
    id           int               not null comment 'ID编号'
        primary key,
    username     varchar(20)       null comment '账号',
    userPassword varchar(100)      null comment '密码',
    isAdmin      tinyint default 0 null comment '是否为管理员 0:用户 1:管理员',
    status       tinyint default 0 null comment '用户状态 0:正常  1:禁用',
    isDelete     tinyint default 0 null comment '是否删除 0:没有  1:删除'
);

create table managet(
                        id int not null primary key COMMENT 'ID主键',
                        `name` varchar(500) not null COMMENT '平台名称',
                        `account` varchar(200) not null COMMENT '平台账号',
                        `password` varchar(200) not null comment '平台密码',
                        uid int not null COMMENT '关联ID',
                        isDelete TINYINT not null default 0 COMMENT '逻辑删除 1:删除 0:没有  默认0'
)

create table if not exists accountmanger.task
(
    id       int auto_increment comment 'ID主键'
        primary key,
    taskName varchar(100)      not null comment '任务名称',
    status   tinyint default 0 not null comment '状态 0 未完成 1 完成',
    isDelete tinyint default 0 not null comment '是否删除 0 未删除 1 删除',
    uid      int               not null comment '用户ID'
);

