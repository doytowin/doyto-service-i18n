package win.doyto.i18n.configurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import win.doyto.web.spring.CommonWebMvcConfiguration;

/**
 * WebMvcConfigurer
 *
 * @author f0rb on 2017-04-23.
 */
@Slf4j
@Configuration
public class WebMvcConfigurer extends CommonWebMvcConfiguration {

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


    @Bean @Lazy
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector(ApplicationContext context) {
        return new HandlerMappingIntrospector(context);
    }

}
