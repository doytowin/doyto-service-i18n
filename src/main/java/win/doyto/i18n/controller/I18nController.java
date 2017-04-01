package win.doyto.i18n.controller;

import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import win.doyto.i18n.exception.BusinessNotFoundException;
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
        List<Lang> data = i18nService.query(group, locale);

        return data;
    }

    @RequestMapping(value = "{group}/{locale}.json", method = RequestMethod.GET)
    @ResponseBody
    public Object exportToJsonByLocale(@PathVariable("group") String group,
                                 @PathVariable("locale") String locale) {
        i18nService.checkGroupAndLocale(group, locale);
        List<Lang> langList = i18nService.query(group, locale);

        JSONObject root = new JSONObject();

        for (Lang lang : langList) {
            nestedJson(root, lang.getLabel().split("\\."), 0, lang.getValue());
        }

        return root;
    }

    /**
     * 处理嵌套的JSON参数
     *
     * @param parent 上级json
     * @param params 参数名
     * @param deep   递归深度
     * @param arg    待处理的参数
     */
    private static void nestedJson(JSONObject parent, String[] params, int deep, Object arg) {
        String param = params[deep];
        if (deep == params.length - 1) {
            parent.put(params[deep], arg);
            return;
        }
        if (!parent.containsKey(param)) {
            parent.put(param, new JSONObject());
        }
        nestedJson(parent.getJSONObject(param), params, deep + 1, arg);
    }

    @ExceptionHandler({BusinessNotFoundException.class})
    public void handleNotFound(BusinessNotFoundException e) {
        throw e;
    }
}
