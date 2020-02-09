package win.doyto.i18n.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import win.doyto.common.web.response.ErrorCode;

/**
 * I18nErrorCode
 *
 * @author f0rb
 * @date 2019-05-21
 */
@Getter
@AllArgsConstructor
public enum I18nErrorCode implements ErrorCode {
    RECORD_NOT_FOUND(1001, "查询记录不存在")
    ;

    private final Integer code;

    private final String message;
}
