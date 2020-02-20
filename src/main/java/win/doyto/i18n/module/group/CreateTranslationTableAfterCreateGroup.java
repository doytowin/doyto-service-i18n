package win.doyto.i18n.module.group;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import win.doyto.i18n.common.CommonUtils;
import win.doyto.i18n.common.TranslationTableDialect;
import win.doyto.i18n.module.locale.LocaleRequest;
import win.doyto.i18n.module.locale.LocaleService;
import win.doyto.query.entity.EntityAspect;

import java.util.HashMap;

/**
 * GroupEntityAspect
 *
 * @author f0rb on 2020-02-09
 */
@AllArgsConstructor
@Component
public class CreateTranslationTableAfterCreateGroup implements EntityAspect<GroupEntity> {

    private JdbcOperations jdbcOperations;

    private LocaleService localeService;

    private TranslationTableDialect translationTableDialect;

    /**
     * 创建分组后
     * <ol>
     *     <li>创建词条表</li>
     *     <li>新增默认语种, 触发词条表新增语种列</li>
     * </ol>
     *
     * @param groupEntity 新增的分组
     */
    @Override
    public void afterCreate(GroupEntity groupEntity) {
        String owner = groupEntity.getOwner();
        String group = groupEntity.getName();
        this.createGroupTable(owner, group);
        this.insertZhCn(owner, groupEntity.getId());
    }

    public void createGroupTable(String owner, String group) {
        String sql = translationTableDialect.buildTranslationTableDDL();
        HashMap<String, String> params = new HashMap<>();
        params.put("user", owner);
        params.put("group", group);
        jdbcOperations.update(CommonUtils.replaceHolderEx(sql, params));
    }

    private void insertZhCn(String owner, Integer groupId) {
        LocaleRequest localeRequest = new LocaleRequest();
        localeRequest.setUsername(owner);
        localeRequest.setGroupId(groupId);
        localeRequest.setLocale("zh_CN");
        localeRequest.setLanguage("简体中文");
        localeRequest.setBaiduLocale("zh");
        localeService.add(localeRequest);
    }

}
