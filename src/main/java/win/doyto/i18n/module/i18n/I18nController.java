package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.module.group.GroupApi;
import win.doyto.i18n.module.group.GroupResponse;
import win.doyto.i18n.module.locale.LocaleRequest;
import win.doyto.query.core.PageList;

import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping("/api/i18n")
@PreAuthorize("hasRole('i18n')")
public class I18nController {

    @Resource
    private I18nService i18nService;

    //importFromExcel
    //importFromJSON
    //importFromProperties

    @RequestMapping(value = "{group}", method = RequestMethod.GET)
    public Object exportAll(@CurrentUser String username, I18nQuery i18nQuery) {
        i18nQuery.setUser(username);
        PageList data = i18nService.page(i18nQuery);
        log.info("导出数据: {}条", data.getTotal());
        return data;
    }

    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.GET)
    public Object exportByLocale(
            @CurrentUser String username,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale) {

        return i18nService.queryWithDefaults(username, group, locale);
    }

    /**
     * 保存翻译文本
     */
    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.POST)
    public Object saveText(
            @CurrentUser String user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale,
            @RequestBody Map<String, String> map
    ) {
        i18nService.saveTranslation(user, group, locale, map);
        return exportByLocale(user, group, locale);
    }

    /**
     * 保存翻译文本
     */
    @RequestMapping(value = "{group}/{locale}/auto", method = RequestMethod.POST)
    public Object autoTranslate(
            @CurrentUser String user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale
    ) {
        i18nService.autoTranslate(user, group, locale);
        return exportByLocale(user, group, locale);
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public void createGroup(
        @CurrentUser String username,
        @RequestBody GroupLocaleRequest request
    ) {
        i18nService.createGroup(username, request.getGroup(), request.getLabel(), request.getLocale());
    }

    @Resource
    GroupApi groupApi;

    @PostMapping("addLocale")
    public void add(
        @CurrentUser String username,
        @RequestBody @Valid LocaleRequest request
    ) {
        request.setUsername(username);
        GroupResponse groupResponse = groupApi.getGroup(username, request.getGroup());
        request.setGroupId(groupResponse.getId());
        i18nService.addLocale(request);
    }

}
