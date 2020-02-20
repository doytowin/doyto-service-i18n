package win.doyto.i18n.module.locale;

import win.doyto.common.web.controller.RestApi;

import java.util.List;

/**
 * LocaleService
 *
 * @author f0rb on 2019-05-23
 */
public interface LocaleApi extends RestApi<Integer, LocaleQuery, LocaleRequest, LocaleResponse> {

    default String getBaiduLocale(String user, String group, String locale) {
        List<LocaleResponse> responses = list(LocaleQuery.builder().createUserId(user).groupName(group).locale(locale).build());
        return responses.isEmpty() ? locale : responses.get(0).getBaiduLocale();
    }

}
