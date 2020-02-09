package win.doyto.i18n.module.locale;

import lombok.Getter;
import lombok.Setter;

/**
 * LocaleResponse
 *
 * @author f0rb on 2019-05-23
 */
@Getter
@Setter
public class LocaleResponse {
    private Integer id;

    private String baiduLocale;

    private String owner;

    private String locale;

    private String language;

}
