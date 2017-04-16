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
import win.doyto.web.service.AbstractController;
import win.doyto.web.service.IMapper;

/**
 * ResourceLocaleController
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@RestController
@RequestMapping("/api/resource/{group}/locale")
public class ResourceLocaleController extends AbstractController<ResourceLocale> {
    @Resource
    private ResourceLocaleService resourceLocaleService;
    @Resource
    private ResourceGroupService resourceGroupService;

    @Override
    protected IMapper<ResourceLocale> getIMapper() {
        return resourceLocaleService.getIMapper();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object query(ResourceLocale resourceLocale, @PathVariable("group") String group) {
        ResourceGroup resourceGroup = resourceGroupService.getGroup(group);
        resourceLocale.setGroupId(resourceGroup.getId());
        return resourceLocaleService.query(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id) {
        return resourceLocaleService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody @Valid ResourceLocale resourceLocale, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return resourceLocaleService.add(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Object save(@RequestBody @Valid ResourceLocale resourceLocale, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return resourceLocaleService.save(resourceLocale);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        ResourceLocale deleted = resourceLocaleService.delete(id);
        return deleted != null ? id : null;
    }
}