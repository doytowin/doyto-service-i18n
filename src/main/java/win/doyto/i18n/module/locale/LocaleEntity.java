package win.doyto.i18n.module.locale;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.CommonEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * LocaleEntity
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@Entity
@Table(name = "i18n_resource_locale")
public class LocaleEntity extends CommonEntity<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    private Integer groupId;

    private String owner;

    private String locale;

    private String baiduTranLang;

    private String language;

    private Boolean deleted;

}