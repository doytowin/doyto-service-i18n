package win.doyto.i18n.module.locale;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import win.doyto.query.web.controller.AbstractIQEEController;
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
class LocaleController extends AbstractIQEEController<LocaleEntity, Integer, LocaleQuery> implements LocaleApi {

    @SuppressWarnings("java:S4684")
    @Override
    @PutMapping("{id}")
    public void update(
            @PathVariable(value = "id", required = false) Integer id,
            @RequestBody @Valid LocaleEntity update
    ) {
        LocaleEntity origin = get(id);
        ErrorCode.assertNotNull(origin, PresetErrorCode.ENTITY_NOT_FOUND);
        ErrorCode.assertTrue(Objects.equals(update.getCreateUserId(), origin.getCreateUserId()), PresetErrorCode.ENTITY_NOT_FOUND);
        origin.setLanguage(update.getLanguage());
        origin.setBaiduLocale(update.getBaiduLocale());
        update(origin);
    }

}