package win.doyto.i18n.common;

import lombok.Getter;
import win.doyto.query.web.response.ErrorCode;

/**
 * I18nErrorCode
 *
 * @author f0rb on 2020-05-04
 */
@Getter
public enum I18nErrorCode implements ErrorCode {
    GROUP_NOT_FOUND("多语言分组未配置: %s"),
    LOCALE_NOT_FOUND("多语言分组[%s]未配置语种: %s")
    ;

    private final Integer code;
    private final String message;

    I18nErrorCode(String message) {
        this.code = I18nErrorCode.Index.count++;
        this.message = message;
    }

    public ErrorCode build(Object... args) {
        return ErrorCode.build(this.code, String.format(this.message, args));
    }

    private static class Index {
        private static int count = 2001;
    }
}
