package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;
import win.doyto.auth.core.UsernameAware;
import win.doyto.query.validation.UpdateGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * GroupRequest
 *
 * @author f0rb on 2018-04-21.
 */
@Getter
@Setter
public class GroupRequest implements UsernameAware {

    @NotNull(groups = UpdateGroup.class)
    private Integer id;

    @NotNull
    @Pattern(regexp = "\\w+")
    private String name;

    @NotNull
    @Pattern(regexp = "\\w+")
    private String label;

    private String owner;

    @Override
    public void setUsername(String username) {
        this.setOwner(username);
    }
}
