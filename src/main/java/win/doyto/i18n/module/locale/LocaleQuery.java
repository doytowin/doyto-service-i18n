package win.doyto.i18n.module.locale;

import lombok.*;
import win.doyto.query.core.PageQuery;
import win.doyto.query.core.QueryField;
import win.doyto.query.core.QueryTable;

/**
 * toResourceLocale
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@QueryTable(table = LocaleEntity.TABLE)
public class LocaleQuery extends PageQuery {

    @QueryField(and = "groupId = (select id from " + "i18n_resource_group" + " where name = #{group})")
    private String group;

    private Integer groupId;

    private String owner;

    private String locale;

    private String language;

}
