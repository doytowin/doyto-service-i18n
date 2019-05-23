package win.doyto.i18n.module.i18n;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.i18n.module.i18n.I18nQuery;
import win.doyto.i18n.module.i18n.LangView;
import win.doyto.i18n.module.i18n.I18nService;
import win.doyto.query.core.PageList;

import static org.junit.Assert.assertEquals;
import static win.doyto.i18n.common.TestConstant.DEFAULT_GROUP;
import static win.doyto.i18n.common.TestConstant.DEFAULT_USER;

/**
 * GroupServiceTest
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
@Ignore
public class I18nServiceTest extends I18nAppTest {
    @Resource
    private I18nService i18nService;

    @Test
    public void queryLanguageByLocale() throws Exception {
        List<LangView> ret = i18nService.query(DEFAULT_USER, DEFAULT_GROUP, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void queryAllLanguage() throws Exception {
        List ret = i18nService.query(DEFAULT_USER, DEFAULT_GROUP);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void pageAllLanguage() throws Exception {
        I18nQuery query = I18nQuery.builder().user(DEFAULT_USER).group(DEFAULT_GROUP).build();
        query.setPageNumber(2);
        PageList ret = i18nService.page(query);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    //@org.springframework.test.annotation.Commit
    public void testSaveTranslation() throws Exception {
        String user = DEFAULT_USER;
        String group = DEFAULT_GROUP;
        String locale;

        locale = i18nService.addLocaleOnGroup(user, group, "zh_CN");
        log.info("多语言分组[{}]添加语种: {}", group, locale);
        locale = i18nService.addLocaleOnGroup(user, group, "en_US");
        log.info("多语言分组[{}]添加语种: {}", group, locale);

        Map<String, String> langMap;
        List<LangView> ret;

        langMap = new HashMap<>();
        langMap.put("test_msg", "测试");
        langMap.put("user", "用户");
        i18nService.saveTranslation(user, group, "zh_CN", langMap);
        ret = i18nService.queryWithDefaults(user, group, "zh_CN");
        log.info("test_zh_CN: \n{}", JSON.toJSONString(ret, true));

        langMap = new HashMap<>();
        langMap.put("test_msg", "test message");
        langMap.put("user", "user");
        i18nService.saveTranslation(user, group, "en_US", langMap);
        ret = i18nService.queryWithDefaults(user, group, "en_US");
        log.info("test_en_US: \n{}", JSON.toJSONString(ret, true));

        for (LangView langView : ret) {
            if (langMap.containsKey(langView.getLabel())) {
                assertEquals(langView.getValue(), langMap.get(langView.getLabel()));
            }
        }

    }


}