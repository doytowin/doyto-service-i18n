package win.doyto.i18n.module.group;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.ErrorCode;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.service.AbstractCrudService;
import win.doyto.query.service.PageList;

import java.util.Date;
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
class GroupController extends AbstractCrudService<GroupEntity, Integer, GroupQuery> implements GroupService {

    @GetMapping
    public PageList<GroupResponse> page(@CurrentUser String owner, GroupQuery groupQuery) {
        groupQuery.setOwner(owner);
        return page(groupQuery);
    }

    public PageList<GroupResponse> page(GroupQuery groupQuery) {
        return page(groupQuery, GroupResponse::build);
    }

    @PostMapping("update/label")
    public void updateLabel(@RequestBody @Valid GroupRequest group) {
        GroupEntity origin = get(group.getId());
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLabel(group.getLabel());
        origin.setUpdateTime(new Date());
        update(origin);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@CurrentUser String owner, @PathVariable("id") Integer id) {
        GroupEntity groupEntity = get(id);
        ErrorCode.assertNotNull(groupEntity, I18nErrorCode.RECORD_NOT_FOUND);
        groupEntity.setOwner(owner);
        groupEntity.setDeleted(true);
        update(groupEntity);
    }

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
        create(groupEntity);
    }

    @Override
    public GroupResponse getById(Integer groupId) {
        GroupEntity groupEntity = get(groupId);
        ErrorCode.assertTrue(groupEntity != null && !groupEntity.getDeleted(), I18nErrorCode.RECORD_NOT_FOUND);
        return GroupResponse.build(groupEntity);
    }

    @Override
    public GroupResponse getGroup(String username, String group) {
        GroupEntity groupEntity = get(GroupQuery.builder().owner(username).nameLike(group).build());
        ErrorCode.assertNotNull(groupEntity, I18nErrorCode.RECORD_NOT_FOUND);
        return GroupResponse.build(groupEntity);
    }
}
