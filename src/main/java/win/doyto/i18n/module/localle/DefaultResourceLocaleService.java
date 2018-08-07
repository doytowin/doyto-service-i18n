package win.doyto.i18n.module.localle;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.module.group.ResourceGroupMapper;
import win.doyto.i18n.module.group.ResourceGroup;
import win.doyto.i18n.module.i18n.I18nService;
import win.doyto.web.RestError;
import win.doyto.web.service.AbstractService;

/**
 * ResourceLocaleDefaultService
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@Service
public class DefaultResourceLocaleService extends AbstractService<ResourceLocale> implements ResourceLocaleService {
    @Resource
    private ResourceLocaleMapper resourceLocaleMapper;

    @Resource
    private ResourceGroupMapper resourceGroupMapper;

    @Resource
    private I18nService i18nService;

    @Override
    public ResourceLocaleMapper getIMapper() {
        return resourceLocaleMapper;
    }

    @Override
    public ResourceLocale getByGroupAndLocale(Integer groupId, String locale) {
        ResourceLocale query = new ResourceLocale();
        query.setGroupId(groupId);
        query.setLocale(locale);
        List<ResourceLocale> list = resourceLocaleMapper.query(query);
        ResourceLocale ret = null;
        if (!list.isEmpty()) {
            ret = list.get(0);

            if (list.size() > 1) {
                log.warn("发现多个ResourceLocale: groupId={}, locale={}", groupId, locale);
            }
        }
        return ret;
    }

    @Override
    @Transactional
    public RestError add(ResourceLocale resourceLocale) {
        resourceLocaleMapper.insert(resourceLocale);
        ResourceGroup group = resourceGroupMapper.get(resourceLocale.getGroupId());
        i18nService.addLocaleOnGroup(group.getOwner(), group.getName(), resourceLocale.getLocale());
        return RestError.create(resourceLocale);
    }

    @Override
    public ResourceLocale save(ResourceLocale resourceLocale) {
        ResourceLocale origin = resourceLocaleMapper.get(resourceLocale.getId());
        if (origin == null) {
            return null;
        }
        origin.setLocale(resourceLocale.getLocale());
        origin.setLanguage(resourceLocale.getLanguage());
        origin.setBaiduTranLang(resourceLocale.getBaiduTranLang());

        //origin.setUpdateUserId(AppContext.getLoginUserId());
        origin.setUpdateTime(new Date());
        resourceLocaleMapper.update(origin);
        return origin;
    }
}