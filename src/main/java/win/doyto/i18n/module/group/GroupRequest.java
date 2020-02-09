package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;
import win.doyto.auth.core.UsernameAware;

import javax.validation.constraints.Pattern;

/**
 * GroupRequest
 *
 * @author f0rb on 2018-04-21.
 */
@Getter
@Setter
public class GroupRequest implements UsernameAware {
    private Integer id;

    @Pattern(regexp = "\\w+")
    private String name;

    @Pattern(regexp = "\\w+")
    private String label;

    private String locale = "zh_CN";

    private String owner;

    @Override
    public void setUsername(String username) {
        this.setOwner(username);
    }
}
