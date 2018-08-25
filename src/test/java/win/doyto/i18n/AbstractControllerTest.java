package win.doyto.i18n;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * AbstractControllerTest
 *
 * @author f0rb on 2018-08-15.
 */
public class AbstractControllerTest {
    public static final String EXPECTED_TRUE = "true";
    public static final String $_SUCCESS = "$.success";
    public static final String $_DATA = "$.data";
    public static final String $_DATA_TOTAL = "$.data.total";
    protected static MockHttpSession mockHttpSession;
    protected MockMvc mockMvc;

    protected ResultActions performAndExpectSuccess(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                      .andDo(print())
                      .andExpect(MockMvcResultMatchers.status().isOk())
                      .andExpect(MockMvcResultMatchers.jsonPath($_SUCCESS).value(EXPECTED_TRUE));
    }

    protected ResultActions performAndExpectFail(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                      .andDo(print())
                      .andExpect(MockMvcResultMatchers.status().isOk())
                      .andExpect(MockMvcResultMatchers.jsonPath($_SUCCESS).value("false"));
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
        return mockMvc.perform(MockMvcRequestBuilders.fileUpload(url).file(file).session(mockHttpSession));
    }
}
