package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.common.web.ErrorCode;
import win.doyto.i18n.module.baidu.BaiduTranResponse;
import win.doyto.i18n.module.baidu.BaiduTranService;
import win.doyto.i18n.module.group.GroupResponse;
import win.doyto.i18n.module.group.GroupService;
import win.doyto.i18n.module.locale.LocaleRequest;
import win.doyto.i18n.module.locale.LocaleResponse;
import win.doyto.i18n.module.locale.LocaleService;
import win.doyto.query.service.PageList;

import java.util.ArrayList;
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
public class I18nService {

    @Resource
    private LangService langService;

    @Resource
    private BaiduTranService baiduTranService;

    @Resource
    GroupService groupService;

    @Resource
    private LocaleService localeService;

    private static String getGroupName(String user, String group) {
        return user + "_" + group;
    }

    public List query(String user, String group) {
        checkGroup(user, group);
        return langService.queryAll(I18nQuery.builder().user(user).group(group).build());
    }

    public PageList page(I18nQuery i18nQuery) {
        checkGroup(i18nQuery.getUser(), i18nQuery.getGroup());
        return new PageList<>(langService.queryAll(i18nQuery), langService.count(i18nQuery));
    }

    public List<LangView> query(String user, String group, String locale) {
        checkGroup(user, group);
        return langService.langByGroupAndLocale(user, group, locale);
    }

    public List<LangView> queryWithDefaults(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);
        return langService.query(user, group, locale);
    }

    public void checkGroup(String user, String group) {
        try {
            langService.existGroup(user, group);
        } catch (Exception e) {
            ErrorCode.fail(ErrorCode.build(-1 ,"多语言分组未配置: " + getGroupName(user, group)));
        }
    }

    public void checkGroupAndLocale(String user, String group, String locale) {
        ErrorCode.assertTrue(existLocale(user, group, locale), ErrorCode.build(-1, "多语言分组[" + getGroupName(user, group) + "]未配置语种: " + locale));
    }

    private boolean existLocale(String user, String group, String locale) {
        try {
            langService.existLocaleOnGroup(user, group, locale);
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
            langService.addLocaleOnGroup(user, group, locale);
        }
        return locale;
    }

    private void saveTranslation(List<LangView> langViewList) {
        int ret = langService.batchInsert(langViewList, "locale_${locale}");
        log.info("保存翻译完毕: {} / {}", ret, langViewList.size());
    }

    @Transactional
    public void saveTranslation(String user, String group, String locale, Map<String, String> translationMap) {
        addLocaleOnGroup(user, group, locale);
        List<LangView> langViewList = new ArrayList<>(translationMap.size());
        for (Map.Entry<String, String> entry : translationMap.entrySet()) {
            LangView langView = new LangView();
            langView.setUser(user);
            langView.setGroup(group);
            langView.setLocale(locale);
            langView.setLabel(entry.getKey());
            langView.setDefaults(entry.getKey());
            langView.setValue(entry.getValue());
            langViewList.add(langView);
        }
        saveTranslation(langViewList);
    }

    @Transactional
    public void autoTranslate(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);

        List<LangView> langViewList = langService.query(user, group, locale);

        LocaleResponse localeResponse = localeService.getByGroupAndLocale(group, locale);

        String to = localeResponse.getBaiduTranLang();
        for (LangView langView : langViewList) {
            langView.setUser(user);
            langView.setGroup(group);
            langView.setLocale(locale);
            if (StringUtils.isNotBlank(langView.getDefaults()) && StringUtils.isBlank(langView.getValue())) {
                BaiduTranResponse baiduTranResponse = baiduTranService.translate(langView.getDefaults(), "auto", to);
                if (baiduTranResponse.success()) {
                    if (baiduTranResponse.getTransResult() != null) {
                        langView.setValue(baiduTranResponse.getTransResult()[0].getDst());
                    }
                } else {
                    log.error("翻译接口调用失败: {}[{}]", baiduTranResponse.getErrorMsg(), baiduTranResponse.getErrorCode());
                }
            }
        }
        saveTranslation(langViewList);
    }

    @Transactional
    public void createGroup(String owner, String group, String label, String locale) {
        groupService.insertGroup(owner, group, label);
        langService.createGroupTable(owner, group);
        addLocaleOnGroup(owner, group, locale);
    }

    @Transactional
    public void addLocale(LocaleRequest request) {
        GroupResponse groupResponse = groupService.getGroup(request.getUsername(), request.getGroup());
        request.setGroupId(groupResponse.getId());
        localeService.create(request);
        GroupResponse group = groupService.getById(request.getGroupId());
        addLocaleOnGroup(group.getOwner(), group.getName(), request.getLocale());
    }
}
