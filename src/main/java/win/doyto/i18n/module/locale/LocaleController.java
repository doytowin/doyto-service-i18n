package win.doyto.i18n.module.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.query.web.controller.AbstractIQRSController;
import win.doyto.query.web.response.ErrorCode;
import win.doyto.query.web.response.JsonBody;
import win.doyto.query.web.response.PresetErrorCode;

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
class LocaleController extends AbstractIQRSController<LocaleEntity, Integer, LocaleQuery, LocaleRequest, LocaleResponse> implements LocaleApi {

    @Override
    protected LocaleResponse buildResponse(LocaleEntity localeEntity) {
        LocaleResponse localeResponse = new LocaleResponse();
        localeResponse.setId(localeEntity.getId());
        localeResponse.setOwner(localeEntity.getCreateUserId());
        localeResponse.setLocale(localeEntity.getLocale());
        localeResponse.setLanguage(localeEntity.getLanguage());
        localeResponse.setBaiduLocale(localeEntity.getBaiduLocale());
        return localeResponse;
    }

    @Override
    protected LocaleEntity buildEntity(LocaleRequest localeRequest) {
        LocaleEntity localeEntity = new LocaleEntity();
        localeEntity.setCreateUserId(localeRequest.getUsername());
        localeEntity.setGroupName(localeRequest.getGroup());
        localeEntity.setLocale(localeRequest.getLocale());
        localeEntity.setBaiduLocale(localeRequest.getBaiduLocale());
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
        ErrorCode.assertNotNull(origin, PresetErrorCode.ENTITY_NOT_FOUND);
        ErrorCode.assertTrue(Objects.equals(localeRequest.getUsername(), origin.getCreateUserId()), PresetErrorCode.ENTITY_NOT_FOUND);
        origin.setLanguage(localeRequest.getLanguage());
        origin.setBaiduLocale(localeRequest.getBaiduLocale());
        update(origin);
    }

}