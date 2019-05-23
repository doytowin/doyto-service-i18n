package win.doyto.i18n.module.locale;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.IntegerId;

/**
 * toResourceLocale
 *
 * @author f0rb on 2017-04-16.
 */
@Getter
@Setter
@Entity
@Table(name = LocaleEntity.TABLE)
class LocaleEntity extends IntegerId {

    public static final String TABLE = "i18n_resource_locale";

    private static final long serialVersionUID = 1L;

    private Integer groupId;

    private String owner;

    private String locale;

    private String baiduTranLang;

    private String language;

    private Date createTime;

    private Date updateTime;

    private Boolean status;

}