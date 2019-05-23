package win.doyto.i18n.module.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.ErrorCode;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;

import java.util.List;
import javax.validation.Valid;

/**
 * LocaleController
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@JsonBody
@RestController
@RequestMapping("/api/locale")
@PreAuthorize("hasAnyRole('i18n')")
class LocaleController implements LocaleApi {
    private LocaleService localeService;

    public LocaleController(LocaleService localeService) {
        this.localeService = localeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Object page(
            @CurrentUser String username,
            @Valid LocaleQuery localeQuery
    ) {
        localeQuery.setOwner(username);
        return localeService.page(localeQuery);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public Object save(
            @CurrentUser String username,
            @RequestBody @Valid LocaleRequest resourceLocale,
            @PathVariable(value = "id", required = false) Integer id
    ) {
        LocaleEntity origin = localeService.get(id);
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        ErrorCode.assertTrue(username.equals(origin.getOwner()), I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLanguage(resourceLocale.getLanguage());
        origin.setBaiduTranLang(resourceLocale.getBaiduTranLang());
        return localeService.save(origin);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable("id") Integer id) {
        return localeService.delete(id);
    }

    @Override
    public LocaleResponse getByGroupAndLocale(String group, String locale) {
        return LocaleResponse.build(localeService.getByGroupAndLocale(group, locale));
    }

    @Override
    public void create(LocaleRequest request) {
        localeService.create(request.toResourceLocale());
    }

    @Override
    public List<LocaleResponse> query(LocaleQuery localeQuery) {
        return localeService.query(localeQuery, LocaleResponse::build);
    }
}