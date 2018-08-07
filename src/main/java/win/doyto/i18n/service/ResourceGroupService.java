package win.doyto.i18n.service;

import win.doyto.i18n.mapper.ResourceGroupMapper;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.i18n.module.group.ResourceGroupQuery;
import win.doyto.web.RestError;
import win.doyto.web.service.ServiceApi;

/**
 * ResourceGroupService
 *
 * @author f0rb on 2017-03-29.
 */
@SuppressWarnings("unused")
public interface ResourceGroupService extends ServiceApi<ResourceGroup, ResourceGroupQuery> {

    ResourceGroupMapper getIMapper();

    ResourceGroup getGroup(String user, String groupName);

    RestError deleteByUser(Integer id, String user);

    RestError create(String owner, String name, String label, String locale);

    RestError updateLabel(Integer groupId, String label);

}
