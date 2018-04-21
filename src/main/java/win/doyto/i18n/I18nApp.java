package win.doyto.i18n;

import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import win.doyto.auth.annotation.EnableSessionAuthentication;
import win.doyto.web.mybatis.MysqlPaginationInterceptor;

/**
 * I18nApp
 *
 * @author f0rb on 2017-03-29.
 */
@SpringCloudApplication
@ComponentScan(basePackages = {"win.doyto.i18n", "win.doyto.web"})
@EnableFeignClients({"win.doyto.client.database"})
@EnableSessionAuthentication
public class I18nApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(I18nApp.class, args);
    }

    //@Bean
    //public Filter configWebContextFilter() {
    //    return new WebContextFilter();
    //}

    //@Bean
    //public Interceptor configMyBatisInterceptor() {
    //    return new CachingExecutorInterceptor();
    //}

    @Bean
    public Interceptor configMyBatisInterceptor() {
        return new MysqlPaginationInterceptor();
    }
}