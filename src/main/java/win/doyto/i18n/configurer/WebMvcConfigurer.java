package win.doyto.i18n.configurer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import win.doyto.query.web.WebMvcConfigurerAdapter;

/**
 * WebMvcConfigurer
 *
 * @author f0rb on 2017-04-23.
 */
@Slf4j
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerInterceptor logInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
                log.info("Request URL {}", request.getRequestURL());
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication != null) {
                    log.info("user: {}, roles:{}", authentication.getName(), authentication.getAuthorities());
                }
                return true;
            }
        };
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor()).addPathPatterns("/**");
    }

}
