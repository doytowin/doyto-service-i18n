package win.doyto.i18n.component;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.BeforeClass;
import org.junit.Test;
import win.doyto.i18n.component.baidu.BaiduTran;
import win.doyto.i18n.component.baidu.BaiduTranApi;

/**
 * BaiduTranApiTest
 *
 * @author f0rb on 2017-04-16.
 */
@Slf4j
public class BaiduTranApiTest {
    private BaiduTranApi baiduTranApi = new BaiduTranApi("20170417000044993", "VaF807U3s7X6ZpRkcThL");
    @BeforeClass
    public static void init() {
    }

    @Test
    public void getTransResult() throws Exception {
        String ret;
        ret = baiduTranApi.getTransResult("用户名", "zh", "fra");
        log.info(ret);
        ret = new BaiduTranApi("2015063000000001", "12345678")
                .getTransResult("username", "en", "fra");
        log.info(ret);

        ret = new BaiduTranApi("2015063000000001", "12345678")
                .getTransResult("username", "en", "jp");
        log.info(ret);

        BaiduTran tran = new BaiduTranApi("2015063000000001", "12345678")
                .getTrans("username", "en", "kor");
        log.info("{}", JSON.toJSONString(tran, true));

    }
}