package win.doyto.i18n.module.locale;

import win.doyto.web.service.IService;

/**
 * ResourceLocaleService
 *
 * @author f0rb on 2017-04-16.
 */
public interface ResourceLocaleService extends IService<ResourceLocale> {

    ResourceLocaleMapper getIMapper();

    ResourceLocale getByGroupAndLocale(Integer groupId, String locale);

}
