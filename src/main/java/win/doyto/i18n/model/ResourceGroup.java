package win.doyto.i18n.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.web.PageableModel;

@Getter
@Setter
public class ResourceGroup extends PageableModel<ResourceGroup> {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String owner;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

}