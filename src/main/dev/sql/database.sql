CREATE DATABASE i18n default charset utf8 COLLATE utf8_general_ci;

GRANT ALL PRIVILEGES ON i18n.* TO i18n@'%' IDENTIFIED BY 'i18n_pass';

flush privileges;