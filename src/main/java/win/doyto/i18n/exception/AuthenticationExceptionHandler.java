package win.doyto.i18n.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import win.doyto.web.RestEnum;
import win.doyto.web.RestError;

/**
 * ExceptionHandler
 *
 * @author f0rb on 2018-04-15.
 */
@Slf4j
@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleException(AccessDeniedException e) {
        log.warn("业务处理异常: {}:{}", e.getClass().getSimpleName(), e.getMessage());
        RestError ret;
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
            ret = RestEnum.UserLoginExpired.value();
        } else {
            ret = RestEnum.UserPermissionInsufficient.value();
        }
        return new ModelAndView(new FastJsonJsonView(), JSON.parseObject(JSON.toJSONString(ret)));
    }

}
