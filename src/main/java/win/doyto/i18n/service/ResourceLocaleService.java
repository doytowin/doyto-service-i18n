package win.doyto.i18n.service;

import win.doyto.i18n.mapper.ResourceLocaleMapper;
import win.doyto.i18n.model.ResourceLocale;
import win.doyto.web.service.IService;

/**
 * ResourceLocaleService
 *
 * @author f0rb on 2017-04-16.
 */
public interface ResourceLocaleService extends IService<ResourceLocale> {

    ResourceLocaleMapper getIMapper();

}
