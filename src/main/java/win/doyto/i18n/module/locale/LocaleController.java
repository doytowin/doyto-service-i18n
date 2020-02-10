package win.doyto.i18n.module.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.common.web.controller.AbstractIQRSController;
import win.doyto.common.web.response.ErrorCode;
import win.doyto.common.web.response.JsonBody;
import win.doyto.i18n.common.I18nErrorCode;

import java.util.Objects;
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
class LocaleController extends AbstractIQRSController<LocaleEntity, Integer, LocaleQuery, LocaleRequest, LocaleResponse> implements LocaleService {

    @Override
    protected LocaleResponse buildResponse(LocaleEntity localeEntity) {
        LocaleResponse localeResponse = new LocaleResponse();
        localeResponse.setId(localeEntity.getId());
        localeResponse.setOwner(localeEntity.getOwner());
        localeResponse.setLocale(localeEntity.getLocale());
        localeResponse.setLanguage(localeEntity.getLanguage());
        localeResponse.setBaiduLocale(localeEntity.getBaiduTranLang());
        return localeResponse;
    }

    @Override
    protected LocaleEntity buildEntity(LocaleRequest localeRequest) {
        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setOwner(localeRequest.getUsername());
        localeEntity.setGroupId(localeRequest.getGroupId());
        localeEntity.setLocale(localeRequest.getLocale());
        localeEntity.setBaiduTranLang(localeRequest.getBaiduLocale());
        localeEntity.setLanguage(localeRequest.getLanguage());
        localeEntity.setDeleted(true);
        return localeEntity;
    }

    @Override
    @PutMapping("{id}")
    public void update(
            @PathVariable(value = "id", required = false) Integer id,
            @RequestBody @Valid LocaleRequest localeRequest
    ) {
        LocaleEntity origin = get(id);
        ErrorCode.assertNotNull(origin, I18nErrorCode.RECORD_NOT_FOUND);
        ErrorCode.assertTrue(Objects.equals(localeRequest.getUsername(), origin.getOwner()), I18nErrorCode.RECORD_NOT_FOUND);
        origin.setLanguage(localeRequest.getLanguage());
        origin.setBaiduTranLang(localeRequest.getBaiduLocale());
        update(origin);
    }

    @Override
    public LocaleResponse getByGroupAndLocale(String group, String locale) {
        return buildResponse(get(LocaleQuery.builder().groupName(group).locale(locale).build()));
    }

}