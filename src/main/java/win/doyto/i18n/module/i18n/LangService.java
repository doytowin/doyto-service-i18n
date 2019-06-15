package win.doyto.i18n.module.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import win.doyto.query.service.AbstractDynamicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import static win.doyto.i18n.module.i18n.I18nEntity.GROUP_FORMAT;

/**
 * I18nLangService
 *
 * @author f0rb on 2019-06-14
 */
@Service
public class LangService extends AbstractDynamicService<LangView, Integer, I18nQuery> {

    private static final ColumnMapRowMapper COLUMN_MAP_ROW_MAPPER = new ColumnMapRowMapper();

    @Override
    protected String resolveCacheKey(LangView i18nLangView) {
        return i18nLangView.getId() + "";
    }

    public List<LangView> query(String user, String group, String locale) {
        return query(I18nQuery.builder().user(user).group(group).locale(locale).build());
    }

    public void existLocaleOnGroup(String user, String group, String locale) {
        query(user, group, locale);
    }

    public void existGroup(String user, String group) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).build();
        i18nQuery.setPageSize(1);
        count(i18nQuery);
    }

    public List<Map<String, Object>> queryAll(I18nQuery i18nQuery) {
        return queryColumns(i18nQuery, COLUMN_MAP_ROW_MAPPER, "*");
    }

    /**
     * 如果locale_${locale}为null, 则以默认值替代
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value}
     */
    public List<LangView> langByGroupAndLocale(String user, String group, String locale) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).locale(locale).build();
        return queryColumns(
            i18nQuery, new BeanPropertyRowMapper<>(LangView.class),
            "label, defaults, IF(locale_${locale} IS NULL OR LENGTH(locale_${locale}) = 0, defaults, locale_${locale}) AS value");
    }

    private static final Pattern PTN_$EX = Pattern.compile("\\$\\{(\\w+)}");
    @Resource
    private JdbcOperations jdbcOperations;

    static String replaceHolderEx(String input, String user, String group, String locale) {

        HashMap<String, String> params = new HashMap<>();
        params.put("user", user);
        params.put("group", group);
        params.put("locale", locale);
        Matcher matcher = PTN_$EX.matcher(input);
        if (!matcher.find()) {
            return input;
        }

        StringBuffer sb = new StringBuffer();
        do {
            String fieldName = matcher.group(1);
            String replacement = String.valueOf(params.getOrDefault(fieldName, ""));
            replacement = StringUtils.remove(replacement, ' ');
            matcher.appendReplacement(sb, replacement);
        } while (matcher.find());
        return matcher.appendTail(sb).toString();
    }

    public void createGroupTable(String owner, String group) {
        String sql = StringUtils.join(new String[]{
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
        }, " ");
        jdbcOperations.update(replaceHolderEx(sql, owner, group, ""));
    }

    public void addLocaleOnGroup(String owner, String group, String locale) {
        String sql = "ALTER TABLE " + GROUP_FORMAT + " ADD locale_${locale} VARCHAR(1000) NOT NULL DEFAULT ''";
        jdbcOperations.update(replaceHolderEx(sql, owner, group, locale));

    }
}
