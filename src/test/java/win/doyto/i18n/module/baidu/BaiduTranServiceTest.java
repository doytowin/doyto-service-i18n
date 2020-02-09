package win.doyto.i18n.module.baidu;

import org.junit.jupiter.api.Test;
import win.doyto.i18n.I18nAppTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BaiduTranServiceTest
 *
 * @author f0rb on 2020-02-06
 */
class BaiduTranServiceTest extends I18nAppTest {

    @Resource
    private BaiduTranService baiduTranService;

    @Test
    void translate() {
        BaiduTranResponse response = baiduTranService.translate("测试", "zh", "en");
        assertTrue(response.success());
        assertEquals("test", response.transResult[0].getDst());
    }
}