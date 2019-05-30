package win.doyto.i18n.module.locale;

import org.junit.Test;
import win.doyto.i18n.common.TestConstant;

/**
 * LocaleEntityControllerTest
 *
 * @author f0rb on 2018-08-27.
 */
public class LocaleEntityControllerTest {
    private final LocaleController resourceLocaleController = new LocaleController();

    @Test
    public void page() {
        resourceLocaleController.page(TestConstant.DEFAULT_USER, LocaleQuery.builder().build());
    }


}