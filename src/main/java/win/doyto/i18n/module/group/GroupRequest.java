package win.doyto.i18n.module.group;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import win.doyto.i18n.common.UsernameAware;
import win.doyto.query.validation.UpdateGroup;

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
    @Pattern(regexp = ".+")
    private String label;

    private String createUserId;

    @Override
    public void setUsername(String username) {
        this.createUserId = username;
    }
}
