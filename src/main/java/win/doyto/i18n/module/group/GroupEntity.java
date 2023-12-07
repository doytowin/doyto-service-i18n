package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;
import win.doyto.query.annotation.Column;
import win.doyto.query.annotation.Entity;
import win.doyto.query.entity.AbstractCommonEntity;

@Getter
@Setter
@Entity(name = "i18n_resource_group")
public class GroupEntity extends AbstractCommonEntity<Integer, String> {

    private static final long serialVersionUID = 1L;

    @Column(name = "groupName")
    private String name;

    private String label;

    private Boolean valid;

    private Boolean deleted;

}