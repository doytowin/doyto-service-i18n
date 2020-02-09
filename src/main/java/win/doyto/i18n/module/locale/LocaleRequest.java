package win.doyto.i18n.module.locale;

import lombok.Getter;
import lombok.Setter;
import win.doyto.auth.core.UsernameAware;

/**
 * LocaleRequest
 *
 * @author f0rb on 2019-05-22
 */
@Getter
@Setter
public class LocaleRequest implements UsernameAware {
    private String username;
    private Integer groupId;
    private String group;
    private String locale;
    private String baiduLocale;
    private String language;
}
