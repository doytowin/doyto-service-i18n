package win.doyto.i18n.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.stereotype.Component;

import static win.doyto.i18n.module.i18n.I18nView.GROUP_FORMAT;

/**
 * MySQLDialect
 *
 * @author f0rb on 2020-02-19
 */
@ConditionalOnClass(name = "com.mysql.cj.jdbc.Driver")
@Component
public class MySQLDialect implements TranslationTableDialect {

    @Override
    public String buildTranslationTableDDL() {
        return StringUtils.join(new String[]{
                "CREATE TABLE",
                GROUP_FORMAT,
                "(",
                "    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,",
                "    label VARCHAR(100) NOT NULL,",
                "    defaults VARCHAR(1000) DEFAULT '' NOT NULL,",
                "    memo VARCHAR(200) DEFAULT '',",
                "    valid BIT(1) DEFAULT TRUE NOT NULL,",
                "    CONSTRAINT UNIQUE INDEX i18n_group_i18n_label_index (label)",
                ")"
        }, " ");
    }
}
