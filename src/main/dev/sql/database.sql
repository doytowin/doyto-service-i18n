CREATE DATABASE i18n default charset utf8 COLLATE utf8_general_ci;

CREATE USER 'i18n'@'%' IDENTIFIED BY 'i18n_pass';

GRANT ALL PRIVILEGES ON i18n.* TO i18n@'%';

flush privileges;