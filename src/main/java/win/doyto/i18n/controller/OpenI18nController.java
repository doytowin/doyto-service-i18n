package win.doyto.i18n.controller;

import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.model.Lang;
import win.doyto.i18n.service.I18nService;
import win.doyto.i18n.view.I18nXlsxView;
import win.doyto.web.spring.RestBody;

import static win.doyto.i18n.common.Constant.DEFAULT_USER;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@RestBody
@Controller
@RequestMapping("/openapi/i18n")
public class OpenI18nController {
    @Resource
    private I18nService i18nService;

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public View exportAllToExcel(Model model, @PathVariable("group") String group) {
        //i18nService.checkGroup(group);
        List data = i18nService.query(DEFAULT_USER, group);
        model.addAttribute("data", data);
        return new I18nXlsxView();
    }

    @RequestMapping(value = "{group}/{locale}.json", method = RequestMethod.GET)
    @ResponseBody
    public Object exportToJsonByLocale(@PathVariable("group") String group,
                                 @PathVariable("locale") String locale) {
        i18nService.checkGroupAndLocale(DEFAULT_USER, group, locale);
        List<Lang> langList = i18nService.query(DEFAULT_USER, group, locale);

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

    @ExceptionHandler({RestNotFoundException.class})
    public void handleNotFound(RestNotFoundException e) {
        throw e;
    }
}
