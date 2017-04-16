package win.doyto.i18n.service;

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