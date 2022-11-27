package win.doyto.i18n.module.group;

import win.doyto.query.core.PageList;

/**
 * GroupApi
 *
 * @author f0rb on 2019-05-23
 */
public interface GroupApi {

    PageList<GroupResponse> page(GroupQuery query);

}