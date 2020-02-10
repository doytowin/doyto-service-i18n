package win.doyto.i18n.module.locale;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import win.doyto.i18n.common.CommonUtils;
import win.doyto.i18n.module.group.GroupApi;
import win.doyto.i18n.module.group.GroupResponse;
import win.doyto.query.entity.EntityAspect;

import java.util.HashMap;

import static win.doyto.i18n.module.i18n.I18nView.GROUP_FORMAT;

/**
 * GroupEntityAspect
 *
 * @author f0rb on 2020-02-09
 */
@AllArgsConstructor
@Component
public class AddLocaleColumnAfterCreateLocale implements EntityAspect<LocaleEntity> {

    private GroupApi groupApi;
    private JdbcOperations jdbcOperations;

    @Override
    public void afterCreate(LocaleEntity localeEntity) {
        GroupResponse groupResponse = groupApi.getById(localeEntity.getGroupId());
        String sql = "ALTER TABLE " + GROUP_FORMAT + " ADD locale_${locale} VARCHAR(1000) NOT NULL DEFAULT ''";
        HashMap<String, String> params = new HashMap<>();
        params.put("user", localeEntity.getOwner());
        params.put("group", groupResponse.getName());
        params.put("locale", localeEntity.getLocale());
        jdbcOperations.update(CommonUtils.replaceHolderEx(sql, params));
    }

}
