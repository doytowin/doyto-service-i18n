package win.doyto.i18n;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * I18nAppTest
 *
 * @author f0rb on 2017-03-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = I18nApp.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
@Rollback
@ActiveProfiles("test")
@Transactional
public abstract class I18nAppTest {

}