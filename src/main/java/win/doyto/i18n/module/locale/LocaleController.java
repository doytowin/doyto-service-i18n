package win.doyto.i18n.module.locale;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import win.doyto.query.entity.UserIdProvider;
import win.doyto.query.web.controller.AbstractEIQController;
import win.doyto.query.web.response.ErrorCode;
import win.doyto.query.web.response.JsonBody;
import win.doyto.query.web.response.PresetErrorCode;

import java.util.Objects;
import javax.annotation.Resource;
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
class LocaleController extends AbstractEIQController<LocaleEntity, Integer, LocaleQuery> implements LocaleApi {

    @Resource
    @Setter
    UserIdProvider<String> userIdProvider;

    @SuppressWarnings("java:S4684")
    @Override
    @PutMapping("{id}")
    public void update(@RequestBody @Valid LocaleEntity update) {
        LocaleEntity origin = get(update.getId());
        ErrorCode.assertNotNull(origin, PresetErrorCode.ENTITY_NOT_FOUND);
        ErrorCode.assertTrue(Objects.equals(userIdProvider.getUserId(), origin.getCreateUserId()), PresetErrorCode.ENTITY_NOT_FOUND);
        origin.setLanguage(update.getLanguage());
        origin.setBaiduLocale(update.getBaiduLocale());
        service.update(origin);
    }

}