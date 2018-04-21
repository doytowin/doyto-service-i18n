package win.doyto.i18n.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.i18n.model.ResourceLocale;
import win.doyto.i18n.service.ResourceGroupService;
import win.doyto.i18n.service.ResourceLocaleService;
import win.doyto.web.spring.RestBody;

/**
 * ResourceLocaleController
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@RestBody
@RestController
@RequestMapping("/api/resource/{group}/locale")
public class ResourceLocaleController {
    @Resource
    private ResourceLocaleService resourceLocaleService;
    @Resource
    private ResourceGroupService resourceGroupService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('i18n')")
    public Object query(
            ResourceLocale resourceLocale,
            Authentication oper,
            @PathVariable("group") String group
    ) {
        ResourceGroup resourceGroup = resourceGroupService.getGroup(oper.getName(), group);
        resourceLocale.setGroupId(resourceGroup.getId());
        return resourceLocaleService.page(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('i18n')")
    public Object get(@PathVariable("id") Integer id) {
        return resourceLocaleService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('i18n')")
    public Object add(
            @RequestBody @Valid ResourceLocale resourceLocale, BindingResult result,
            Authentication oper,
            @PathVariable("group") String group
    ) {
        if (result.hasErrors()) {
            return result;
        }
        ResourceGroup resourceGroup = resourceGroupService.getGroup(oper.getName(), group);
        resourceLocale.setGroupId(resourceGroup.getId());
        resourceLocale.setGroup(group);
        return resourceLocaleService.add(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    @PreAuthorize("hasRole('i18n')")
    public Object save(
            @RequestBody @Valid ResourceLocale resourceLocale,
            BindingResult result,
            Authentication oper,
            @PathVariable("group") String group,
            @PathVariable(value = "id", required = false) String id
    ) {
        if (result.hasErrors()) {
            return result;
        }
        ResourceGroup resourceGroup = resourceGroupService.getGroup(oper.getName(), group);
        resourceLocale.setGroupId(resourceGroup.getId());
        return resourceLocaleService.save(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('i18n')")
    public Object delete(@PathVariable("id") Integer id) {
        return resourceLocaleService.delete(id);
    }
}