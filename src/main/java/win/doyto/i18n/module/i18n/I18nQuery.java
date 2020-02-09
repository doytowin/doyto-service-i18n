package win.doyto.i18n.module.i18n;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import win.doyto.query.core.PageQuery;

import javax.persistence.Transient;

/**
 * I18nQuery
 *
 * @author f0rb
 * @date 2019-05-21
 */
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
public class I18nQuery extends PageQuery {
    public I18nQuery() {// no lombok here
    }

    @Transient
    private String user;

    @Transient
    private String group;

    private String locale;

    @Builder.Default
    private Boolean valid = true;

}
