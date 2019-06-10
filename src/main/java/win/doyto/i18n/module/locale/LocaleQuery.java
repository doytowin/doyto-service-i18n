package win.doyto.i18n.module.locale;

import lombok.*;
import win.doyto.query.annotation.SubQuery;
import win.doyto.query.core.PageQuery;

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
public class LocaleQuery extends PageQuery {

    @SubQuery(column = "groupId", left = "id", table = "i18n_resource_group")
    private String groupName;

    private Integer groupId;

    private String owner;

    private String locale;

    private String language;

}
