package win.doyto.i18n.module.locale;

import java.util.Date;
import javax.annotation.Resource;
import javax.validation.Valid;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.i18n.module.group.ResourceGroupService;
import win.doyto.web.RestEnum;
import win.doyto.web.spring.RestBody;

/**
 * ResourceLocaleController
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@RestBody
@RestController
@RequestMapping("/api/locale")
@PreAuthorize("hasAnyRole('i18n')")
public class ResourceLocaleController {
    private ResourceLocaleService resourceLocaleService;

    @Resource
    private ResourceGroupService resourceGroupService;

    public ResourceLocaleController(ResourceLocaleService resourceLocaleService) {
        this.resourceLocaleService = resourceLocaleService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object page(
            @CurrentUser String username,
            @RequestBody @Valid ResourceLocaleQuery resourceLocaleQuery
    ) {
        resourceLocaleQuery.setOwner(username);
        return resourceLocaleService.page(resourceLocaleQuery);
    }

    @RequestMapping(value = "{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public Object save(
            @CurrentUser String username,
            @RequestBody @Valid UpdateLocaleRequest resourceLocale,
            @PathVariable(value = "id", required = false) Integer id
    ) {
        //ResourceGroup resourceGroup = resourceGroupService.getGroup(oper.getName(), group);
        //resourceLocale.setGroupId(resourceGroup.getId());

        ResourceLocale origin = resourceLocaleService.get(id);
        if (origin == null) {
            return RestEnum.RecordNotFound.value();
        }
        if (!username.equals(origin.getOwner())) {
            return RestEnum.RecordNotFound.value();
        }
        origin.setLanguage(resourceLocale.getLanguage());
        origin.setBaiduTranLang(resourceLocale.getBaiduTranLang());

        //origin.setUpdateUserId(AppContext.getLoginUserId());
        origin.setUpdateTime(new Date());
        return resourceLocaleService.save(origin);
    }

    @Data
    static class UpdateLocaleRequest {

        private String baiduTranLang;

        private String language;

    }

}