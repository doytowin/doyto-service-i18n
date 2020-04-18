package win.doyto.i18n.module.i18n;

import lombok.*;
import win.doyto.query.core.IdWrapper;

/**
 * LandIdWrapper
 *
 * @author f0rb on 2020-01-30
 */
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class I18nIdWrapper implements IdWrapper<Integer> {

    private Integer id;

    @NonNull
    private String user;

    @NonNull
    private String group;

    private String locale;

    @Override
    public String toCacheKey() {
        return String.valueOf(id);
    }
}
