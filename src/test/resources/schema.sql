SET DATABASE SQL SYNTAX MYS TRUE;

drop table if exists i18n_resource_group;
create table i18n_resource_group
(
    id int PRIMARY KEY NOT NULL IDENTITY,
    name varchar(100) not null,
    owner varchar(32) not null,
    label varchar(100) default '' null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    updateTime datetime,
    valid bit default b'1' not null,
    deleted bit default b'0' not null,
    constraint i18n_group
    unique (name, owner)
);


drop table if exists i18n_resource_locale;
create table i18n_resource_locale
(
    id int PRIMARY KEY NOT NULL IDENTITY,
    groupId int not null,
    locale varchar(10) not null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    updateTime datetime,
    status tinyint default '1' not null,
    baiduTranLang varchar(10) not null,
    language varchar(100) null,
    constraint i18n_resource_locale_i18n_group_id_fk
    foreign key (groupId) references i18n_resource_group (id)
);

create index i18n_resource_locale_i18n_group_id_fk on i18n_resource_locale (groupId);

