package win.doyto.i18n.service;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.exception.BusinessNotFoundException;
import win.doyto.i18n.mapper.GroupLocaleMapper;
import win.doyto.i18n.mapper.GroupMapper;
import win.doyto.i18n.model.Group;
import win.doyto.i18n.model.Lang;
import win.doyto.web.service.AbstractService;

/**
 * GroupDefaultService
 *
 * @author f0rb on 2017-03-29.
 */
@Slf4j
@Service
public class GroupDefaultService extends AbstractService<Group> implements GroupService {
    @Resource
    private GroupMapper groupMapper;

    @Resource
    private GroupLocaleMapper groupLocaleMapper;

    @Override
    public GroupMapper getIMapper() {
        return groupMapper;
    }

    @Override
    public Group save(Group group) {
        Group origin = groupMapper.get(group.getId());
        if (origin == null) {
            return null;
        }
        //origin.setLabel(group.getLabel());
        //origin.setValue(group.getValue());

        //origin.setUpdateUserId(AppContext.getLoginUserId());
        //origin.setUpdateTime(new Date());
        groupMapper.update(origin);
        return origin;
    }

    @Override
    public List query(String group) {
        checkGroup(group);
        return groupLocaleMapper.langByGroup(group);
    }

    @Override
    public List<Lang> query(String group, String locale) {
        checkGroup(group);
        return groupLocaleMapper.langByGroupAndLocale(group, locale);
    }

    @Override
    public void checkGroup(String group) {
        try {
            groupLocaleMapper.existGroup(group);
        } catch (Exception e) {
            throw new BusinessNotFoundException("多语言分组未配置: " + group);
        }
    }

    @Override
    public void checkGroupAndLocale(String group, String locale) {
        if (!existLocale(group, locale)){
            throw new BusinessNotFoundException("多语言分组[" + group + "]未配置语种: " + locale);
        }
    }

    private boolean existLocale(String group, String locale) {
        try {
            groupLocaleMapper.existLocaleOnGroup(group, locale);
        } catch (Exception e) {
            log.info("多语言分组[{}]不存在语种: {}", group, locale);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public String addLocaleOnGroup(String group, String locale) {
        checkGroup(group);
        if (!existLocale(group, locale)) {
            groupLocaleMapper.addLocaleOnGroup(group, locale);
        }
        return locale;
    }

    @Override
    @Transactional
    public List<Lang> saveTranslation(String group, String locale, Map<String, String> langMap) {
        addLocaleOnGroup(group, locale);
        int ret = groupLocaleMapper.saveTranslation(group, locale, langMap);
        List<Lang> returnList = groupLocaleMapper.langByGroupAndLocale(group, locale);
        return returnList;
    }

}