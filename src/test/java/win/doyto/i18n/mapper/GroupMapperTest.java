package win.doyto.i18n.mapper;

import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.i18n.common.Constant;

/**
 * GroupMapperTest
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
public class GroupMapperTest extends I18nAppTest {
    @Resource
    private GroupLocaleMapper groupLocaleMapper;

    private String group = Constant.SYSTEM_GROUP;

    @Test
    public void langByGroup() throws Exception {
        List ret = groupLocaleMapper.langByGroup(group);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void langByGroupAndLocale() throws Exception {
        List ret = groupLocaleMapper.langByGroupAndLocale(group, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

}