package win.doyto.i18n.module.locale;

import win.doyto.query.web.controller.RestApi;

import java.util.List;

/**
 * LocaleService
 *
 * @author f0rb on 2019-05-23
 */
public interface LocaleApi extends RestApi<Integer, LocaleQuery, LocaleEntity, LocaleEntity> {

    default String getBaiduLocale(String user, String group, String locale) {
        List<LocaleEntity> localeEntities = query(LocaleQuery.builder().createUserId(user).groupName(group).locale(locale).build());
        return localeEntities.isEmpty() ? locale : localeEntities.get(0).getBaiduLocale();
    }

}
