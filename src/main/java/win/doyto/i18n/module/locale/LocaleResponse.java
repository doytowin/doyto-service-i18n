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

    private String baiduTranLang;

    private String owner;

    private String locale;

    private String language;

    static LocaleResponse build(LocaleEntity localeEntity) {
        LocaleResponse localeResponse = new LocaleResponse();
        localeResponse.setId(localeEntity.getId());
        localeResponse.setOwner(localeEntity.getOwner());
        localeResponse.setLocale(localeEntity.getLocale());
        localeResponse.setLanguage(localeEntity.getLanguage());
        localeResponse.setBaiduTranLang(localeEntity.getBaiduTranLang());
        return localeResponse;
    }
}
