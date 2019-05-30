package win.doyto.i18n.module.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.module.group.GroupQuery;
import win.doyto.i18n.module.group.GroupResponse;
import win.doyto.i18n.module.group.GroupService;
import win.doyto.query.service.PageList;

import javax.annotation.Resource;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping("/admin/resource-group")
@PreAuthorize("hasAnyRole('admin', 'admin:i18n')")
public class GroupAdminController {
    @Resource
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public PageList<GroupResponse> page(GroupQuery query) {
        return groupService.page(query);
    }

}