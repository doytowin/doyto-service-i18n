package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.RequestBuilder;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
public class I18nControllerTest extends I18nAppTest {

    @BeforeEach
    public void setUp() throws Exception {
        if (mockHttpSession == null) {
            RequestBuilder builder = post("/login")
                    .param("username", "i18n")
                    .param("password", "i18n");
            mockHttpSession = (MockHttpSession) mockMvc.perform(builder).andReturn().getRequest().getSession();
        }

    }

    /*======== GROUP =======*/

    @Test
    public void testPage() throws Exception {
        performGetAndExpectSuccess("/api/i18n/i18n", "pageNumber=1", "pageSize=5")
                .andExpect(jsonPath($_DATA_TOTAL).value(8))
                .andExpect(jsonPath("$.data.list").isArray())
                .andExpect(jsonPath("$.data.list.length()").value(3));

    }
    /*======== GROUP =======*/

}