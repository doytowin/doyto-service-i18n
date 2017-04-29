package win.doyto.i18n.component;

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
    String from;
    String to;
    @JSONField(alternateNames = "trans_result")
    BaiduTranEntry[] transResult;
}
