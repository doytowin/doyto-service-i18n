package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;

/**
 * GroupResponse
 *
 * @author f0rb on 2019-05-23
 */
@Getter
@Setter
public class GroupResponse {

    private Integer id;

    private String owner;

    private String name;

    private String label;

    static GroupResponse build(GroupEntity groupEntity) {
        GroupResponse groupResponse = new GroupResponse();
        groupResponse.setId(groupEntity.getId());
        groupResponse.setName(groupEntity.getName());
        groupResponse.setOwner(groupEntity.getOwner());
        groupResponse.setLabel(groupEntity.getLabel());
        return groupResponse;
    }
}
