package win.doyto.i18n.module.i18n;

import org.apache.ibatis.annotations.*;
import win.doyto.query.core.QueryBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * I18nMapper
 *
 * @author f0rb on 2017-03-30.
 */
@Mapper
@SuppressWarnings({"squid:S00115", "squid:S1214"})
public interface I18nMapper {

    String GROUP_FORMAT = "i18n_data_${user}_${group}";

    @Results(id = "localeMap", value = {
            @Result(column = "id"),
            @Result(column = "memo"),
            @Result(column = "createTime"),
            @Result(column = "updateTime"),
            @Result(column = "valid")
    })
    @SelectProvider(type = QueryBuilder.class, method = "buildSelect")
    List<LinkedHashMap<String, Object>> query(I18nQuery i18nQuery);

    @SelectProvider(type = QueryBuilder.class, method = "buildCount")
    long count(I18nQuery i18nQuery);

    /**
     * 如果locale_${locale}为null, 则以默认值替代
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value}
     */
    @Select("SELECT label, defaults, IF(locale_${locale} IS NULL OR LENGTH(locale_${locale}) = 0, defaults, locale_${locale}) AS value FROM " + GROUP_FORMAT)
    List<LangView> langByGroupAndLocale(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

    /**
     * 查询标签, 默认值, 语种对应的翻译
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value,defaults}
     */
    @Select("SELECT label, defaults, locale_${locale} AS value FROM " + GROUP_FORMAT)
    List<LangView> langWithDefaultsByGroupAndLocale(@Param("user") String user, @Param("group") String group, @Param("locale") String locale);

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

    default void existGroup(String user, String group) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).build();
        i18nQuery.setPageSize(1);
        count(i18nQuery);
    }

    default void existLocaleOnGroup(@Param("user") String user, @Param("group") String group, @Param("locale") String locale) {
        langWithDefaultsByGroupAndLocale(user, group, locale);
    }

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
