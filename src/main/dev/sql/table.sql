USE i18n;

CREATE TABLE i18n.i18n_resource_group
(
    id         INT(11)      NOT NULL AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    createTime TIMESTAMP    NOT NULL DEFAULT current_timestamp,
    updateTime TIMESTAMP    NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    valid      BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT UNIQUE INDEX i18n_group (name)
);

CREATE TABLE i18n.i18n_resource_locale
(
    id INT(11) NOT NULL AUTO_INCREMENT,
    group_id INT(11) NOT NULL,
    locale VARCHAR(15),

    createTime TIMESTAMP NOT NULL DEFAULT current_timestamp,
    updateTime TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    statu TINYINT(1) NOT NULL DEFAULT 0,
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