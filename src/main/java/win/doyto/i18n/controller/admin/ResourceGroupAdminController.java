package win.doyto.i18n.controller.admin;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping("/admin/resource-group")
@PreAuthorize("hasAnyRole('admin', 'admin:i18n')")
public class ResourceGroupAdminController {
    @Resource
    private ResourceGroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Object page(ResourceGroupQuery query) {
        return groupService.page(query);
    }

}