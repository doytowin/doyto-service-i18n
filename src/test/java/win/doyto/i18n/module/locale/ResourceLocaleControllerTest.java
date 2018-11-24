package win.doyto.i18n.module.locale;

import org.junit.Test;
import win.doyto.i18n.common.TestConstant;
import win.doyto.i18n.mapper.MockResourceLocaleMapper;
import win.doyto.i18n.module.locale.ResourceLocaleController;
import win.doyto.i18n.module.locale.ResourceLocaleQuery;
import win.doyto.i18n.module.locale.ResourceLocaleService;

/**
 * ResourceLocaleControllerTest
 *
 * @author f0rb on 2018-08-27.
 */
public class ResourceLocaleControllerTest {
    private final MockResourceLocaleMapper resourceLocaleMapper = new MockResourceLocaleMapper();
    private final ResourceLocaleService resourceLocaleService = new ResourceLocaleService(resourceLocaleMapper);
    private final ResourceLocaleController resourceLocaleController = new ResourceLocaleController(resourceLocaleService);

    @Test
    public void page() {
        resourceLocaleController.page(TestConstant.DEFAULT_USER, ResourceLocaleQuery.builder().build());
    }

    @Test
    public void save() {
        ResourceLocaleController.UpdateLocaleRequest updateLocaleRequest = new ResourceLocaleController.UpdateLocaleRequest();
        resourceLocaleController.save(TestConstant.DEFAULT_USER, updateLocaleRequest, 1);
    }

}