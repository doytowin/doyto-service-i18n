package win.doyto.i18n;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * I18nAppTest
 *
 * @author f0rb on 2017-03-29.
 */
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = I18nApp.class)
@Rollback
@Transactional
@ActiveProfiles("test")
public abstract class I18nAppTest {
    public static final String EXPECTED_TRUE = "true";
    public static final String $_SUCCESS = "$.success";
    public static final String $_DATA = "$.data";
    public static final String $_DATA_TOTAL = $_DATA + ".total";
    protected static MockHttpSession mockHttpSession;

    @Resource
    protected MockMvc mockMvc;

    protected ResultActions performAndExpectSuccess(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                      .andDo(print())
                      .andExpect(status().isOk())
                      .andExpect(jsonPath($_SUCCESS).value(EXPECTED_TRUE));
    }

    protected ResultActions performAndExpectFail(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                      .andDo(print())
                      .andExpect(status().isOk())
                      .andExpect(jsonPath($_SUCCESS).value("false"));
    }

    protected ResultActions performGetAndExpectSuccess(String url, String... args) throws Exception {
        return performAndExpectSuccess(buildGet(url, args).session(mockHttpSession));
    }

    protected MockHttpServletRequestBuilder buildGet(String url, String... args) {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
        for (String arg : args) {
            int idx = arg.indexOf('=');
            requestBuilder.param(arg.substring(0, idx), arg.substring(idx + 1));
        }
        return requestBuilder;
    }

    protected MockHttpServletRequestBuilder buildPostJson(String url, String json) {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(url);
        return json == null ? post : post.contentType(MediaType.APPLICATION_JSON).content(json);
    }

    protected MockHttpServletRequestBuilder buildPostJsonWithSession(String url, String json) {
        return buildPostJson(url, json).session(mockHttpSession);
    }

    protected ResultActions performPostJsonAndExpectSuccess(String url, String content) throws Exception {
        return performAndExpectSuccess(buildPostJsonWithSession(url, content));
    }

    protected ResultActions performPostEmptyAndExpectSuccess(String url) throws Exception {
        return performPostJsonAndExpectSuccess(url, null);
    }

    protected ResultActions performUploadFile(String url, MockMultipartFile file) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.multipart(url).file(file).session(mockHttpSession));
    }

}