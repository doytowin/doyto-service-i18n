package win.doyto.i18n.module.locale;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.auth.core.UsernameAware;
import win.doyto.query.annotation.SubQuery;
import win.doyto.query.core.PageQuery;

/**
 * toResourceLocale
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class LocaleQuery extends PageQuery implements UsernameAware {

    private String owner;

    @JsonAlias("group")
    @SubQuery(column = "groupId", left = "id", table = "i18n_resource_group")
    private String groupName;

    private Integer groupId;

    private String locale;

    private String language;

    @Override
    public void setUsername(String username) {
        this.setOwner(username);
    }

    public void setGroup(String groupName) {
        this.groupName = groupName;
    }
}
