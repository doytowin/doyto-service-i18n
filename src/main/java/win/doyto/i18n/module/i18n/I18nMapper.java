package win.doyto.i18n.module.i18n;

import java.util.List;
import java.util.Map;

/**
 * I18nMapper
 *
 * @author f0rb on 2017-03-30.
 */
@SuppressWarnings({"squid:S1214"})
public interface I18nMapper {

    List<Map<String, Object>> queryAll(I18nQuery i18nQuery);

    long count(I18nQuery i18nQuery);

    /**
     * 如果locale_${locale}为null, 则以默认值替代
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value}
     */
    List<LangView> langByGroupAndLocale(String user, String group, String locale);

    /**
     * 查询标签, 默认值, 语种对应的翻译
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value,defaults}
     */
    List<LangView> langWithDefaultsByGroupAndLocale(String user, String group, String locale);

    int saveTranslation(String user, String group, String locale, Map<String, String> langMap);

    default void existGroup(String user, String group) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).build();
        i18nQuery.setPageSize(1);
        count(i18nQuery);
    }

    default void existLocaleOnGroup(String user, String group, String locale) {
        langWithDefaultsByGroupAndLocale(user, group, locale);
    }

}
