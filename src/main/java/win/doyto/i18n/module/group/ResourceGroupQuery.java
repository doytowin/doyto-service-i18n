package win.doyto.i18n.module.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.PageQuery;
import win.doyto.query.QueryTable;

/**
 * ResourceGroupQuery
 *
 * @author f0rb on 2018-04-24.
 */
@Getter
@Setter
@SuperBuilder
@QueryTable(table = ResourceGroup.TABLE, entity = ResourceGroup.class)
public class ResourceGroupQuery extends PageQuery {

    public ResourceGroupQuery() {
    }

    @Builder.Default
    private Boolean deleted = false;

    private String owner;

    private String nameLike;

    private Boolean valid;

}
