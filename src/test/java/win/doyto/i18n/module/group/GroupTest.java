package win.doyto.i18n.module.group;

import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * GroupTest
 *
 * @author f0rb on 2020-02-18
 */
public class GroupTest extends I18nAppTest {

    @Test
    void create() throws Exception {
        performPostJsonAndExpectSuccess("/api/group/", "{\"name\":\"test2\",\"label\":\"test2\"}")
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
        ;
    }
}
