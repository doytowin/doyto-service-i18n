package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;


/**
 * Lang
 *
 * @author f0rb on 2017-03-30.
 */
@Getter
@Setter
@Entity(name = I18nView.GROUP_FORMAT)
public class I18nView extends AbstractPersistable<Integer> {
    public static final String GROUP_FORMAT = "i18n_data_${user}_${group}";

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
    public I18nIdWrapper toIdWrapper() {
        return new I18nIdWrapper(id, user, group, locale);
    }
}
