package win.doyto.i18n.module.i18n;

import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * I18nOpenControllerTest
 *
 * @author f0rb on 2020-04-18
 */
class I18nOpenControllerTest extends I18nAppTest {

    @Test
    void query() throws Exception {
        performGetAndExpectSuccess("/openapi/i18n/i18n/locale", "pageSize=5")
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(3));
    }


    @Test
    void exportToJsonByLocale() throws Exception {
        performGetAndExpectSuccess("/openapi/i18n/i18n/zh_CN.json", "pageSize=5")
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.保存").value("保存"));
    }
}