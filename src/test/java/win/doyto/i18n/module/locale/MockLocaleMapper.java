package win.doyto.i18n.module.locale;

import win.doyto.query.mybatis.AbstractMockMapper;

/**
 * MockLocaleMapper
 *
 * @author f0rb on 2018-08-29.
 */
public class MockLocaleMapper extends AbstractMockMapper<LocaleEntity, Integer, LocaleQuery> implements LocaleMapper {

    public MockLocaleMapper() {
        super(LocaleEntity.TABLE);
    }

}
