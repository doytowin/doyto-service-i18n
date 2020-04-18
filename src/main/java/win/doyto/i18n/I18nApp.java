package win.doyto.i18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * I18nApp
 *
 * @author f0rb on 2017-03-29.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
@EnableConfigurationProperties
@ComponentScan(basePackages = {"win.doyto.i18n", "win.doyto.query.web.component"})
public class I18nApp {
    public static void main(String[] args) {
        SpringApplication.run(I18nApp.class, args);
    }
}
