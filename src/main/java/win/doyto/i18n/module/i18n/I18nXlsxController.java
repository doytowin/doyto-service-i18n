package win.doyto.i18n.module.i18n;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;

/**
 * I18nXlsxController
 *
 * @author f0rb on 2019-05-22
 */
@RequestMapping("/api/i18n")
@Controller
public class I18nXlsxController {

    public I18nXlsxController(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    I18nService i18nService;

    /**
     * 导出所有的标签和语言
     *
     * @param group 资源分组名称
     * @return i18n.xlsx
     */
    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public ModelAndView exportAllToExcel(Authentication oper, @PathVariable("group") String group) {
        HashMap<String, Object> map = new HashMap<>();
        List data = i18nService.query(oper.getName(), group);
        map.put("data", data);
        map.put("group", group);
        return new ModelAndView("i18nXlsxView", map);
    }
}

