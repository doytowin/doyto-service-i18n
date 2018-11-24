package win.doyto.i18n.module.locale;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import win.doyto.query.CollectionUtils;
import win.doyto.query.IDataService;
import win.doyto.query.mybatis.AbstractMyBatisDataService;

/**
 * ResourceLocaleService
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@Service
public class ResourceLocaleService
        extends AbstractMyBatisDataService<ResourceLocale, Integer, ResourceLocaleQuery>
        implements IDataService<ResourceLocale, Integer, ResourceLocaleQuery> {

    @Getter
    private ResourceLocaleMapper mapper;

    public ResourceLocaleService(ResourceLocaleMapper resourceLocaleMapper) {
        this.mapper = resourceLocaleMapper;
    }

    public ResourceLocale getByGroupAndLocale(String group, String locale) {
        return CollectionUtils.getFirst(query(ResourceLocaleQuery.builder().group(group).locale(locale).build()));
    }

}
