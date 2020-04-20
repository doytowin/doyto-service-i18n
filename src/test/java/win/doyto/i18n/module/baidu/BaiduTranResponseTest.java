package win.doyto.i18n.module.baidu;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * BaiduTranResponseTest
 *
 * @author f0rb on 2020-04-20
 */
class BaiduTranResponseTest {

    @Test
    void success() {
        BaiduTranResponse response = new BaiduTranResponse();
        assertTrue(response.success());

        response.setErrorCode("52000");
        assertTrue(response.success());

        response.setErrorCode("1");
        assertFalse(response.success());
    }
}