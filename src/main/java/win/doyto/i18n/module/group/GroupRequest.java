package win.doyto.i18n.module.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

/**
 * GroupRequest
 *
 * @author f0rb on 2018-04-21.
 */
@Getter
@Setter
public class GroupRequest {
    private Integer id;

    @Pattern(regexp = ".+")
    private String name;

    @Pattern(regexp = ".+")
    private String label;

    private String locale = "zh_CN";

}
