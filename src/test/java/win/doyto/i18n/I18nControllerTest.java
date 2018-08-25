package win.doyto.i18n;

import java.util.Arrays;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import win.doyto.i18n.controller.ResourceGroupControllerTest;
import win.doyto.i18n.module.group.ResourceGroupService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = I18nApp.class)
@ActiveProfiles({"test"})
public class I18nControllerTest extends AbstractControllerTest {
    @Resource
    protected WebApplicationContext wac;

    @Resource
    ResourceGroupService resourceGroupService;

    @PostConstruct
    public void init() throws Exception {
        resourceGroupService.save(ResourceGroupControllerTest.INIT_LIST);
    }

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        if (mockHttpSession == null) {
            mockHttpSession = (MockHttpSession) mockMvc.perform(buildGet("/actuator/info")).andReturn().getRequest().getSession();
        }

        UsernamePasswordAuthenticationToken authentication
                = new UsernamePasswordAuthenticationToken("i18n", "i18n", Arrays.asList(new SimpleGrantedAuthority("ROLE_i18n")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /*======== GROUP =======*/

    @Test
    public void testPage() throws Exception {

        performGetAndExpectSuccess("/api/group", "pageNumber=1", "pageSize=5")
        .andExpect(jsonPath($_DATA_TOTAL).value(ResourceGroupControllerTest.INIT_I18N_SIZE))
        .andExpect(jsonPath("$.data.list").isArray())
        .andExpect(jsonPath("$.data.list.length()").value(3));

    }
    /*======== GROUP =======*/

}