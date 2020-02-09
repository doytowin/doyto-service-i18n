package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.IntegerId;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Lang
 *
 * @author f0rb on 2017-03-30.
 */
@Getter
@Setter
@Table(name = I18nEntity.GROUP_FORMAT)
public class LangView extends IntegerId {
    @Transient
    private String user;

    @Transient
    private String group;

    @Transient
    private String locale;

    private String label;

    private String defaults;

    @Column(name = "locale_${locale}")
    private String value;

    @Override
    public LangIdWrapper toIdWrapper() {
        return new LangIdWrapper(id, user, group);
    }
}
