SET DATABASE SQL SYNTAX MYS TRUE;

drop index if exists i18n_resource_locale_i18n_group_id_fk;
drop table if exists i18n_resource_group;
create table i18n_resource_group
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) primary key ,
    groupName varchar(100) not null,
    label varchar(100) default '' null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    createUserId varchar(30) not null,
    updateTime datetime,
    updateUserId varchar(30) not null,
    valid bit default b'1' not null,
    deleted bit default b'0' not null,
    constraint i18n_group unique (groupName, createUserId)
);

drop table if exists i18n_resource_locale;
create table i18n_resource_locale
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    groupId int null,
    groupName varchar(100) not null,
    locale varchar(10) not null,
    baiduLocale varchar(10) not null,
    language varchar(100) null,
    deleted bit default b'0' not null,
    createTime timestamp default CURRENT_TIMESTAMP not null,
    createUserId varchar(30) not null,
    updateTime datetime,
    updateUserId varchar(30) not null,
    constraint i18n_resource_locale_i18n_group_id_fk
        foreign key (groupId) references i18n_resource_group (id)
);

create index i18n_resource_locale_i18n_group_id_fk on i18n_resource_locale (groupId);


-- i18n_data_i18n_i18n  (表前缀_模块名_资源名)
-- drop table if exists i18n_data_i18n_i18n;
CREATE TABLE i18n_data_i18n_i18n
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    label VARCHAR(100) NOT NULL,
    defaults VARCHAR(1000) DEFAULT '' NOT NULL,
    memo VARCHAR(1000) DEFAULT '' NOT NULL,
    createUserId varchar(30) null,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updateUserId varchar(30) null,
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    valid bit DEFAULT b'1' NOT NULL,
    locale_zh_CN VARCHAR(1000) DEFAULT '' NOT NULL,
    locale_en_US VARCHAR(1000) DEFAULT '' NOT NULL,
    locale_ja_JP VARCHAR(1000) DEFAULT '' NOT NULL,
    CONSTRAINT uniq_i18n_data_i18n_i18n_label UNIQUE (label)
);

CREATE TABLE i18n_data_i18n_test
(
    id INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY,
    label VARCHAR(100) NOT NULL,
    defaults VARCHAR(1000) DEFAULT '' NOT NULL,
    memo VARCHAR(1000) DEFAULT '' NOT NULL,
    createUserId varchar(30) null,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updateUserId varchar(30) null,
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    valid bit DEFAULT b'1' NOT NULL,
    locale_zh_CN VARCHAR(1000) DEFAULT '' NOT NULL,
    CONSTRAINT uniq_i18n_data_i18n_test_label UNIQUE (label)
);

insert into i18n_resource_group (id, groupName, label, createUserId, createTime, updateUserId, updateTime, valid, deleted) values (1, 'i18n',  '多语言', 'i18n', '2019-05-22 00:10:05', 'i18n', '2020-02-19 17:53:10', 1, 0);
insert into i18n_resource_group (id, groupName, label, createUserId, createTime, updateUserId, updateTime, valid, deleted) values (2, 'test',  'test', 'i18n', '2019-07-11 06:29:42', 'i18n', '2020-02-19 17:53:10', 1, 0);

insert into i18n_resource_locale (groupId, locale, language, baiduLocale, groupName, createUserId, createTime, updateUserId, updateTime, deleted) values (1, 'zh_CN', '简体中文', 'zh', 'i18n', 'i18n', '2019-05-22 14:11:55', 'i18n', '2020-02-20 00:45:47', 0);
insert into i18n_resource_locale (groupId, locale, language, baiduLocale, groupName, createUserId, createTime, updateUserId, updateTime, deleted) values (1, 'en_US', 'English(US)', 'en', 'i18n', 'i18n', '2019-05-22 14:11:56', 'i18n', '2020-02-20 00:45:48', 0);
insert into i18n_resource_locale (groupId, locale, language, baiduLocale, groupName, createUserId, createTime, updateUserId, updateTime, deleted) values (1, 'ja_JP', '日本語', 'jp', 'i18n', 'i18n', '2019-05-22 14:11:57', 'i18n', '2020-02-20 00:45:49', 0);
insert into i18n_resource_locale (groupId, locale, language, baiduLocale, groupName, createUserId, createTime, updateUserId, updateTime, deleted) values (2, 'zh_CN', '简体中文', 'zh', 'test', 'i18n', '2019-05-22 14:11:55', 'i18n', '2020-02-20 00:45:47', 0);

insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values (' ', '', '不间断空格0xA0', true, '', ' ', '');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values (' ', '', '普通空格0x20', true, '', ' ', '');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('保存', '保存', '', true, '保存', 'Save', '保存');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('添加', '添加', '', true, '添加', 'Add', '追加');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('编辑', '编辑', '', true, '编辑', 'Edit', '編集');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('取消', '取消', '', true, '取消', 'Cancel', 'キャンセル');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('标签', '标签', '', true, '标签', 'Label', 'ラベル');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('locale_ko', '朝鲜语', '', true, '韩语', 'Korean', '朝鮮語');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('locale_zh_cn', '中文', '', true, '中文', 'Chinese', '中国語');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('label', '标签', '', true, '标签', 'Label', 'ラベル');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('语种', '语种', '', true, '语种', 'Locale', '言語');
insert into i18n_data_i18n_i18n (label, defaults, memo, valid, locale_zh_CN, locale_en_US, locale_ja_JP) values ('管理', '管理', '', true, '管理', '', '管理');
