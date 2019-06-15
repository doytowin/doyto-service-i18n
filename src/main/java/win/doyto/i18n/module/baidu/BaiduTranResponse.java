package win.doyto.i18n.module.baidu;

import lombok.Getter;
import lombok.Setter;

/**
 * BaiduTranResponse
 *
 * @author f0rb on 2017-04-17.
 */
@Getter
@Setter
public class BaiduTranResponse {

    String errorCode;

    String errorMsg;

    String from;
    String to;

    BaiduTranEntry[] transResult;

    String monLang;
    String query;

    public boolean success() {
        return errorCode == null || errorCode.equals("52000");
    }
}
