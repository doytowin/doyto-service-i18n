package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * GroupLocaleRequest
 *
 * @author f0rb on 2019-05-22
 */
@Getter
@Setter
public class GroupLocaleRequest {
    @NotNull
    @Size(max = 15)
    private String group;

    @NotNull
    @Size(max = 15)
    private String label;

    @NotNull
    @Size(max = 7)
    private String locale;
}
