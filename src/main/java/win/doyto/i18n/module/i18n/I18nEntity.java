package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.CommonEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = I18nEntity.GROUP_FORMAT)
public class I18nEntity extends CommonEntity<Integer, Integer> {
    public static final String GROUP_FORMAT = "i18n_data_${user}_${group}";
    private static final long serialVersionUID = 1L;

    private String user;

    private String group;

    private String label;

    private String defaults;

    private String memo;

    private Boolean valid;

}