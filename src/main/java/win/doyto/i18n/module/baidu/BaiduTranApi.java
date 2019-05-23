package win.doyto.i18n.module.baidu;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * BaiduTranApi
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
@Component
public class BaiduTranApi {

    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    @Value("${doyto.baidu.appid:2015063000000001}")
    private String appid;
    @Value("${doyto.baidu.securityKey:12345678}")
    private String securityKey;

    public BaiduTranApi() {
    }

    public BaiduTranApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public BaiduTran getTrans(String query, String from, String to) {
        return JSON.parseObject(getTransResult(query, from, to), BaiduTran.class);
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        String ret = null;
        try {
            ret = Request.Get(getUrlWithQueryString(TRANS_API_HOST, params))
                         .execute().returnContent().asString();
        } catch (IOException e) {
            log.error("翻译接口调用错误", e);
        }
        return ret;
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", DigestUtils.md5Hex(src));

        return params;
    }

    public static String getUrlWithQueryString(String url, Map<String, String> params) throws UnsupportedEncodingException {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            String encodedValue = URLEncoder.encode(value, "utf-8");
            builder.append(key);
            builder.append('=');
            builder.append(encodedValue);

            i++;
        }

        return builder.toString();
    }

}
