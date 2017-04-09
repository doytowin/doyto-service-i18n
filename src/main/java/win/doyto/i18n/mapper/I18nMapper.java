package win.doyto.i18n.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import win.doyto.i18n.model.I18n;
import win.doyto.i18n.model.Lang;

import static win.doyto.web.service.IMapper.COUNT_;
import static win.doyto.web.service.IMapper.LIST_;
import static win.doyto.web.service.IMapper._LIMIT_1;

/**
 * I18nMapper
 *
 * @author f0rb on 2017-03-30.
 */
@Mapper
public interface I18nMapper {

    String GROUP_FORMAT = "i18n_i18n_${group}";

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @Results(id = "localeMap", value = {
            @Result(column = "id"),
            @Result(column = "memo"),
            @Result(column = "createTime"),
            @Result(column = "updateTime"),
            @Result(column = "valid")
    })
    List<LinkedHashMap<String, ?>> langByGroup(@Param("group") String group);

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @ResultMap("localeMap")
    List<LinkedHashMap<String, ?>> pageLangByGroup(@Param("group") String group, RowBounds rowBounds);

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
    List<Lang> langByGroupAndLocale(@Param("group") String group, @Param("locale") String locale);

    /**
     * 查询标签, 默认值, 语种对应的翻译
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value,defaults}
     */
    @Select("SELECT label, defaults, locale_${locale} AS value FROM " + GROUP_FORMAT)
    List<Lang> langWithDefaultsByGroupAndLocale(@Param("group") String group, @Param("locale") String locale);

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
            "locale_${locale} = #{value}",
            "</script>"
    })
    @Options
    int saveTranslation(@Param("group") String group, @Param("locale") String locale, @Param("map") Map<String, String> langMap);

    @Update({
            "ALTER TABLE",
            GROUP_FORMAT,
            "ADD locale_${locale} TEXT"
    })
    void addLocaleOnGroup(@Param("group") String group, @Param("locale") String locale);

    @Select({LIST_, GROUP_FORMAT, _LIMIT_1})
    Object existGroup(@Param("group") String group);

    @Select({"SELECT locale_${locale} FROM ", GROUP_FORMAT, _LIMIT_1})
    Object existLocaleOnGroup(@Param("group") String group, @Param("locale") String locale);
}
