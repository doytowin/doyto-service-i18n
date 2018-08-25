package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.common.repository.PageQuery;
import win.doyto.common.repository.QueryTable;

/**
 * ResourceGroupQuery
 *
 * @author f0rb on 2018-04-24.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@QueryTable(table = ResourceGroup.TABLE, entity = ResourceGroup.class)
public class ResourceGroupQuery extends PageQuery {

    private Boolean deleted = false;

    private String owner;

    private String nameLike;

    private Boolean valid;

}
