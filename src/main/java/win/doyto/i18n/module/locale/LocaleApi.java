package win.doyto.i18n.module.locale;

import java.util.List;

/**
 * LocaleApi
 *
 * @author f0rb on 2019-05-23
 */
public interface LocaleApi {
    LocaleResponse getByGroupAndLocale(String group, String locale);

    void create(LocaleRequest request);

    List<LocaleResponse> query(LocaleQuery localeQuery);
}
