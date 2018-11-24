package win.doyto.i18n.module.group;

import javax.validation.Valid;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.query.PageList;
import win.doyto.web.RestEnum;
import win.doyto.web.RestError;
import win.doyto.web.spring.RestBody;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@RestBody
@RestController
@RequestMapping({"/api/resource-group", "/api/group"})
@PreAuthorize("hasAnyRole('i18n')")
public class ResourceGroupController {
    private ResourceGroupService groupService;

    public ResourceGroupController(ResourceGroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public PageList<ResourceGroup> page(@CurrentUser String owner, ResourceGroupQuery resourceGroupQuery) {
        resourceGroupQuery.setOwner(owner);
        return groupService.page(resourceGroupQuery);
    }

    @PostMapping("update/label")
    public RestError updateLabel(@RequestBody @Valid UpdateGroupLabelRequest group) {
        ResourceGroup resourceGroup = groupService.updateLabel(group.getId(), group.getLabel());
        return resourceGroup == null ? RestEnum.RecordNotFound.value() : RestError.Success;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public RestError delete(@PathVariable("id") Integer id) {
        ResourceGroup resourceGroup = groupService.logicDelete(id);
        return resourceGroup == null ? RestEnum.RecordNotFound.value() : RestError.Success;
    }

    @Data
    public static class UpdateGroupLabelRequest {
        private Integer id;
        private String label;
    }
}
