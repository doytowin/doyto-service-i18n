package win.doyto.i18n.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;
import win.doyto.i18n.model.Lang;

import static win.doyto.web.service.IMapper.LIST_;
import static win.doyto.web.service.IMapper._LIMIT_1;

/**
 * GroupLocaleMapper
 *
 * @author f0rb on 2017-03-30.
 */
@Mapper
public interface GroupLocaleMapper {

    String GROUP_FORMAT = "i18n_group_${group}";

    @Select({LIST_, GROUP_FORMAT, " WHERE valid = true"})
    @Results({
            @Result(column = "id"),
            @Result(column = "memo"),
            @Result(column = "createTime"),
            @Result(column = "updateTime"),
            @Result(column = "valid")
    })
    List<LinkedHashMap<String, ?>> langByGroup(@Param("group") String group);

    @Select("SELECT label, locale_${locale} as value FROM " + GROUP_FORMAT)
    List<Lang> langByGroupAndLocale(@Param("group") String group, @Param("locale") String locale);

    @Insert({
            "<script>",
            "<foreach collection='map' index='key' item='value' separator=';'>",
            "UPDATE",
            GROUP_FORMAT,
            "SET locale_${locale} = #{value} WHERE label = #{key}",
            "</foreach>",
            "</script>"
    })
    int saveTranslation(@Param("group") String group, @Param("locale") String locale, @Param("map") Map<String, String> langMap);

    @Update({
            "ALTER TABLE",
            GROUP_FORMAT,
            "ADD locale_${locale} TEXT NOT NULL"
    })
    void addLocaleOnGroup(@Param("group") String group, @Param("locale") String locale);

    @Select({LIST_, GROUP_FORMAT, _LIMIT_1})
    Object existGroup(@Param("group") String group);

    @Select({"SELECT locale_${locale} FROM ", GROUP_FORMAT, _LIMIT_1})
    Object existLocaleOnGroup(@Param("group") String group, @Param("locale") String locale);
}
