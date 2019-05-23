package win.doyto.i18n.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import win.doyto.auth.annotation.EnableSessionAuthentication;

/**
 * ProdW
 *
 * @author f0rb on 2019-05-22
 */
@Profile("prod")
@Configuration
@EnableSessionAuthentication
public class ProdWebSecurityConfigurer {
}

