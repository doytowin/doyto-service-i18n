package win.doyto.i18n.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import win.doyto.i18n.common.HsqldbDialect;
import win.doyto.i18n.common.TranslationTableDialect;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * DemoConfig
 *
 * @author f0rb on 2020-04-18
 */
@Profile({"demo", "test"})
@Configuration
public class DemoConfig {

    @Resource
    public void setDataSource(DataSource dataSource) {
        new ResourceDatabasePopulator(new ClassPathResource("import.sql")).execute(dataSource);
    }

    @Bean
    public TranslationTableDialect translationTableDialect() {
        return new HsqldbDialect();
    }

}
