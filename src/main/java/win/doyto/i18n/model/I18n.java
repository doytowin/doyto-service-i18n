package win.doyto.i18n.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.web.PageableModel;

@Getter
@Setter
public class I18n extends PageableModel<I18n> {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String group;

    private String label;

    private String defaults;

    private String memo;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

}