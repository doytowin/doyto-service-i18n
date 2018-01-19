package win.doyto.i18n.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import win.doyto.i18n.I18nAppTest;
import win.doyto.i18n.common.Constant;

import static win.doyto.i18n.common.Constant.DEFAULT_USER;

/**
 * I18nMapperTest
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
public class I18nMapperTest extends I18nAppTest {
    @Resource
    private I18nMapper i18nMapper;

    private String group = Constant.DEFAULT_GROUP;

    @Test
    public void langByGroup() throws Exception {
        List ret = i18nMapper.langByGroup(DEFAULT_USER, group);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }
    @Test
    public void pageLangByGroup() throws Exception {
        List ret = i18nMapper.pageLangByGroup(DEFAULT_USER, group, new RowBounds(5, 10));
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void langByGroupAndLocale() throws Exception {
        List ret = i18nMapper.langByGroupAndLocale(DEFAULT_USER, group, "zh_CN");
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

    @Test
    public void saveTranslation () throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("添加", "添加");
        map.put("修改", "修改");
        int ret = i18nMapper.saveTranslation(DEFAULT_USER, group, "zh_CN", map);
        log.info("结果\n{}", JSON.toJSONString(ret, true));
    }

}