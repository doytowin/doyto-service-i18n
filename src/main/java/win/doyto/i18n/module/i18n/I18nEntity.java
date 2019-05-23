package win.doyto.i18n.module.i18n;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.IntegerId;

@Getter
@Setter
public class I18nEntity extends IntegerId {
    private static final long serialVersionUID = 1L;

    private String user;

    private String group;

    private String label;

    private String defaults;

    private String memo;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

}