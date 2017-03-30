package win.doyto.i18n.controller;

import java.util.List;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.i18n.exception.BusinessNotFoundException;
import win.doyto.i18n.model.Lang;
import win.doyto.i18n.service.GroupService;
import win.doyto.i18n.view.GroupXlsxView;

/**
 * I18nController
 *
 * @author f0rb on 2017-03-30.
 */
@Slf4j
@RestController
@RequestMapping("/api/i18n")
public class I18nController {
    @Resource
    private GroupService groupService;

    //importFromExcel
    //importFromJSON
    //importFromProperties

    @RequestMapping(value = "{group}.xlsx", method = RequestMethod.GET)
    public Object exportAll(ModelAndView mav, @PathVariable("group") String group) {
        groupService.checkGroup(group);
        List data = groupService.query(group);
        mav.setView(new GroupXlsxView());
        mav.addObject("data", data);
        return mav;
    }

    @RequestMapping(value = "{group}/{locale}", method = RequestMethod.GET)
    public Object exportByLocale(ModelAndView mav, @PathVariable("group") String group, @PathVariable("locale") String locale) {
        groupService.checkGroupAndLocale(group, locale);
        List<Lang> data = groupService.query(group, locale);
        return data;
    }

    @ExceptionHandler({BusinessNotFoundException.class})
    public void handleNotFound(BusinessNotFoundException e) {
        throw e;
    }
}
