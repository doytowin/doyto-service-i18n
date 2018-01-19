package win.doyto.i18n.service;

import win.doyto.i18n.mapper.ResourceGroupMapper;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.web.service.IService;

/**
 * ResourceGroupService
 *
 * @author f0rb on 2017-03-29.
 */
@SuppressWarnings("unused")
public interface ResourceGroupService extends IService<ResourceGroup> {

    ResourceGroupMapper getIMapper();

    ResourceGroup getGroup(String user, String groupName);

    ResourceGroup checkGroup(Integer id, String user, String groupName);
}
