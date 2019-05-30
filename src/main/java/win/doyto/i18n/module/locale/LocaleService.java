package win.doyto.i18n.module.locale;

import java.util.List;

/**
 * LocaleService
 *
 * @author f0rb on 2019-05-23
 */
public interface LocaleService {
    LocaleResponse getByGroupAndLocale(String group, String locale);

    void create(LocaleRequest request);

    List<LocaleResponse> list(LocaleQuery localeQuery);
}
