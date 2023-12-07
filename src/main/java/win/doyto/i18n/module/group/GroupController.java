package win.doyto.i18n.module.group;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import win.doyto.query.core.PageList;
import win.doyto.query.util.BeanUtil;
import win.doyto.query.validation.CreateGroup;
import win.doyto.query.web.response.ErrorCode;
import win.doyto.query.web.response.JsonBody;
import win.doyto.query.web.response.PresetErrorCode;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping("/api/group/")
@PreAuthorize("hasAnyRole('i18n')")
@AllArgsConstructor
class GroupController implements GroupApi {

    @Resource
    private GroupService groupService;

    protected GroupResponse buildResponse(GroupEntity groupEntity) {
        return BeanUtil.convertTo(groupEntity, GroupResponse.class);
    }

    @GetMapping
    public PageList<GroupResponse> page(GroupQuery groupQuery) {
        return groupService.page(groupQuery, this::buildResponse);
    }

    @PutMapping("update/label")
    public void updateLabel(@RequestBody @Valid GroupRequest group) {
        GroupEntity origin = groupService.get(group.getId());
        ErrorCode.assertNotNull(origin, PresetErrorCode.ENTITY_NOT_FOUND);
        origin.setLabel(group.getLabel());
        groupService.update(origin);
    }

    @DeleteMapping("{id}")
    public void delete(Authentication user, @PathVariable("id") Integer id) {
        delete(user.getName(), id);
    }

    public void delete(String operator, Integer id) {
        GroupEntity groupEntity = groupService.get(id);
        ErrorCode.assertNotNull(groupEntity, PresetErrorCode.ENTITY_NOT_FOUND);
        ErrorCode.assertTrue(groupEntity.getCreateUserId().equals(operator), PresetErrorCode.ENTITY_NOT_FOUND);
        groupEntity.setDeleted(true);
        groupService.update(groupEntity);
    }

    @PostMapping
    public void create(@RequestBody @Validated(CreateGroup.class) GroupRequest groupRequest) {
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setCreateUserId(groupRequest.getCreateUserId());
        groupEntity.setName(groupRequest.getName());
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
        ErrorCode.assertTrue(groupEntity != null && !groupEntity.getDeleted(), PresetErrorCode.ENTITY_NOT_FOUND);
        return buildResponse(groupEntity);
    }

}
