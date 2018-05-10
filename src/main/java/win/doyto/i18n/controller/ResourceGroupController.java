package win.doyto.i18n.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.i18n.model.ResourceGroupAddReq;
import win.doyto.i18n.query.ResourceGroupQuery;
import win.doyto.i18n.service.ResourceGroupService;
import win.doyto.web.spring.RestBody;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@RestBody
@RestController
@RequestMapping("/api/resource-group")
@PreAuthorize("hasAnyRole('i18n')")
public class ResourceGroupController {
    @Resource
    private ResourceGroupService groupService;

    @GetMapping
    public Object page(ResourceGroupQuery query) {
        return groupService.page(query);
    }

    @GetMapping("list")
    public Object query() {
        List<ResourceGroup> resourceGroupList = groupService.query().getData();

        return resourceGroupList;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(
            Authentication authentication,
            @RequestBody @Valid ResourceGroupAddReq group,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.create(authentication.getName(), group.getName(), group.getLabel(), group.getLocale());
    }

    @PostMapping("update/label")
    public Object save(@RequestBody @Valid ResourceGroup group, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.updateLabel(group.getId(), group.getLabel());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(Authentication authentication,  @PathVariable("id") Integer id) {
        return groupService.deleteByUser(id, authentication.getName());
    }

}