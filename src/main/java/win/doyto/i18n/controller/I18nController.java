package win.doyto.i18n.controller;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.model.Lang;
import win.doyto.i18n.service.I18nService;
import win.doyto.i18n.view.I18nXlsxView;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@Controller
@RequestMapping("/api/i18n")
public class I18nController {
    @Resource
    private I18nService i18nService;

    //importFromExcel
    //importFromJSON
    //importFromProperties

    @RequestMapping(value = "{group}", method = RequestMethod.GET)
    @ResponseBody
    public Object exportAll(@PathVariable("group") String group) {
        i18nService.checkGroup(group);
        List data = i18nService.query(group);
        return data;
    }

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public View exportAllToExcel(Model model, @PathVariable("group") String group) {
        i18nService.checkGroup(group);
        List data = i18nService.query(group);
        model.addAttribute("data", data);
        return new I18nXlsxView();
    }

    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.GET)
    @ResponseBody
    public Object exportByLocale(ModelAndView mav, @PathVariable("group") String group,
                                 @PathVariable("locale") String locale,
                                 @PathVariable(value = "format", required = false) String format) {
        i18nService.checkGroupAndLocale(group, locale);
        List<Lang> data = i18nService.queryWithDefaults(group, locale);

        return data;
    }

    /**
     * 保存翻译文本
     *
     */
    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.POST)
    @ResponseBody
    public Object saveText(ModelAndView mav,
                           @PathVariable("group") String group,
                           @PathVariable("locale") String locale,
                           @RequestBody Map<String, String> map
    ) {
        i18nService.checkGroupAndLocale(group, locale);

        List<Lang> data = i18nService.saveTranslation(group, locale, map);

        return data;
    }

    @ExceptionHandler({RestNotFoundException.class})
    public void handleNotFound(RestNotFoundException e) {
        throw e;
    }
}
