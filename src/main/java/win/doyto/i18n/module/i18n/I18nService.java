package win.doyto.i18n.module.i18n;

import java.util.*;
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
import win.doyto.i18n.module.group.ResourceGroupService;
import win.doyto.i18n.module.locale.ResourceLocale;
import win.doyto.i18n.module.locale.ResourceLocaleMapper;
import win.doyto.i18n.module.locale.ResourceLocaleService;
import win.doyto.web.PageResponse;
import win.doyto.web.RestEnum;
import win.doyto.web.RestError;

/**
 * I18nService
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
@Service
@EnableConfigurationProperties
public class I18nService {

    @Resource
    private I18nMapper i18nMapper;

    @Resource
    private BaiduTranApi baiduTranApi;

    @Resource
    private ResourceGroupService resourceGroupService;

    @Resource
    private ResourceLocaleService resourceLocaleService;

    @Resource
    private ResourceLocaleMapper resourceLocaleMapper;

    private static String getGroupName(String user, String group) {
        return user + "_" + group;
    }

    public List query(String user, String group) {
        checkGroup(user, group);
        return i18nMapper.langByGroup(user, group);
    }

    public PageResponse query(I18n i18n) {
        checkGroup(i18n.getUser(), i18n.getGroup());
        PageResponse<LinkedHashMap<String, ?>> pageList = new PageResponse<>();
        pageList.setList(i18nMapper.query(i18n));
        pageList.setTotal(i18nMapper.count(i18n));
        return pageList;
    }

    public List<Lang> query(String user, String group, String locale) {
        checkGroup(user, group);
        return i18nMapper.langByGroupAndLocale(user, group, locale);
    }

    public List<Lang> queryWithDefaults(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);
        return i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);
    }

    public void checkGroup(String user, String group) {
        try {
            i18nMapper.existGroup(user, group);
        } catch (Exception e) {
            throw new RestNotFoundException("多语言分组未配置: " + getGroupName(user, group));
        }
    }

    public void checkGroupAndLocale(String user, String group, String locale) {
        if (!existLocale(user, group, locale)) {
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

    @Transactional
    public String addLocaleOnGroup(String user, String group, String locale) {
        checkGroup(user, group);
        if (!existLocale(user, group, locale)) {
            i18nMapper.addLocaleOnGroup(user, group, locale);
        }
        return locale;
    }

    @Transactional
    public void saveTranslation(String user, String group, String locale, Map<String, String> translationMap) {
        addLocaleOnGroup(user, group, locale);
        int ret = i18nMapper.saveTranslation(user, group, locale, translationMap);
        log.info("保存翻译完毕: {} / {}", ret, translationMap.size());
    }

    public void autoTranslate(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);

        List<Lang> langList = i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);

        ResourceLocale resourceLocale = resourceLocaleService.getByGroupAndLocale(group, locale);

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

    public void createGroupTable(String user, String name) {
        i18nMapper.createGroupTable(user, name);
    }

    @Transactional
    public RestError createGroup(String owner, String group, String label, String locale) {
        insertGroup(owner, group, label);
        createGroupTable(owner, group);
        addLocaleOnGroup(owner, group, locale);
        return RestEnum.Success.value();
    }

    private void insertGroup(String owner, String name, String label) {
        ResourceGroup resourceGroup;
        resourceGroup = new ResourceGroup();
        resourceGroup.setOwner(owner);
        resourceGroup.setName(name);
        resourceGroup.setLabel(label);
        resourceGroup.setCreateTime(new Date());
        resourceGroupService.add(resourceGroup);
    }

    @Transactional
    public ResourceLocale addLocale(ResourceLocale resourceLocale) {
        resourceLocaleService.add(resourceLocale);
        ResourceGroup group = resourceGroupService.get(resourceLocale.getGroupId());
        addLocaleOnGroup(group.getOwner(), group.getName(), resourceLocale.getLocale());
        return resourceLocale;
    }
}
