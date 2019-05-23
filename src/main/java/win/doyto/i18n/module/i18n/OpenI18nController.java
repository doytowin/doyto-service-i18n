package win.doyto.i18n.module.i18n;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.module.locale.LocaleQuery;
import win.doyto.i18n.module.locale.LocaleApi;

import java.util.HashMap;
import java.util.List;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@RestController
@RequestMapping("/openapi")
@JsonBody
public class OpenI18nController {
    private I18nService i18nService;

    private LocaleApi localeApi;

    public OpenI18nController(I18nService i18nService, LocaleApi localeApi) {
        this.i18nService = i18nService;
        this.localeApi = localeApi;
    }

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @GetMapping("{user}/{group}.xlsx")
    public ModelAndView exportAllToExcel(@PathVariable("user") String user, @PathVariable("group") String group) {
        List data = i18nService.query(user, group);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("group", group);
        return new ModelAndView(new I18nXlsxView(), map);
    }

    @RequestMapping(value = "{user}/{group}/locale", method = RequestMethod.GET)
    public Object query(LocaleQuery localeQuery, @PathVariable("user") String user, @PathVariable("group") String group) {
        localeQuery.setGroup(group);
        return localeApi.query(localeQuery);
    }

    @RequestMapping(value = "{user}/{group}/{locale}.json", method = RequestMethod.GET)
    public Object exportToJsonByLocale(
            @PathVariable("user") String user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale) {

        i18nService.checkGroupAndLocale(user, group, locale);
        List<LangView> langViewList = i18nService.query(user, group, locale);

        JSONObject root = new JSONObject();

        for (LangView langView : langViewList) {
            nestedJson(root, langView.getLabel().split("\\."), 0, langView.getValue());
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

}
