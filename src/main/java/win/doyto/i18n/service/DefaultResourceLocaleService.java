package win.doyto.i18n.service;

import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.mapper.ResourceLocaleMapper;
import win.doyto.i18n.model.ResourceLocale;
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
    public ResourceLocale add(ResourceLocale resourceLocale) {
        resourceLocaleMapper.insert(resourceLocale);
        i18nService.addLocaleOnGroup(resourceLocale.getGroup(), resourceLocale.getLocale());
        return resourceLocale;
    }

    @Override
    public ResourceLocale save(ResourceLocale resourceLocale) {
        ResourceLocale origin = resourceLocaleMapper.get(resourceLocale.getId());
        if (origin == null) {
            return null;
        }
        //origin.setGroup_id(resourceLocale.getGroup_id());
        //origin.setLocale(resourceLocale.getLocale());
        //origin.setStatu(resourceLocale.getStatu());

        //origin.setUpdateUserId(AppContext.getLoginUserId());
        //origin.setUpdateTime(new Date());
        resourceLocaleMapper.update(origin);
        return origin;
    }
}