package win.doyto.i18n.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.i18n.model.Lang;
import win.doyto.i18n.service.GroupService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static win.doyto.i18n.common.Constant.SYSTEM_GROUP;

/**
 * GroupServiceTest
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
public class GroupServiceTest extends I18nAppTest {
    @Resource
    private GroupService groupService;

    @Test
    public void queryLanguageByLocale() throws Exception {
        List<Lang> ret = groupService.query(SYSTEM_GROUP, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void queryAllLanguage() throws Exception {
        List ret = groupService.query(SYSTEM_GROUP);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }


    @Test
    //@org.springframework.test.annotation.Commit
    public void testSaveTranslation() throws Exception {
        String group = "i18n";
        String locale;

        locale = groupService.addLocaleOnGroup(group, "zh_CN");
        log.info("多语言分组[{}]添加语种: {}", group, locale);
        locale = groupService.addLocaleOnGroup(group, "en_US");
        log.info("多语言分组[{}]添加语种: {}", group, locale);

        Map<String, String> langMap;
        List<Lang> ret;

        langMap = new HashMap<>();
        langMap.put("test_msg", "测试");
        langMap.put("user", "用户");
        ret = groupService.saveTranslation(group, "zh_CN", langMap);
        log.info("test_zh_CN: \n{}", JSON.toJSONString(ret, true));

        langMap = new HashMap<>();
        langMap.put("test_msg", "test message");
        langMap.put("user", "user");
        ret = groupService.saveTranslation(group, "en_US", langMap);
        log.info("test_en_US: \n{}", JSON.toJSONString(ret, true));

        for (Lang lang : ret) {
            assertTrue(langMap.containsKey(lang.getLabel()));
            assertEquals(lang.getValue(), langMap.get(lang.getLabel()));
        }

    }


}