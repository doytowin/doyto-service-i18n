package win.doyto.i18n.module.locale;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.i18n.common.UsernameAware;
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

    private String createUserId;

    private String groupName;

    private String locale;

    @Override
    public void setUsername(String username) {
        this.setCreateUserId(username);
    }

    public void setGroup(String groupName) {
        this.groupName = groupName;
    }
}
