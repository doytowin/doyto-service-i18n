package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.entity.CommonEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "i18n_resource_group")
public class GroupEntity extends CommonEntity<Integer, String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "groupName")
    private String name;

    private String label;

    private Boolean valid;

    private Boolean deleted;

}