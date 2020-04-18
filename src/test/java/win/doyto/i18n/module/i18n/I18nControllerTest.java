package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
public class I18nControllerTest extends I18nAppTest {

    @Test
    public void testPage() throws Exception {
        performGetAndExpectSuccess("/api/i18n/i18n", "pageNumber=2", "pageSize=5")
                .andExpect(jsonPath($_DATA_TOTAL).value(12))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list.length()").value(2));
    }

    @Test
    public void autoTranslate() throws Exception {
        performPostEmptyAndExpectSuccess("/api/i18n/i18n/en_US/auto")
                .andExpect(jsonPath("$.data.length()").value(12));
    }

    @Test
    public void export() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = buildGet("/api/i18n/i18n.xlsx")
                .session(mockHttpSession)
                .header(HttpHeaders.USER_AGENT, "AppleWebKit/537.36 (KHTML, like Gecko)");
        mockMvc.perform(requestBuilder)
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

    }

}