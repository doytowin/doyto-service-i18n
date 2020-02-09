package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    private Date createTime;

}
