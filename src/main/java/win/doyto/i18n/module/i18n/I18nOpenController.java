package win.doyto.i18n.module.i18n;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.common.web.response.JsonBody;
import win.doyto.i18n.module.locale.LocaleQuery;
import win.doyto.i18n.module.locale.LocaleService;

import java.util.HashMap;
import java.util.List;

/**
 * I18nOpenController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/openapi")
@JsonBody
public class I18nOpenController {

    private I18nService i18nService;
    private LocaleService localeService;

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @GetMapping("{user}/{group}.xlsx")
    public ModelAndView exportAllToExcel(@PathVariable("user") String user, @PathVariable("group") String group) {
        List<?> data = i18nService.query(user, group);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("group", group);
        return new ModelAndView(new I18nXlsxView(), map);
    }

    @RequestMapping(value = "{user}/{group}/locale", method = RequestMethod.GET)
    public Object query(LocaleQuery localeQuery, @PathVariable("user") String user, @PathVariable("group") String group) {
        localeQuery.setOwner(user);
        localeQuery.setGroupName(group);
        return localeService.list(localeQuery);
    }

    @RequestMapping(value = "{user}/{group}/{locale}.json", method = RequestMethod.GET)
    public Object exportToJsonByLocale(
            @PathVariable("user") String user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale) {

        i18nService.checkGroupAndLocale(user, group, locale);
        List<I18nView> i18nViewList = i18nService.queryWithDefault(user, group, locale);

        JSONObject root = new JSONObject();

        for (I18nView i18nView : i18nViewList) {
            nestedJson(root, i18nView.getLabel().split("\\."), 0, i18nView.getValue());
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
