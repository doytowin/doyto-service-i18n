package win.doyto.i18n.module.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.service.AbstractDynamicService;
import win.doyto.query.service.PageList;
import win.doyto.query.web.response.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * I18nService
 *
 * @author f0rb on 2017-03-31.
 */
@Slf4j
@Service
@SuppressWarnings("java:S3740")
public class I18nService extends AbstractDynamicService<I18nView, Integer, I18nQuery> {

    private static String getGroupName(String user, String group) {
        return user + "_" + group;
    }

    public List<I18nView> query(String user, String group, String locale) {
        return query(I18nQuery.builder().user(user).group(group).locale(locale).build());
    }

    public void existGroup(String user, String group) {
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).build();
        i18nQuery.setPageSize(1);
        count(i18nQuery);
    }

    public List<Map> queryAll(I18nQuery i18nQuery) {
        return queryColumns(i18nQuery, Map.class, "*");
    }

    /**
     * 如果locale_${locale}为null, 则以默认值替代
     *
     * @param group  资源分组名
     * @param locale 语种
     * @return {label,value}
     */
    public List<I18nView> queryWithDefault(String user, String group, String locale) {
        checkGroup(user, group);
        I18nQuery i18nQuery = I18nQuery.builder().user(user).group(group).locale(locale).build();
        return queryColumns(
                i18nQuery, I18nView.class,
                "label, defaults, IF(locale_${locale} IS NULL OR LENGTH(locale_${locale}) = 0, defaults, locale_${locale}) AS value");
    }

    public List<Map> query(String user, String group) {
        checkGroup(user, group);
        return queryAll(I18nQuery.builder().user(user).group(group).build());
    }

    public PageList<Map> paging(I18nQuery i18nQuery) {
        checkGroup(i18nQuery.getUser(), i18nQuery.getGroup());
        return new PageList<>(queryAll(i18nQuery), count(i18nQuery));
    }

    public List<I18nView> queryWithDefaults(String user, String group, String locale) {
        checkGroupAndLocale(user, group, locale);
        return query(user, group, locale);
    }

    public void checkGroup(String user, String group) {
        try {
            existGroup(user, group);
        } catch (Exception e) {
            ErrorCode.fail(I18nErrorCode.GROUP_NOT_FOUND.build(getGroupName(user, group)));
        }
    }

    public void checkGroupAndLocale(String user, String group, String locale) {
        try {
            query(user, group, locale);
        } catch (Exception e) {
            ErrorCode.fail(I18nErrorCode.LOCALE_NOT_FOUND.build(getGroupName(user, group), locale));
        }
    }

    @Transactional
    public void saveTranslation(List<I18nView> i18nViewList) {
        if (i18nViewList.isEmpty()) {
            log.info("翻译记录为空");
            return;
        }
        I18nView first = i18nViewList.get(0);
        I18nQuery query = I18nQuery.builder().user(first.getUser()).group(first.getGroup()).locale(first.getLocale()).build();
        ArrayList<I18nView> addList = new ArrayList<>();
        for (I18nView i18nView : i18nViewList) {
            I18nView origin = get(query.setLabel(i18nView.getLabel()));
            if (origin != null) {
                i18nView.setId(origin.getId());
                patch(i18nView);
            } else {
                addList.add(i18nView);
            }
        }
        int ret = create(addList);
        log.info("保存翻译完毕: {} / {}", ret, i18nViewList.size());
    }

    @Transactional
    public void saveTranslation(String user, String group, String locale, Map<String, String> translationMap) {
        List<I18nView> i18nViewList = new ArrayList<>(translationMap.size());
        for (Map.Entry<String, String> entry : translationMap.entrySet()) {
            I18nView i18nView = new I18nView();
            i18nView.setUser(user);
            i18nView.setGroup(group);
            i18nView.setLocale(locale);
            i18nView.setLabel(entry.getKey());
            i18nView.setDefaults(entry.getKey());
            i18nView.setValue(entry.getValue());
            i18nViewList.add(i18nView);
        }
        saveTranslation(i18nViewList);
    }

}
