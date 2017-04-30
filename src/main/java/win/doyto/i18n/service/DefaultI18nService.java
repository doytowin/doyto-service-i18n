package win.doyto.i18n.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.component.BaiduTran;
import win.doyto.i18n.component.BaiduTranApi;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.mapper.I18nMapper;
import win.doyto.i18n.model.I18n;
import win.doyto.i18n.model.Lang;
import win.doyto.i18n.model.ResourceGroup;
import win.doyto.i18n.model.ResourceLocale;
import win.doyto.web.PageList;

/**
 * I18nService
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
@Service
@EnableConfigurationProperties
public class DefaultI18nService implements I18nService {

    @Resource
    private I18nMapper i18nMapper;

    @Resource
    private BaiduTranApi baiduTranApi;

    @Resource
    private ResourceGroupService resourceGroupService;

    @Resource
    private ResourceLocaleService resourceLocaleService;

    @Override
    public List query(String group) {
        checkGroup(group);
        return i18nMapper.langByGroup(group);
    }

    @Override
    public List query(I18n i18n) {
        checkGroup(i18n.getGroup());
        PageList<LinkedHashMap<String, ?>> pageList = new PageList<>();
        pageList.setList(i18nMapper.query(i18n));
        pageList.setTotal(i18nMapper.count(i18n));
        return pageList;
    }

    @Override
    public List<Lang> query(String group, String locale) {
        checkGroup(group);
        return i18nMapper.langByGroupAndLocale(group, locale);
    }

    @Override
    public List<Lang> queryWithDefaults(String group, String locale) {
        checkGroupAndLocale(group, locale);
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
    public void saveTranslation(String group, String locale, Map<String, String> translationMap) {
        addLocaleOnGroup(group, locale);
        int ret = i18nMapper.saveTranslation(group, locale, translationMap);
        log.info("保存翻译完毕: {} / {}", ret, translationMap.size());
    }

    @Override
    public void autoTranslate(String group, String locale) {
        checkGroupAndLocale(group, locale);

        List<Lang> langList = i18nMapper.langWithDefaultsByGroupAndLocale(group, locale);

        ResourceGroup resourceGroup = resourceGroupService.getGroup(group);
        ResourceLocale resourceLocale = resourceLocaleService.getByGroupAndLocale(resourceGroup.getId(), locale);

        String to = resourceLocale.getBaiduTranLang();
        Map<String, String> translationMap = new HashMap<>();
        for (Lang lang : langList) {
            if (StringUtils.isBlank(lang.getValue())) {
                BaiduTran baiduTran = baiduTranApi.getTrans(lang.getDefaults(), "auto", to);
                if (baiduTran.fail()) {
                    log.error("翻译接口调用失败: {}[{}]", baiduTran.getErrorMsg(), baiduTran.getErrorCode());
                }
                try {
                    if (baiduTran.getTransResult() != null) {
                        translationMap.put(lang.getLabel(), baiduTran.getTransResult()[0].getDst());
                    }
                } catch (Exception e) {
                    log.error("获取翻译失败: " + lang.getLabel(), e);
                }
            }
        }
        int ret = i18nMapper.saveTranslation(group, locale, translationMap);
        log.info("自动翻译完毕: {} / {}", ret, translationMap.size());
    }
}
