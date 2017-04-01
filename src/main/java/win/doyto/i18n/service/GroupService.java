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

}
