package win.doyto.i18n.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import win.doyto.web.service.BusinessException;

/**
 * GroupNotFoundException
 *
 * @author f0rb on 2017-03-30.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BusinessNotFoundException extends BusinessException {
    public BusinessNotFoundException(String message) {
        super(message);
    }
}
