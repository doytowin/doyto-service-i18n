package win.doyto.i18n.module.group;

import win.doyto.query.service.PageList;

/**
 * GroupService
 *
 * @author f0rb on 2019-05-23
 */
public interface GroupApi {

    GroupResponse getById(Integer groupId);

    PageList<GroupResponse> page(GroupQuery query);

    default PageList<GroupResponse> page(String owner, GroupQuery groupQuery) {
        groupQuery.setOwner(owner);
        return page(groupQuery);
    }

    void updateLabel(GroupRequest groupRequest);

    void delete(String owner, Integer groupId);

}