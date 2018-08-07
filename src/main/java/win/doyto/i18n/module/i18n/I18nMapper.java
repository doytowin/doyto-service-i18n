package win.doyto.i18n.module.i18n;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import static win.doyto.web.service.IMapper.*;

/**
 * I18nMapper
 *
 * @author f0rb on 2017-03-30.
 */
@Mapper
public interface I18nMapper {

    String GROUP_FORMAT = "i18n_data_${user}_${group}";

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @Results(id = "localeMap", value = {
            @Result(column = "id"),
            @Result(column = "memo"),
            @Result(column = "createTime"),
            @Result(column = "updateTime"),
            @Result(column = "valid")
    })
    List<LinkedHashMap<String, ?>> langByGroup(@Param("user") String user, @Param("group") String group);

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @ResultMap("localeMap")
    List<LinkedHashMap<String, ?>> pageLangByGroup(@Param("user") String user, @Param("group") String group, RowBounds rowBounds);

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @ResultMap("localeMap")
    List<LinkedHashMap<String, ?>> query(I18n i18n);

    @Select({COUNT_, GROUP_FORMAT, " WHERE valid = true"})
    long count(I18n i18n);

    /**
     * 如果locale_${locale}为null, 则以默认值替代
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value}
     */
    @Select("SELECT label, IF(locale_${locale} IS NULL OR locale_${locale} = '', defaults, locale_${locale}) AS value FROM " + GROUP_FORMAT)
    List<Lang> langByGroupAndLocale(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

    /**
     * 查询标签, 默认值, 语种对应的翻译
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value,defaults}
     */
    @Select("SELECT label, defaults, locale_${locale} AS value FROM " + GROUP_FORMAT)
    List<Lang> langWithDefaultsByGroupAndLocale(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

    @Update({
            "<script>",
            "INSERT INTO",
            GROUP_FORMAT,
            "(label, defaults, locale_${locale})",
            "VALUES",
            "<foreach collection='map' index='key' item='value' separator=','>",
            "(#{key}, #{key}, #{value})",
            "</foreach>",
            "ON DUPLICATE KEY UPDATE",
            "locale_${locale} = VALUES(locale_${locale})",
            "</script>"
    })
    @Options
    int saveTranslation(@Param("user") String user, @Param("group") String group, @Param("locale") String locale, @Param("map") Map<String, String> langMap);

    @Update({
            "ALTER TABLE",
            GROUP_FORMAT,
            "ADD locale_${locale} VARCHAR(1000) NOT NULL DEFAULT ''"
    })
    void addLocaleOnGroup(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

    @Select({LIST_, GROUP_FORMAT, _LIMIT_1})
    Object existGroup(@Param("user") String user, @Param("group") String group);

    @Select({"SELECT locale_${locale} FROM ", GROUP_FORMAT, _LIMIT_1})
    Object existLocaleOnGroup(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

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

    @Update("DROP TABLE IF EXISTS" + GROUP_FORMAT)
    void deleteGroupTable(@Param("user") String owner, @Param("group") String name);
}
