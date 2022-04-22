create table xdu_hospital.xdu_hospital_user
(
    open_id                varchar(100) collate utf8mb4_unicode_ci not null comment 'open_id'
        primary key,
    role                   int       default 0                     null comment '用户角色1.患者2.医生3.管理员',
    city                   varchar(255)                            null comment '市',
    province               varchar(255)                            null comment '省',
    country                varchar(255)                            null comment '国',
    avatar_url             varchar(255)                            null comment '头像',
    gender                 tinyint   default 0                     null comment '性别 女:1  男:0',
    personalized_signature varchar(255) charset utf8mb4            null comment '个性签名',
    nick_name              varchar(255)                            null comment '微信昵称',
		phone                  varchar(50)                             null comment '手机号码',
    bonus                  int       default 0                     null comment '积分',
    create_time            timestamp default CURRENT_TIMESTAMP     null comment '创建时间',
    last_visit_time        timestamp default CURRENT_TIMESTAMP     null comment '最后登录时间',
    update_time            timestamp default CURRENT_TIMESTAMP     not null on update CURRENT_TIMESTAMP comment '用户更新时间'
)
    comment '微信用户信息' charset = utf8;


