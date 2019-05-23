package win.doyto.i18n.module.locale;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.doyto.query.mybatis.AbstractMyBatisService;

/**
 * ResourceLocaleService
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@Service
class LocaleService
        extends AbstractMyBatisService<LocaleEntity, Integer, LocaleQuery> {

    public LocaleService(LocaleMapper localeMapper) {
        super(localeMapper);
    }

    public LocaleEntity getByGroupAndLocale(String group, String locale) {
        return get(LocaleQuery.builder().group(group).locale(locale).build());
    }

}
