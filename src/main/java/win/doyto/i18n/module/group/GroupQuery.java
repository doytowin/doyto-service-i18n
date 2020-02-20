package win.doyto.i18n.module.group;

import lombok.*;
import lombok.experimental.SuperBuilder;
import win.doyto.auth.core.UsernameAware;
import win.doyto.query.core.PageQuery;

/**
 * GroupQuery
 *
 * @author f0rb on 2018-04-24.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GroupQuery extends PageQuery implements UsernameAware {

    private String createUserId;

    @Builder.Default
    private Boolean deleted = false;

    private String groupNameLike;

    private Boolean valid;

    @Override
    public void setUsername(String username) {
        this.setCreateUserId(username);
    }
}
