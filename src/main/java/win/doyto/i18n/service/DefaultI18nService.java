package win.doyto.i18n.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.mapper.I18nMapper;
import win.doyto.i18n.model.Lang;

/**
 * I18nService
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
@Service
public class DefaultI18nService implements I18nService {

    @Resource
    private I18nMapper i18nMapper;

    @Override
    public List query(String group) {
        checkGroup(group);
        return i18nMapper.langByGroup(group);
    }

    @Override
    public List<Lang> query(String group, String locale) {
        checkGroup(group);
        return i18nMapper.langByGroupAndLocale(group, locale);
    }

    @Override
    public List<Lang> queryWithDefaults(String group, String locale) {
        checkGroup(group);
        return i18nMapper.langWithDefaultsByGroupAndLocale(group, locale);
    }

    @Override
    public void checkGroup(String group) {
        try {
            i18nMapper.existGroup(group);
        } catch (Exception e) {
            throw new RestNotFoundException("多语言分组未配置: " + group);
        }
    }

    @Override
    public void checkGroupAndLocale(String group, String locale) {
        if (!existLocale(group, locale)){
            throw new RestNotFoundException("多语言分组[" + group + "]未配置语种: " + locale);
        }
    }

    private boolean existLocale(String group, String locale) {
        try {
            i18nMapper.existLocaleOnGroup(group, locale);
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
            i18nMapper.addLocaleOnGroup(group, locale);
        }
        return locale;
    }

    @Override
    @Transactional
    public List<Lang> saveTranslation(String group, String locale, Map<String, String> langMap) {
        addLocaleOnGroup(group, locale);
        int ret = i18nMapper.saveTranslation(group, locale, langMap);
        List<Lang> returnList = i18nMapper.langWithDefaultsByGroupAndLocale(group, locale);
        return returnList;
    }
}
