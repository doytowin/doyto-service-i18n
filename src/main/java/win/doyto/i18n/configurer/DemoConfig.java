package win.doyto.i18n.configurer;

import jakarta.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import win.doyto.i18n.common.HsqldbDialect;
import win.doyto.i18n.common.TranslationTableDialect;

import javax.sql.DataSource;

/**
 * DemoConfig
 *
 * @author f0rb on 2020-04-18
 */
@ConditionalOnClass(name = "org.hsqldb.jdbc.JDBCDriver")
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
