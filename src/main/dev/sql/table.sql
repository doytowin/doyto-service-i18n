USE i18n;

drop table if exists i18n_resource_group;
CREATE TABLE i18n_resource_group
(
    id           INT(11)      NOT NULL AUTO_INCREMENT,
    groupName    varchar(100) not null,
    label        varchar(100)          default '' null,
    createUserId varchar(100) not null,
    createTime   TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    updateUserId varchar(100) not null,
    updateTime   TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    valid        BOOLEAN      NOT NULL DEFAULT TRUE,
    deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id),
    CONSTRAINT UNIQUE INDEX i18n_group (groupName)
);

drop table if exists i18n_resource_locale;
CREATE TABLE i18n_resource_locale
(
    id           INT(11)      NOT NULL AUTO_INCREMENT,
    groupName    varchar(100) not null,
    locale       VARCHAR(15)  not null,
    baiduLocale  varchar(10)  not null,
    language     varchar(100) null,
    createUserId varchar(100) not null,
    createTime   TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    updateUserId varchar(100) not null,
    updateTime   TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);

-- i18n_data_i18n_i18n  (表前缀_模块名_资源名)
-- CREATE TABLE i18n.i18n_data_i18n_i18n
-- (
--     id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
--     label VARCHAR(100) NOT NULL,
--     defaults VARCHAR(1000) DEFAULT '' NOT NULL,
--     createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
--     updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
--     valid TINYINT(1) DEFAULT '1' NOT NULL,
--     locale_zh_CN VARCHAR(1000) DEFAULT '' NOT NULL,
--     locale_en_US VARCHAR(1000) DEFAULT '' NOT NULL,
--     locale_fr VARCHAR(1000) DEFAULT '' NOT NULL,
--     CONSTRAINT UNIQUE INDEX i18n_group_i18n_label_index (label)
-- );