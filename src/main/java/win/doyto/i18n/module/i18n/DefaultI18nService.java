package win.doyto.i18n.module.i18n;

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
import win.doyto.i18n.component.baidu.BaiduTran;
import win.doyto.i18n.component.baidu.BaiduTranApi;
import win.doyto.i18n.exception.RestNotFoundException;
import win.doyto.i18n.module.group.ResourceGroup;
import win.doyto.i18n.module.localle.ResourceLocale;
import win.doyto.i18n.module.group.ResourceGroupService;
import win.doyto.i18n.module.localle.ResourceLocaleService;
import win.doyto.web.PageResponse;

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

    private static String getGroupName(String user, String group) {
        return user + "_" + group;
    }

    @Override
    public List query(String user, String group) {
        checkGroup(user, group);
        return i18nMapper.langByGroup(user, group);
    }

    @Override
    public PageResponse query(I18n i18n) {
        checkGroup(i18n.getUser(), i18n.getGroup());
        PageResponse<LinkedHashMap<String, ?>> pageList = new PageResponse<>();
        pageList.setList(i18nMapper.query(i18n));
        pageList.setTotal(i18nMapper.count(i18n));
        return pageList;
    }

    @Override
    public List<Lang> query(String user, String group, String locale) {
        checkGroup(user, group);
        return i18nMapper.langByGroupAndLocale(user, group, locale);
    }

    @Override
    public List<Lang> queryWithDefaults(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);
        return i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);
    }

    @Override
    public void checkGroup(String user, String group) {
        try {
            i18nMapper.existGroup(user, group);
        } catch (Exception e) {
            throw new RestNotFoundException("多语言分组未配置: " + getGroupName(user, group));
        }
    }

    @Override
    public void checkGroupAndLocale(String user, String group, String locale) {
        if (!existLocale(user, group, locale)){
            throw new RestNotFoundException("多语言分组[" + getGroupName(user, group) + "]未配置语种: " + locale);
        }
    }

    private boolean existLocale(String user, String group, String locale) {
        try {
            i18nMapper.existLocaleOnGroup(user, group, locale);
        } catch (Exception e) {
            log.info("多语言分组[{}]不存在语种: {}", getGroupName(user, group), locale);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public String addLocaleOnGroup(String user, String group, String locale) {
        checkGroup(user, group);
        if (!existLocale(user, group, locale)) {
            i18nMapper.addLocaleOnGroup(user, group, locale);
        }
        return locale;
    }

    @Override
    @Transactional
    public void saveTranslation(String user, String group, String locale, Map<String, String> translationMap) {
        addLocaleOnGroup(user, group, locale);
        int ret = i18nMapper.saveTranslation(user, group, locale, translationMap);
        log.info("保存翻译完毕: {} / {}", ret, translationMap.size());
    }

    @Override
    public void autoTranslate(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);

        List<Lang> langList = i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);

        ResourceGroup resourceGroup = resourceGroupService.getGroup(user, group);
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
        int ret = translationMap.isEmpty() ? 0 : i18nMapper.saveTranslation(user, group, locale, translationMap);
        log.info("自动翻译完毕: {} / {}", ret, translationMap.size());
    }

    @Override
    public void createGroupTable(String user, String name) {
        i18nMapper.createGroupTable(user, name);
    }
}
