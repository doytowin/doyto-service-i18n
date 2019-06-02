package win.doyto.i18n.module.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Service;
import win.doyto.query.service.AbstractCrudService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import static win.doyto.i18n.module.i18n.I18nEntity.GROUP_FORMAT;

/**
 * I18nDataService
 *
 * @author f0rb on 2019-05-30
 */
@Service
public class I18nCrudService extends AbstractCrudService<I18nEntity, Integer, I18nQuery> implements I18nMapper {

    private static final ColumnMapRowMapper COLUMN_MAP_ROW_MAPPER = new ColumnMapRowMapper();
    private static final Pattern PTN_$EX = Pattern.compile("\\$\\{(\\w+)}");
    @Resource
    private JdbcOperations jdbcOperations;

    static String buildInsertSql(int size) {
        List<String> sqls = new ArrayList<>();
        sqls.add("INSERT INTO");
        sqls.add(I18nEntity.GROUP_FORMAT);
        sqls.add("(label, defaults, locale_${locale})");
        sqls.add("VALUES");
        for (int i = 0; i < size - 1; i++) {
            sqls.add("(?, ?, ?),");
        }
        sqls.add("(?, ?, ?)");
        sqls.add("ON DUPLICATE KEY UPDATE");
        sqls.add("locale_${locale} = VALUES(locale_${locale})");
        return StringUtils.join(sqls, " ");
    }

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
            matcher = matcher.appendReplacement(sb, replacement);
        } while (matcher.find());
        return matcher.appendTail(sb).toString();
    }

    @Override
    public List<Map<String, Object>> queryAll(I18nQuery i18nQuery) {
        return dataAccess.queryColumns(i18nQuery, COLUMN_MAP_ROW_MAPPER, "*");
    }

    @Override
    public long count(I18nQuery i18nQuery) {
        return dataAccess.count(i18nQuery);
    }

    @Override
    public List<LangView> langByGroupAndLocale(String user, String group, String locale) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).locale(locale).build();
        return dataAccess.queryColumns(i18nQuery, new BeanPropertyRowMapper<>(LangView.class),
            "label, defaults, IF(locale_${locale} IS NULL OR LENGTH(locale_${locale}) = 0, defaults, locale_${locale}) AS value");
    }

    @Override
    public List<LangView> langWithDefaultsByGroupAndLocale(String user, String group, String locale) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).locale(locale).build();
        return dataAccess.queryColumns(i18nQuery, new BeanPropertyRowMapper<>(LangView.class),
           "label, defaults, locale_${locale} AS value");
    }

    @Override
    public int saveTranslation(String user, String group, String locale, Map<String, String> langMap) {

        ArrayList<Object> args = new ArrayList<>();
        String sql = buildInsert(langMap, args);

        sql = replaceHolderEx(sql, user, group, locale);
        return jdbcOperations.update(sql, args);
    }

    private String buildInsert(Map<String, String> langMap, List<Object> args) {
        buildInsertArgs(langMap, args);
        return buildInsertSql(langMap.size());
    }

    private void buildInsertArgs(Map<String, String> langMap, List<Object> args) {
        langMap.keySet().forEach(key -> {
            args.add(key);
            args.add(key);
            args.add(langMap.get(key));
        });
    }

    void createGroupTable(String owner, String group) {
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

    void addLocaleOnGroup(String owner, String group, String locale) {
        String sql = "ALTER TABLE " + GROUP_FORMAT + " ADD locale_${locale} VARCHAR(1000) NOT NULL DEFAULT ''";
        jdbcOperations.update(replaceHolderEx(sql, owner, group, locale));

    }
}
