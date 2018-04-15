package win.doyto.i18n.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.i18n.model.I18n;
import win.doyto.i18n.model.Lang;
import win.doyto.web.PageResponse;

import static org.junit.Assert.assertEquals;
import static win.doyto.i18n.common.Constant.DEFAULT_GROUP;
import static win.doyto.i18n.common.Constant.DEFAULT_USER;

/**
 * GroupServiceTest
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
public class I18nServiceTest extends I18nAppTest {
    @Resource
    private I18nService i18nService;

    @Test
    public void queryLanguageByLocale() throws Exception {
        List<Lang> ret = i18nService.query(DEFAULT_USER, DEFAULT_GROUP, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void queryAllLanguage() throws Exception {
        List ret = i18nService.query(DEFAULT_USER, DEFAULT_GROUP);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void pageAllLanguage() throws Exception {
        I18n query = new I18n();
        query.setUser(DEFAULT_USER);
        query.setGroup(DEFAULT_GROUP);
        query.setLimit(10);
        query.setPage(2);
        PageResponse ret = i18nService.query(query);
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
        List<Lang> ret;

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

        for (Lang lang : ret) {
            if (langMap.containsKey(lang.getLabel())) {
                assertEquals(lang.getValue(), langMap.get(lang.getLabel()));
            }
        }

    }


}