package win.doyto.i18n.module.group;

import win.doyto.query.core.PageList;

/**
 * GroupApi
 *
 * @author f0rb on 2019-05-23
 */
public interface GroupApi {

    void insertGroup(String owner, String group, String label);

    GroupResponse get(Integer groupId);

    GroupResponse getGroup(String username, String group);

    PageList<GroupResponse> page(GroupQuery query);
}
