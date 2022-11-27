package win.doyto.i18n.module.locale;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.AbstractCommonEntity;

import javax.persistence.Entity;

/**
 * LocaleEntity
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@Entity(name = "i18n_resource_locale")
public class LocaleEntity extends AbstractCommonEntity<Integer, String> {

    private static final long serialVersionUID = 1L;

    private String groupName;

    private String locale;

    private String baiduLocale;

    private String language;

    private boolean deleted;

}