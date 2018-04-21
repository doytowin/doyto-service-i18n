package win.doyto.i18n.model;

import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

/**
 * ResourceGroupAddReq
 *
 * @author f0rb on 2018-04-21.
 */
@Getter
@Setter
public class ResourceGroupAddReq {

    @Pattern(regexp = ".+")
    private String name;

    @Pattern(regexp = ".+")
    private String label;

    private String locale = "zh_CN";

}
