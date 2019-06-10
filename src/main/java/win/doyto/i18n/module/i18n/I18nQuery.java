package win.doyto.i18n.module.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import win.doyto.query.core.PageQuery;

/**
 * I18nQuery
 *
 * @author f0rb
 * @date 2019-05-21
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class I18nQuery extends PageQuery {
    public I18nQuery() {// no lombok here
    }

    private String user;

    private String group;

    private String locale;

    @Builder.Default
    private Boolean valid = true;

}
