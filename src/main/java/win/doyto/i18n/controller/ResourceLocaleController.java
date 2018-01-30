package win.doyto.i18n.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.i18n.model.ResourceLocale;
import win.doyto.i18n.service.ResourceGroupService;
import win.doyto.i18n.service.ResourceLocaleService;
import win.doyto.web.spring.RestBody;

import static win.doyto.i18n.common.Constant.DEFAULT_USER;

/**
 * ResourceLocaleController
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@RestBody
@RestController
@RequestMapping("/api/resource/{groupId}_{group}/locale")
public class ResourceLocaleController {
    @Resource
    private ResourceLocaleService resourceLocaleService;
    @Resource
    private ResourceGroupService resourceGroupService;

    @RequestMapping(method = RequestMethod.GET)
    public Object query(ResourceLocale resourceLocale, @PathVariable("group") String group) {
        ResourceGroup resourceGroup = resourceGroupService.getGroup(DEFAULT_USER, group);
        resourceLocale.setGroupId(resourceGroup.getId());
        return resourceLocaleService.query(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id) {
        return resourceLocaleService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(
            @RequestBody @Valid ResourceLocale resourceLocale, BindingResult result,
            @PathVariable("groupId") Integer groupId,
            @PathVariable("group") String group
    ) {
        if (result.hasErrors()) {
            return result;
        }
        ResourceGroup resourceGroup = resourceGroupService.checkGroup(groupId, DEFAULT_USER, group);
        resourceLocale.setGroupId(resourceGroup.getId());
        resourceLocale.setGroup(group);
        return resourceLocaleService.add(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public Object save(
            @RequestBody @Valid ResourceLocale resourceLocale,
            BindingResult result,
            @PathVariable("groupId") Integer groupId,
            @PathVariable("group") String group,
            @PathVariable(value = "id", required = false) String id
    ) {
        if (result.hasErrors()) {
            return result;
        }
        ResourceGroup resourceGroup = resourceGroupService.checkGroup(groupId, DEFAULT_USER, group);
        resourceLocale.setGroupId(resourceGroup.getId());
        return resourceLocaleService.save(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        ResourceLocale deleted = resourceLocaleService.delete(id);
        return deleted != null ? "语种删除成功: " + deleted.getLocale()    : null;
    }
}