package win.doyto.i18n.mapper;

import win.doyto.query.mock.AbstractMockMapper;
import win.doyto.i18n.module.locale.ResourceLocale;
import win.doyto.i18n.module.locale.ResourceLocaleMapper;
import win.doyto.i18n.module.locale.ResourceLocaleQuery;

/**
 * MockResourceLocaleMapper
 *
 * @author f0rb on 2018-08-29.
 */
public class MockResourceLocaleMapper extends AbstractMockMapper<ResourceLocale, Integer, ResourceLocaleQuery> implements ResourceLocaleMapper {

    public MockResourceLocaleMapper() {
        super(ResourceLocale.TABLE);
    }

}
