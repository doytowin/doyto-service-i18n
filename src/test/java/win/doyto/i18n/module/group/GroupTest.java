package win.doyto.i18n.module.group;

import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * GroupTest
 *
 * @author f0rb on 2020-02-18
 */
public class GroupTest extends I18nAppTest {

    @Test
    void create() throws Exception {
        performGetAndExpectFail("/api/i18n/test2");
        performPostJsonAndExpectSuccess("/api/group/", "{\"name\":\"test2\",\"label\":\"测试2\"}");
        performGetAndExpectSuccess("/api/i18n/test2")
                .andExpect(jsonPath($_DATA_TOTAL).value(0));
    }

    @Test
    void fixUniqIndexConflict() throws Exception {
        performPostJsonAndExpectSuccess("/api/group/", "{\"name\":\"test3\",\"label\":\"test3\"}");
        performGetAndExpectSuccess("/api/i18n/test3")
                .andExpect(jsonPath($_DATA_TOTAL).value(0));
    }
}
