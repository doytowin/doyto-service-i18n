package win.doyto.i18n.module.baidu;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

/**
 * BaiduTranService
 *
 * @author f0rb on 2019-06-12
 */
@FeignClient(value = "baidu", url = "http://api.fanyi.baidu.com/api")
public interface BaiduTranService {

    @RequestMapping(value = "/trans/vip/translate", method = RequestMethod.GET)
    BaiduTranResponse translate(@RequestParam Map<String, String> params);

    default BaiduTranResponse translate(String query, String from, String to) {
        String appId = "20170417000044993";
        String securityKey = "VaF807U3s7X6ZpRkcThL";
        String salt = String.valueOf(System.currentTimeMillis());
        String sign = DigestUtils.md5Hex(appId + query + salt + securityKey);


        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appId);
        params.put("salt", salt);
        params.put("sign", sign);
        return translate(params);
    }

}
