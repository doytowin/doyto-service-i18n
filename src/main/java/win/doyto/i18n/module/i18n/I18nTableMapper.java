package win.doyto.i18n.module.i18n;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import static win.doyto.i18n.module.i18n.I18nMapper.GROUP_FORMAT;

/**
 * I18nTableMapper
 *
 * @author f0rb on 2019-05-26
 */
@Mapper
public interface I18nTableMapper {

    @Update({
        "CREATE TABLE",
        GROUP_FORMAT,
        "(",
        "    id INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,",
        "    label VARCHAR(100) NOT NULL,",
        "    defaults VARCHAR(200) DEFAULT '' NOT NULL,",
        "    memo VARCHAR(200) DEFAULT '',",
        "    valid BIT(1) DEFAULT TRUE NOT NULL,",
        "    CONSTRAINT UNIQUE INDEX i18n_group_i18n_label_index (label)",
        ")"
    })
    void createGroupTable(@Param("user") String owner, @Param("group") String name);

    @Update({
        "ALTER TABLE",
        GROUP_FORMAT,
        "ADD locale_${locale} VARCHAR(1000) NOT NULL DEFAULT ''"
    })
    void addLocaleOnGroup(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

    @Update("DROP TABLE IF EXISTS" + GROUP_FORMAT)
    void dropGroupTable(@Param("user") String owner, @Param("group") String name);

}
