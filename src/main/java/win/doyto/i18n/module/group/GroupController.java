package win.doyto.i18n.module.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.response.ErrorCode;
import win.doyto.common.web.response.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.service.PageList;
import win.doyto.query.validation.CreateGroup;

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
        groupResponse.setName(groupEntity.getGroupName());
        groupResponse.setOwner(groupEntity.getCreateUserId());
        groupResponse.setLabel(groupEntity.getLabel());
        groupResponse.setCreateTime(groupEntity.getCreateTime());
        return groupResponse;
    }

    @GetMapping
    public PageList<GroupResponse> page(GroupQuery groupQuery) {
        return groupService.page(groupQuery, this::buildResponse);
    }

    @PutMapping("update/label")
    public void updateLabel(@RequestBody @Valid GroupRequest group) {
        GroupEntity origin = groupService.get(group.getId());
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLabel(group.getLabel());
        origin.setUpdateTime(new Date());
        groupService.update(origin);
    }

    @DeleteMapping("{id}")
    public void delete(@CurrentUser String operator, @PathVariable("id") Integer id) {
        GroupEntity groupEntity = groupService.get(id);
        ErrorCode.assertNotNull(groupEntity, I18nErrorCode.RECORD_NOT_FOUND);
        ErrorCode.assertTrue(groupEntity.getCreateUserId().equals(operator), I18nErrorCode.RECORD_NOT_FOUND);
        groupEntity.setDeleted(true);
        groupService.update(groupEntity);
    }

    @PostMapping
    public void create(@RequestBody @Validated(CreateGroup.class) GroupRequest groupRequest) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCreateUserId(groupRequest.getUsername());
        groupEntity.setGroupName(groupRequest.getName());
        groupEntity.setLabel(groupRequest.getLabel());
        groupEntity.setValid(true);
        groupEntity.setDeleted(false);
        groupService.create(groupEntity);
    }

    public PageList<GroupResponse> page(String username, GroupQuery groupQuery) {
        groupQuery.setUsername(username);
        return page(groupQuery);
    }

    public GroupResponse getById(Integer groupId) {
        GroupEntity groupEntity = groupService.get(groupId);
        ErrorCode.assertTrue(groupEntity != null && !groupEntity.getDeleted(), I18nErrorCode.RECORD_NOT_FOUND);
        return buildResponse(groupEntity);
    }

}
