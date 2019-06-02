package win.doyto.i18n.module.i18n;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * I18nDataServiceTest
 *
 * @author f0rb on 2019-06-02
 */
public class I18NCrudServiceTest {

    @Test
    public void insertSql() {
        String sql = "INSERT INTO i18n_data_${user}_${group} (label, defaults, locale_${locale}) VALUES (?, ?, ?), (?, ?, ?) ON DUPLICATE KEY UPDATE locale_${locale} = VALUES(locale_${locale})";
        String actual = I18nCrudService.buildInsertSql(2);
        assertEquals(sql, actual);
        System.out.println(actual);
    }

    @Test
    public void replaceHolderEx() {
        String sql = I18nCrudService.replaceHolderEx(I18nCrudService.buildInsertSql(2), "i18n", "i18n", "zh");
        assertEquals("INSERT INTO i18n_data_i18n_i18n (label, defaults, locale_zh) VALUES (?, ?, ?), (?, ?, ?) ON DUPLICATE KEY UPDATE locale_zh = VALUES(locale_zh)", sql);
        System.out.println(sql);

    }
}