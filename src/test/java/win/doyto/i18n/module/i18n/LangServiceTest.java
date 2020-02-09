package win.doyto.i18n.module.i18n;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static win.doyto.i18n.common.TestConstant.DEFAULT_GROUP;
import static win.doyto.i18n.common.TestConstant.DEFAULT_USER;

/**
 * I18nLangServiceTest
 *
 * @author f0rb on 2019-06-15
 */
@Slf4j
public class LangServiceTest {

    private LangService langService = new LangService();

    @Test
    public void langByGroupAndLocale() throws Exception {
        List ret = langService.langByGroupAndLocale(DEFAULT_USER, DEFAULT_GROUP, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void saveTranslation () throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("添加", "添加");
        map.put("修改", "修改");
        //int ret = i18nMapper.saveTranslation(DEFAULT_USER, group, "zh_CN", map);
        //log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

}