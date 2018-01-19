package win.doyto.i18n.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.model.ResourceGroup;
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
public class ResourceGroupController {
    @Resource
    private ResourceGroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Object query(ResourceGroup group) {
        return groupService.query(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id) {
        return groupService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody @Valid ResourceGroup group, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.add(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Object save(@RequestBody @Valid ResourceGroup group, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.save(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        ResourceGroup deleted = groupService.delete(id);
        return deleted != null ? id : null;
    }

}