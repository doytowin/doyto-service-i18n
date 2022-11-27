package win.doyto.i18n.module.i18n;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.query.core.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static win.doyto.i18n.common.TestConstant.DEFAULT_GROUP;
import static win.doyto.i18n.common.TestConstant.DEFAULT_USER;

/**
 * GroupServiceTest
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
 class I18nServiceTest extends I18nAppTest {
    @Resource
    private I18nService i18nService;

    @Test
     void queryLanguageByLocale() throws Exception {
        List<I18nView> ret = i18nService.queryWithDefault(DEFAULT_USER, DEFAULT_GROUP, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
        assertThat(ret).hasSize(12);
    }

    @Test
     void queryAllLanguage() throws Exception {
        List<Map> ret = i18nService.query(DEFAULT_USER, DEFAULT_GROUP);
        assertThat(ret).hasSize(12);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
     void pageAllLanguage() throws Exception {
        I18nQuery query = I18nQuery.builder().user(DEFAULT_USER).group(DEFAULT_GROUP).pageNumber(2).build();
        PageList<Map> ret = i18nService.paging(query);
        assertThat(ret.getTotal()).isEqualTo(12);
        assertThat(ret.getList()).hasSize(2);

        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
     void testSaveTranslation() throws Exception {
        String user = DEFAULT_USER;
        String group = DEFAULT_GROUP;

        Map<String, String> langMap;
        List<I18nView> ret;

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

        for (I18nView i18nView : ret) {
            if (langMap.containsKey(i18nView.getLabel())) {
                assertEquals(i18nView.getValue(), langMap.get(i18nView.getLabel()));
            }
        }

    }


}