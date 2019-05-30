package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.common.web.ErrorCode;
import win.doyto.i18n.module.baidu.BaiduTran;
import win.doyto.i18n.module.baidu.BaiduTranApi;
import win.doyto.i18n.module.group.GroupResponse;
import win.doyto.i18n.module.group.GroupService;
import win.doyto.i18n.module.locale.LocaleRequest;
import win.doyto.i18n.module.locale.LocaleResponse;
import win.doyto.i18n.module.locale.LocaleService;
import win.doyto.query.service.PageList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

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
    private I18nTableMapper i18nTableMapper;

    @Resource
    private BaiduTranApi baiduTranApi;

    @Resource
    GroupService groupService;

    @Resource
    private LocaleService localeService;

    private static String getGroupName(String user, String group) {
        return user + "_" + group;
    }

    public List query(String user, String group) {
        checkGroup(user, group);
        return i18nMapper.query(I18nQuery.builder().user(user).group(group).build());
    }

    public PageList page(I18nQuery i18nQuery) {
        checkGroup(i18nQuery.getUser(), i18nQuery.getGroup());
        return new PageList<>(i18nMapper.query(i18nQuery), i18nMapper.count(i18nQuery));
    }

    public List<LangView> query(String user, String group, String locale) {
        checkGroup(user, group);
        return i18nMapper.langByGroupAndLocale(user, group, locale);
    }

    public List<LangView> queryWithDefaults(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);
        return i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);
    }

    public void checkGroup(String user, String group) {
        try {
            i18nMapper.existGroup(user, group);
        } catch (Exception e) {
            ErrorCode.fail(ErrorCode.build(-1 ,"多语言分组未配置: " + getGroupName(user, group)));
        }
    }

    public void checkGroupAndLocale(String user, String group, String locale) {
        ErrorCode.assertTrue(existLocale(user, group, locale), ErrorCode.build(-1, "多语言分组[" + getGroupName(user, group) + "]未配置语种: " + locale));
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
            i18nTableMapper.addLocaleOnGroup(user, group, locale);
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

        List<LangView> langViewList = i18nMapper.langWithDefaultsByGroupAndLocale(user, group, locale);

        LocaleResponse localeResponse = localeService.getByGroupAndLocale(group, locale);

        String to = localeResponse.getBaiduTranLang();
        Map<String, String> translationMap = new HashMap<>();
        for (LangView langView : langViewList) {
            if (StringUtils.isBlank(langView.getValue())) {
                BaiduTran baiduTran = baiduTranApi.getTrans(langView.getDefaults(), "auto", to);
                if (baiduTran.fail()) {
                    log.error("翻译接口调用失败: {}[{}]", baiduTran.getErrorMsg(), baiduTran.getErrorCode());
                }
                try {
                    if (baiduTran.getTransResult() != null) {
                        translationMap.put(langView.getLabel(), baiduTran.getTransResult()[0].getDst());
                    }
                } catch (Exception e) {
                    log.error("获取翻译失败: " + langView.getLabel(), e);
                }
            }
        }
        int ret = translationMap.isEmpty() ? 0 : i18nMapper.saveTranslation(user, group, locale, translationMap);
        log.info("自动翻译完毕: {} / {}", ret, translationMap.size());
    }

    public void createGroupTable(String user, String name) {
        i18nTableMapper.createGroupTable(user, name);
    }

    @Transactional
    public void createGroup(String owner, String group, String label, String locale) {
        groupService.insertGroup(owner, group, label);
        createGroupTable(owner, group);
        addLocaleOnGroup(owner, group, locale);
    }

    @Transactional
    public void addLocale(LocaleRequest request) {
        localeService.create(request);
        GroupResponse group = groupService.getById(request.getGroupId());
        addLocaleOnGroup(group.getOwner(), group.getName(), request.getLocale());
    }
}
