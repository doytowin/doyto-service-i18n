package win.doyto.i18n.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.web.PageableModel;

@Getter
@Setter
public class Group extends PageableModel<Group> {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String label;

    private String value;

    private String memo;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

}