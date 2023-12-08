package win.doyto.i18n.module.i18n;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.i18n.module.locale.LocaleApi;
import win.doyto.i18n.module.locale.LocaleQuery;
import win.doyto.query.web.response.JsonBody;

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
    private LocaleApi localeApi;

    @GetMapping("{username}/{group}/locale")
    public Object query(LocaleQuery localeQuery) {
        return localeApi.query(localeQuery);
    }

    @GetMapping("{user}/{group}/{locale}.json")
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
        parent.computeIfAbsent(param, k -> new JSONObject());
        nestedJson(parent.getJSONObject(param), params, deep + 1, arg);
    }

}
