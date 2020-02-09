package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.response.JsonBody;
import win.doyto.i18n.module.locale.LocaleRequest;
import win.doyto.query.service.PageList;

import java.util.HashMap;
import java.util.List;
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
        PageList<?> data = i18nService.page(i18nQuery);
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
        @RequestBody @Valid GroupLocaleRequest request
    ) {
        i18nService.createGroup(request.getUsername(), request.getGroup(), request.getLabel());
    }

    @PostMapping("addLocale")
    public void add(
        @RequestBody @Valid LocaleRequest request
    ) {
        i18nService.addLocale(request);
    }

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public ModelAndView exportAllToExcel(Authentication oper, @PathVariable("group") String group) {
        HashMap<String, Object> map = new HashMap<>();
        List data = i18nService.query(oper.getName(), group);
        map.put("data", data);
        map.put("group", group);
        return new ModelAndView("i18nXlsxView", map);
    }
}
