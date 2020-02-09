package win.doyto.i18n.module.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.common.web.controller.AbstractRestController;
import win.doyto.common.web.response.JsonBody;

import javax.annotation.Resource;

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
    @Resource
    private GroupService groupService;

    @Override
    protected GroupService getService() {
        return groupService;
    }

    @Override
    protected GroupResponse buildResponse(GroupEntity groupEntity) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupEntity.getId());
        groupResponse.setName(groupEntity.getName());
        groupResponse.setOwner(groupEntity.getOwner());
        groupResponse.setLabel(groupEntity.getLabel());
        return groupResponse;
    }

    @Override
    protected GroupEntity buildEntity(GroupRequest groupRequest) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setName(groupRequest.getName());
        groupEntity.setLabel(groupRequest.getLabel());
        groupEntity.setOwner(groupRequest.getName());
        return groupEntity;
    }

}