package win.doyto.i18n.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import win.doyto.common.web.ErrorCode;

/**
 * I18nErrorCode
 *
 * @author f0rb
 * @date 2019-05-21
 */
@Getter
@AllArgsConstructor
public enum I18nErrorCode implements ErrorCode {
    LOGIN_EXPIRED(1, "登录过期"),
    RECORD_NOT_FOUND(10002, "查询记录不存在")
    ;

    private final Integer code;

    private final String message;
}
