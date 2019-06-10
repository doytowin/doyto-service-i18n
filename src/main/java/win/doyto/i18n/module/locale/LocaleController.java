package win.doyto.i18n.module.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.auth.annotation.CurrentUser;
import win.doyto.common.web.ErrorCode;
import win.doyto.common.web.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;
import win.doyto.query.service.AbstractCrudService;

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
class LocaleController extends AbstractCrudService<LocaleEntity, Integer, LocaleQuery> implements LocaleService {
    
    @RequestMapping(method = RequestMethod.GET)
    public Object page(
            @CurrentUser String username,
            @Valid LocaleQuery localeQuery
    ) {
        localeQuery.setOwner(username);
        return page(localeQuery, LocaleResponse::build);
    }

    @PutMapping("{id}")
    public void save(
            @CurrentUser String username,
            @RequestBody @Valid LocaleRequest resourceLocale,
            @PathVariable(value = "id", required = false) Integer id
    ) {
        LocaleEntity origin = get(id);
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        ErrorCode.assertTrue(username.equals(origin.getOwner()), I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLanguage(resourceLocale.getLanguage());
        origin.setBaiduTranLang(resourceLocale.getBaiduTranLang());
        update(origin);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteById(@PathVariable("id") Integer id) {
        super.delete(id);
    }

    public LocaleResponse getByGroupAndLocale(String group, String locale) {
        return LocaleResponse.build(get(LocaleQuery.builder().groupName(group).locale(locale).build()));
    }

    public void create(LocaleRequest request) {
        create(request.toResourceLocale());
    }

    public List<LocaleResponse> list(LocaleQuery localeQuery) {
        return query(localeQuery, LocaleResponse::build);
    }
}