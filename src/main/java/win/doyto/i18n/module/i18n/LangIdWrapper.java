package win.doyto.i18n.module.i18n;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import win.doyto.query.core.IdWrapper;

/**
 * LandIdWrapper
 *
 * @author f0rb on 2020-01-30
 */
@Getter
@RequiredArgsConstructor
public class LangIdWrapper implements IdWrapper<Integer> {

    @NonNull
    private Integer id;

    @NonNull
    private String user;

    @NonNull
    private String group;

    @Override
    public String toCacheKey() {
        return id + "";
    }
}
