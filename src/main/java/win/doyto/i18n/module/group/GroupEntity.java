package win.doyto.i18n.module.group;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.IntegerId;

@Getter
@Setter
@Entity
@Table(name = GroupEntity.TABLE)
class GroupEntity extends IntegerId {

    public static final String TABLE = "i18n_resource_group";

    private static final long serialVersionUID = 1L;

    private String owner;

    private String name;

    private String label;

    private Date createTime;

    private Date updateTime;

    private Boolean valid;

    private Boolean deleted;

}