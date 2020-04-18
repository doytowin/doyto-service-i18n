package win.doyto.i18n.module.baidu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BaiduTranServiceTest
 *
 * @author f0rb on 2020-02-06
 */
class BaiduTranServiceTest {

    private BaiduTranService baiduTranService = new BaiduTranService();

    @Test
    void translate() {
        BaiduTranResponse response = baiduTranService.translate("测试", "zh", "en");
        assertTrue(response.success());
        assertEquals("test", response.transResult[0].getDst());
    }
}