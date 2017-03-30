package win.doyto.i18n.service;

import java.util.List;
import java.util.Map;

import win.doyto.i18n.model.Group;
import win.doyto.i18n.mapper.GroupMapper;
import win.doyto.i18n.model.Lang;
import win.doyto.web.service.BusinessException;
import win.doyto.web.service.IService;

/**
 * GroupService
 *
 * @author f0rb on 2017-03-29.
 */
@SuppressWarnings("unused")
public interface GroupService extends IService<Group> {

    GroupMapper getIMapper();

    List query(String group);

    List<Lang> query(String group, String locale);

    void checkGroup(String group) throws BusinessException;

    void checkGroupAndLocale(String group, String locale) throws BusinessException;

    String addLocaleOnGroup(String group, String locale);

    List<Lang> saveTranslation(String group, String locale, Map<String, String> langMap);

}
