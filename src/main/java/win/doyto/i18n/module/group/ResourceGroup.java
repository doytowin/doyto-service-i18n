package win.doyto.i18n.module.group;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import win.doyto.common.repository.IntegerId;

@Getter
@Setter
public class ResourceGroup extends IntegerId {

    private static final long serialVersionUID = 1L;

    private String owner;

    private String name;

    private String label;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

}