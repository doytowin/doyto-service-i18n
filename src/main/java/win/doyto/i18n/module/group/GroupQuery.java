package win.doyto.i18n.module.group;

import lombok.*;
import win.doyto.query.core.PageQuery;
import win.doyto.query.core.QueryTable;

/**
 * GroupQuery
 *
 * @author f0rb on 2018-04-24.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@QueryTable(table = GroupEntity.TABLE, entityClass = GroupEntity.class)
public class GroupQuery extends PageQuery {

    public GroupQuery() {//lombok
    }

    @Builder.Default
    private Boolean deleted = false;

    private String owner;

    private String nameLike;

    private Boolean valid;

}
