#
#
-- auto-generated definition
create table account
(
    id           int               not null comment 'ID���'
        primary key,
    username     varchar(20)       null comment '�˺�',
    userPassword varchar(100)      null comment '����',
    isAdmin      tinyint default 0 null comment '�Ƿ�Ϊ����Ա 0:�û� 1:����Ա',
    status       tinyint default 0 null comment '�û�״̬ 0:����  1:����',
    isDelete     tinyint default 0 null comment '�Ƿ�ɾ�� 0:û��  1:ɾ��'
);

create table managet(
                        id int not null primary key COMMENT 'ID����',
                        `name` varchar(500) not null COMMENT 'ƽ̨����',
                        `account` varchar(200) not null COMMENT 'ƽ̨�˺�',
                        `password` varchar(200) not null comment 'ƽ̨����',
                        uid int not null COMMENT '����ID',
                        isDelete TINYINT not null default 0 COMMENT '�߼�ɾ�� 1:ɾ�� 0:û��  Ĭ��0'
)

create table if not exists accountmanger.task
(
    id       int auto_increment comment 'ID����'
        primary key,
    taskName varchar(100)      not null comment '��������',
    status   tinyint default 0 not null comment '״̬ 0 δ��� 1 ���',
    isDelete tinyint default 0 not null comment '�Ƿ�ɾ�� 0 δɾ�� 1 ɾ��',
    uid      int               not null comment '�û�ID'
);

