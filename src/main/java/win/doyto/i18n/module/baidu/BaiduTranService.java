package win.doyto.i18n.module.baidu;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * BaiduTranService
 *
 * @author f0rb on 2019-06-12
 */
@Service
@SuppressWarnings("java:S4790")
public class BaiduTranService {

    @SneakyThrows
    public BaiduTranResponse translate(String query, String from, String to) {
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

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }

        return Request.Get("http://api.fanyi.baidu.com/api/trans/vip/translate?" + sb.toString())
                      .connectTimeout(1000).execute()
                      .handleResponse(httpResponse -> JSON.parseObject(httpResponse.getEntity().getContent(), BaiduTranResponse.class));

    }

}
