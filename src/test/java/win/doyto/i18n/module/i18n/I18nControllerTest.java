package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
public class I18nControllerTest extends I18nAppTest {

    @Test
    public void testPage() throws Exception {
        performGetAndExpectSuccess("/api/i18n/i18n", "pageNumber=2", "pageSize=5")
                .andExpect(jsonPath($_DATA_TOTAL).value(12))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list.length()").value(2));

    }

}