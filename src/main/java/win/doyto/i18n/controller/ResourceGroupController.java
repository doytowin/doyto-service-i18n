package win.doyto.i18n.controller;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.module.group.ResourceGroup;
import win.doyto.i18n.module.group.ResourceGroupQuery;
import win.doyto.i18n.module.group.ResourceGroupService;
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
        return groupService.query().getData();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(Authentication authentication, @RequestBody @Valid ResourceGroupAddReq group) {
        return groupService.create(authentication.getName(), group.getName(), group.getLabel(), group.getLocale());
    }

    @PostMapping("update/label")
    public Object save(@RequestBody @Valid ResourceGroup group) {
        return groupService.updateLabel(group.getId(), group.getLabel());
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(Authentication authentication,  @PathVariable("id") Integer id) {
        return groupService.deleteByUser(id, authentication.getName());
    }

    /**
     * ResourceGroupAddReq
     *
     * @author f0rb on 2018-04-21.
     */
    @Getter
    @Setter
    public static class ResourceGroupAddReq {

        @Pattern(regexp = ".+")
        private String name;

        @Pattern(regexp = ".+")
        private String label;

        private String locale = "zh_CN";

    }
}