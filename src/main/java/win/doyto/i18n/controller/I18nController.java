package win.doyto.i18n.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import win.doyto.i18n.module.i18n.I18n;
import win.doyto.i18n.module.i18n.Lang;
import win.doyto.i18n.module.i18n.I18nService;
import win.doyto.i18n.view.I18nXlsxView;
import win.doyto.web.PageResponse;
import win.doyto.web.spring.RestBody;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@RestBody
@Controller
@RequestMapping("/api/i18n")
@PreAuthorize("hasRole('i18n')")
public class I18nController {

    @Resource
    private I18nService i18nService;

    //importFromExcel
    //importFromJSON
    //importFromProperties

    @RequestMapping(value = "{group}", method = RequestMethod.GET)
    @ResponseBody
    public Object exportAll(Authentication oper, I18n i18n) {
        i18n.setUser(oper.getName());
        PageResponse data = i18nService.query(i18n);
        log.info("导出数据: {}条", data.getTotal());
        return data;
    }

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public View exportAllToExcel(Authentication oper, Model model, @PathVariable("group") String group) {
        List data = i18nService.query(oper.getName(), group);
        model.addAttribute("data", data);
        return new I18nXlsxView();
    }

    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.GET)
    @ResponseBody
    public Object exportByLocale(
            ModelAndView mav, @PathVariable("group") String group,
            Authentication oper,
            @PathVariable("locale") String locale,
            @PathVariable(value = "format", required = false) String format) {
        List<Lang> data = i18nService.queryWithDefaults(oper.getName(), group, locale);
        return data;
    }

    /**
     * 保存翻译文本
     */
    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.POST)
    @ResponseBody
    public Object saveText(
            ModelAndView mav,
            Authentication oper,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale,
            @RequestBody Map<String, String> map
    ) {
        String user = oper.getName();
        i18nService.saveTranslation(user, group, locale, map);
        List<Lang> data = i18nService.queryWithDefaults(user, group, locale);

        return data;
    }

    /**
     * 保存翻译文本
     */
    @RequestMapping(value = "{group}/{locale}/auto", method = RequestMethod.POST)
    @ResponseBody
    public Object autoTranslate(
            ModelAndView mav,
            Authentication oper,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale
    ) {
        String user = oper.getName();
        i18nService.autoTranslate(user, group, locale);
        List<Lang> data = i18nService.queryWithDefaults(user, group, locale);
        return data;
    }

}
