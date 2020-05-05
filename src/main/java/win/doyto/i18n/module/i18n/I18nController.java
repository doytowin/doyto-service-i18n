package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.i18n.module.baidu.BaiduTranResponse;
import win.doyto.i18n.module.baidu.BaiduTranService;
import win.doyto.i18n.module.locale.LocaleApi;
import win.doyto.query.web.response.JsonBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

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

    @Resource
    private BaiduTranService baiduTranService;

    @Resource
    private LocaleApi localeApi;

    //importFromExcel
    //importFromJSON
    //importFromProperties

    @GetMapping("{group}")
    public Object exportAll(Authentication user, I18nQuery i18nQuery) {
        i18nQuery.setUser(user.getName());
        return i18nService.paging(i18nQuery);
    }

    @GetMapping("{group}/{locale}")
    public Object exportByLocale(
            Authentication user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale) {

        return i18nService.queryWithDefaults(user.getName(), group, locale);
    }

    /**
     * 保存翻译文本
     */
    @PostMapping("{group}/{locale}")
    public Object saveText(
            Authentication user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale,
            @RequestBody Map<String, String> map
    ) {
        i18nService.saveTranslation(user.getName(), group, locale, map);
        return exportByLocale(user, group, locale);
    }

    /**
     * 保存翻译文本
     */
    @Transactional
    @PostMapping("{group}/{locale}/auto")
    public Object autoTranslate(
            Authentication user,
            @PathVariable("group") String group,
            @PathVariable("locale") String locale
    ) {
        String username = user.getName();
        i18nService.checkGroupAndLocale(username, group, locale);

        List<I18nView> i18nViewList = i18nService.query(username, group, locale);

        String to = localeApi.getBaiduLocale(username, group, locale);
        for (I18nView i18nView : i18nViewList) {
            i18nView.setUser(username);
            i18nView.setGroup(group);
            i18nView.setLocale(locale);
            if (StringUtils.isNotBlank(i18nView.getDefaults()) && StringUtils.isEmpty(i18nView.getValue())) {
                BaiduTranResponse baiduTranResponse = baiduTranService.translate(i18nView.getDefaults(), "auto", to);
                if (baiduTranResponse.success()) {
                    if (baiduTranResponse.getTransResult() != null) {
                        i18nView.setValue(baiduTranResponse.getTransResult()[0].getDst());
                    }
                } else {
                    log.error("翻译接口调用失败: {}[{}]", baiduTranResponse.getErrorMsg(), baiduTranResponse.getErrorCode());
                }
            }
        }
        i18nService.saveTranslation(i18nViewList);

        return exportByLocale(user, group, locale);
    }

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @GetMapping("{group}.xlsx")
    public ModelAndView exportAllToExcel(Authentication user, @PathVariable("group") String group) {
        HashMap<String, Object> map = new HashMap<>();
        List<?> data = i18nService.query(user.getName(), group);
        map.put("data", data);
        map.put("group", group);
        return new ModelAndView("i18nXlsxView", map);
    }
}
