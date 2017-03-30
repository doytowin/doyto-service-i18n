package win.doyto.i18n.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import win.doyto.i18n.model.Group;
import win.doyto.i18n.service.GroupService;

/**
 * GroupController
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Resource
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Object query(Group group) {
        return groupService.query(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Integer id) {
        return groupService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Object add(@RequestBody @Valid Group group, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.add(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.POST)
    public Object save(@RequestBody @Valid Group group, BindingResult result) {
        if (result.hasErrors()) {
            return result;
        }
        return groupService.save(group);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        Group deleted = groupService.delete(id);
        return deleted != null ? id : null;
    }

}