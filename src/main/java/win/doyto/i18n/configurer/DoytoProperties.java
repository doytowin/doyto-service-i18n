package win.doyto.i18n.configurer;

import javax.annotation.Resource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * D1mProperties
 *
 * @author f0rb on 2017-11-06.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "doyto")
public class DoytoProperties {

    private String domain;
    private String homeUrl;
    private String ssoUrl;
    private String loginUrl;
    private String failureUrl;
    private String rememberMeToken;
    private String rememberMeCookie;
    private BaiduProperties baidu = new BaiduProperties();

    public static boolean isDev() {
        return dev;
    }

    private static boolean dev;

    @Resource
    public void setEnvironment(Environment environment) {
        environment.getActiveProfiles();
        for (String profile : environment.getActiveProfiles()) {
            if (profile.equals("dev")) {
                dev = true;
                break;
            }
        }
    }

    @Getter
    @Setter
    static class BaiduProperties {
        private String appid;
        private String securityKey;
    }
}
