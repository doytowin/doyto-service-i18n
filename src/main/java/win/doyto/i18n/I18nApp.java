package win.doyto.i18n;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * I18nApp
 *
 * @author f0rb on 2017-03-29.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"win.doyto.i18n", "win.doyto.common.web"})
@EnableFeignClients
public class I18nApp {
    public static void main(String[] args) {
        SpringApplication.run(I18nApp.class, args);
    }
}
