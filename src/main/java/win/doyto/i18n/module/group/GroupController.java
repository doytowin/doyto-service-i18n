package win.doyto.i18n.module.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.response.ErrorCode;
import win.doyto.common.web.response.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.service.PageList;

import java.util.Date;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping({"/api/resource-group", "/api/group"})
@PreAuthorize("hasAnyRole('i18n')")
@AllArgsConstructor
class GroupController implements GroupApi {

    @Resource
    private GroupService groupService;

    protected GroupResponse buildResponse(GroupEntity groupEntity) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupEntity.getId());
        groupResponse.setName(groupEntity.getName());
        groupResponse.setOwner(groupEntity.getOwner());
        groupResponse.setLabel(groupEntity.getLabel());
        groupResponse.setCreateTime(groupEntity.getCreateTime());
        return groupResponse;
    }

    @GetMapping
    public PageList<GroupResponse> page(GroupQuery groupQuery) {
        return groupService.page(groupQuery, this::buildResponse);
    }

    @PostMapping("update/label")
    public void updateLabel(@RequestBody @Valid GroupRequest group) {
        GroupEntity origin = groupService.get(group.getId());
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLabel(group.getLabel());
        origin.setUpdateTime(new Date());
        groupService.update(origin);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@CurrentUser String owner, @PathVariable("id") Integer id) {
        GroupEntity groupEntity = groupService.get(id);
        ErrorCode.assertNotNull(groupEntity, I18nErrorCode.RECORD_NOT_FOUND);
        groupEntity.setOwner(owner);
        groupEntity.setDeleted(true);
        groupService.update(groupEntity);
    }

    @Override
    public void insertGroup(String owner, String name, String label) {
        GroupEntity groupEntity;
        groupEntity = new GroupEntity();
        groupEntity.setOwner(owner);
        groupEntity.setName(name);
        groupEntity.setLabel(label);
        groupEntity.setValid(true);
        groupEntity.setDeleted(false);
        groupEntity.setCreateTime(new Date());
        groupEntity.setUpdateTime(new Date());
        groupService.create(groupEntity);
    }

    @Override
    public GroupResponse getById(Integer groupId) {
        GroupEntity groupEntity = groupService.get(groupId);
        ErrorCode.assertTrue(groupEntity != null && !groupEntity.getDeleted(), I18nErrorCode.RECORD_NOT_FOUND);
        return buildResponse(groupEntity);
    }

    @Override
    public GroupResponse getGroup(String username, String group) {
        GroupEntity groupEntity = groupService.get(GroupQuery.builder().owner(username).groupNameLike(group).build());
        ErrorCode.assertNotNull(groupEntity, I18nErrorCode.RECORD_NOT_FOUND);
        return buildResponse(groupEntity);
    }
}
