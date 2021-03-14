package win.doyto.i18n.module.group;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;
import win.doyto.i18n.common.CommonUtils;
import win.doyto.i18n.common.TranslationTableDialect;
import win.doyto.i18n.module.locale.LocaleApi;
import win.doyto.i18n.module.locale.LocaleEntity;
import win.doyto.query.entity.EntityAspect;

import java.util.HashMap;

/**
 * GroupEntityAspect
 *
 * @author f0rb on 2020-02-09
 */
@Slf4j
@AllArgsConstructor
@Component
public class CreateTranslationTableAfterCreateGroup implements EntityAspect<GroupEntity> {

    private JdbcOperations jdbcOperations;

    private LocaleApi localeApi;

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
        String owner = groupEntity.getCreateUserId();
        String group = groupEntity.getName();
        this.createGroupTable(owner, group);
        this.insertZhCn(group);
    }

    public void createGroupTable(String owner, String group) {
        String sql = translationTableDialect.buildTranslationTableDDL();
        log.info("创建数据表: {}", sql);
        HashMap<String, String> params = new HashMap<>();
        params.put("user", owner);
        params.put("group", group);
        jdbcOperations.update(CommonUtils.replaceHolderEx(sql, params));
    }

    private void insertZhCn(String group) {
        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setGroupName(group);
        localeEntity.setLocale("zh_CN");
        localeEntity.setLanguage("简体中文");
        localeEntity.setBaiduLocale("zh");
        localeApi.create(localeEntity);
    }

}
