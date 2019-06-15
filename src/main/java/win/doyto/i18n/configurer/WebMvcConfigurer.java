package win.doyto.i18n.configurer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import win.doyto.auth.annotation.EnableSessionAuthentication;
import win.doyto.auth.component.CurrentUserMethodArgumentResolver;
import win.doyto.common.web.WebMvcConfigurerAdapter;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * WebMvcConfigurer
 *
 * @author f0rb on 2017-04-23.
 */
@Slf4j
@Configuration
@EnableSessionAuthentication
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerInterceptor logInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                log.info("Request:URI {} URL {}", request.getRequestURI(), request.getRequestURL());
                return true;
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserMethodArgumentResolver());
    }

}
