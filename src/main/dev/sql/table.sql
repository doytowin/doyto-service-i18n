USE i18n;

# i18n_group_i18n  group
CREATE TABLE i18n.i18n_group_i18n
(
    id INT(11) NOT NULL AUTO_INCREMENT,
    label VARCHAR(100) NOT NULL,
    value TEXT,
    memo VARCHAR(200) NOT NULL DEFAULT '',
    createTime TIMESTAMP NOT NULL DEFAULT current_timestamp,
    updateTime TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
    valid BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    CONSTRAINT UNIQUE INDEX i18n_group_i18n_label_index (label)
);
