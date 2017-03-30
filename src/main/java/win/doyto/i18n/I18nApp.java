package win.doyto.i18n;

import javax.servlet.Filter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import win.doyto.web.WebContextFilter;

/**
 * I18nApp
 *
 * @author f0rb on 2017-03-29.
 */
@SpringBootApplication(scanBasePackages = {"win.doyto.i18n", "win.doyto.web"})
@PropertySource("classpath:config.properties")
public class I18nApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(I18nApp.class, args);
    }

    @Bean
    public Filter configWebContextFilter() {
        return new WebContextFilter();
    }

    //@Bean
    //public Interceptor configMyBatisInterceptor() {
    //    return new CachingExecutorInterceptor();
    //}
}