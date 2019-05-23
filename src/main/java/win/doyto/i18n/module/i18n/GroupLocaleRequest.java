package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * GroupLocaleRequest
 *
 * @author f0rb on 2019-05-22
 */
@Getter
@Setter
public class GroupLocaleRequest {
    @NotNull
    private String group;

    @NotNull
    private String label;

    @NotNull
    private String locale;
}
