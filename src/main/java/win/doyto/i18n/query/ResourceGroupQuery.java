package win.doyto.i18n.query;

import lombok.Getter;
import lombok.Setter;

/**
 * ResourceGroupQuery
 *
 * @author f0rb on 2018-04-24.
 */
@Getter
@Setter
public class ResourceGroupQuery extends PageQuery {

    private String owner;

    private String name;

    private String label;

    private Boolean valid;

}
