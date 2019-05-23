package win.doyto.i18n.module.baidu;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * BaiduTran
 *
 * @author f0rb on 2017-04-17.
 */
@Getter
@Setter
public class BaiduTran {
    @JSONField(name = "error_code")
    String errorCode;

    @JSONField(name = "error_msg")
    String errorMsg;


    String from;
    String to;
    @JSONField(alternateNames = "trans_result")
    BaiduTranEntry[] transResult;

    String monLang;
    String query;

    public boolean fail() {
        return errorCode != null && !errorCode.equals("52000");
    }
}
