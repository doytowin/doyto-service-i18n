package win.doyto.i18n.module.i18n;

import java.util.List;
import java.util.Map;

import win.doyto.web.PageResponse;
import win.doyto.web.RestException;

/**
 * I18nService
 *
 * @author f0rb on 2017-03-31.
 */
public interface I18nService {

    List query(String user, String group);

    PageResponse query(I18n i18n);

    List<Lang> query(String user, String group, String locale);

    List<Lang> queryWithDefaults(String user, String group, String locale);

    void checkGroup(String user, String group) throws RestException;

    void checkGroupAndLocale(String user, String group, String locale) throws RestException;

    String addLocaleOnGroup(String user, String group, String locale);

    void saveTranslation(String user, String group, String locale, Map<String, String> translationMap);

    void autoTranslate(String user, String group, String locale);

    void createGroupTable(String user, String name);
}
