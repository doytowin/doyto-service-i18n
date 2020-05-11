package win.doyto.i18n.module.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.web.controller.AbstractRestController;
import win.doyto.query.web.response.JsonBody;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping("/admin/group")
@PreAuthorize("hasAnyRole('admin', 'admin:i18n')")
public class GroupAdminController extends AbstractRestController<GroupEntity, Integer, GroupQuery, GroupRequest, GroupResponse> {

    public GroupAdminController(GroupService groupService) {
        super(groupService);
    }

}