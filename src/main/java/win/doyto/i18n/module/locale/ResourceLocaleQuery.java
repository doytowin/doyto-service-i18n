package win.doyto.i18n.module.locale;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.PageQuery;
import win.doyto.query.QueryProperty;
import win.doyto.query.QueryTable;
import win.doyto.i18n.module.group.ResourceGroup;

/**
 * toResourceLocale
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Builder
@QueryTable(table = ResourceLocale.TABLE, entity = ResourceLocale.class)
public class ResourceLocaleQuery extends PageQuery {

    @QueryProperty(where = "groupId = (select id from " + ResourceGroup.TABLE + " where group = ${group})")
    private String group;

    private Integer groupId;

    private String owner;

    private String locale;

    private String language;

}
