package win.doyto.i18n;

import javax.servlet.Filter;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import win.doyto.web.WebContextFilter;
import win.doyto.web.mybatis.MysqlPaginationInterceptor;

/**
 * I18nApp
 *
 * @author f0rb on 2017-03-29.
 */
@SpringBootApplication(scanBasePackages = {"win.doyto.i18n", "win.doyto.web"})
@EnableFeignClients({"win.doyto.client.database"})
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

    @Bean
    public Interceptor configMyBatisInterceptor() {
        return new MysqlPaginationInterceptor();
    }
}