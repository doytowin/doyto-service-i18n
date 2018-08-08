package win.doyto.i18n.module.group;

import win.doyto.common.repository.IDataService;
import win.doyto.web.RestError;

/**
 * ResourceGroupService
 *
 * @author f0rb on 2017-03-29.
 */
@SuppressWarnings("unused")
public interface ResourceGroupService extends IDataService<ResourceGroup, Integer, ResourceGroupQuery> {

    ResourceGroup getGroup(String user, String groupName);

    RestError deleteByUser(String user, Integer id);

    RestError create(String owner, String name, String label, String locale);

    RestError updateLabel(Integer groupId, String label);

}
